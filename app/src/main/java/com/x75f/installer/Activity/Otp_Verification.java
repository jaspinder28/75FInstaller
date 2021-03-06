package com.x75f.installer.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;

import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Otp_Verification extends Dialog implements View.OnClickListener {
    public Context c;
    @InjectView(R.id.otp)
    EditText otp;
    @InjectView(R.id.cancel)
    Button cancel;
    @InjectView(R.id.ok)
    Button ok;
    private static String ccu_id1;
//    private SQLliteAdapter sqLliteAdapter;
    private int tab;


    public Otp_Verification(Context c, String ccu_id, int tab) {
        super(c);
        this.c = c;
        ccu_id1 = ccu_id;
        this.tab = tab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sqLliteAdapter = new SQLliteAdapter(CCU_Details.getSingletonContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.otp_dialog);
        ButterKnife.inject(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.cancel):
                if (CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 2 || CCU_Details.getSingletonContext().viewPager.getCurrentItem() == 3) {
                    dismiss();
//                    sqLliteAdapter.update(ccu_id1, 0);
                    Generic_Methods.updateOtpListRow(ccu_id1,0,"");
                    CCU_Details.getSingletonContext().viewPager.setCurrentItem(0);
                    CCU_Details.getSingletonContext().bSummary.setTextColor(c.getResources().getColor(R.color.primary));
                    CCU_Details.getSingletonContext().bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.summaryc), null, null);
                    CCU_Details.getSingletonContext().bDatalog.setTextColor(c.getResources().getColor(R.color.gray));
                    CCU_Details.getSingletonContext().bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.datalog), null, null);
                    CCU_Details.getSingletonContext().bSystem.setTextColor(c.getResources().getColor(R.color.gray));
                    CCU_Details.getSingletonContext().bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.systemtest), null, null);
                    CCU_Details.getSingletonContext().bDamper.setTextColor(c.getResources().getColor(R.color.gray));
                    CCU_Details.getSingletonContext().bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.damper), null, null);
                    CCU_Details.getSingletonContext().bNotes.setTextColor(c.getResources().getColor(R.color.gray));
                    CCU_Details.getSingletonContext().bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.notes), null, null);
                } else {
                    dismiss();

//                    sqLliteAdapter.update(ccu_id1, 0);
                    Generic_Methods.updateOtpListRow(ccu_id1,0,"");
                }
                break;
            case (R.id.ok):
                if (otp.getText().toString().equalsIgnoreCase("")) {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), "Please enter otp");
                } else {
                    Query newquery = new Query();
                    newquery.equals("_id", ccu_id1);
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUOneTimePassword", GenericJson.class);
                        summary.get(newquery, new KinveyListCallback<GenericJson>() {
                            @Override
                            public void onSuccess(GenericJson[] genericJsons) {
                                try {
                                    if (genericJsons.length == 0) {
                                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), c.getString(R.string.otp_expired));
                                    } else if (genericJsons.length == 1) {
                                        VerifyOTP(genericJsons[0].toString());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Generic_Methods.getToast(CCU_Details.getSingletonContext(), throwable.getMessage());
                            }
                        });

                    } else {
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), CCU_Details.getSingletonContext().getResources().getString(R.string.user_offline));
                    }
                }
                break;
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void VerifyOTP(String otp1) {
        try {
            JSONObject s = new JSONObject(otp1);
            if (s.getString("oneTimePassword").equalsIgnoreCase(otp.getText().toString())) {
//                Generic_Methods.createEditLastOtp(CCU_Details.getSingletonContext(), otp.getText().toString());
//                int x = sqLliteAdapter.update(ccu_id1, 1);
                Generic_Methods.updateOtpListRow(ccu_id1,1,otp.getText().toString());
//                Generic_Methods.getToast(CCU_Details.getSingletonContext(), x + " " + ccu_id1);
//                Log.d("updaterow", x + "");
                dismiss();
                if (CCU_Details.getSingletonContext().viewPager.getCurrentItem() != 2 && CCU_Details.getSingletonContext().viewPager.getCurrentItem() != 3) {
                    CCU_Details.getSingletonContext().viewPager.setCurrentItem(tab);
                    if (tab == 2) {
                        CCU_Details.getSingletonContext().bSummary.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.summary), null, null);
                        CCU_Details.getSingletonContext().bDatalog.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.datalog), null, null);
                        CCU_Details.getSingletonContext().bSystem.setTextColor(c.getResources().getColor(R.color.primary));
                        CCU_Details.getSingletonContext().bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.systemtestc), null, null);
                        CCU_Details.getSingletonContext().bDamper.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.damper), null, null);
                        CCU_Details.getSingletonContext().bNotes.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.notes), null, null);
                    } else {
                        CCU_Details.getSingletonContext().bSummary.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bSummary.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.summary), null, null);
                        CCU_Details.getSingletonContext().bDatalog.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bDatalog.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.datalog), null, null);
                        CCU_Details.getSingletonContext().bSystem.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bSystem.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.systemtest), null, null);
                        CCU_Details.getSingletonContext().bDamper.setTextColor(c.getResources().getColor(R.color.primary));
                        CCU_Details.getSingletonContext().bDamper.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.damperc), null, null);
                        CCU_Details.getSingletonContext().bNotes.setTextColor(c.getResources().getColor(R.color.gray));
                        CCU_Details.getSingletonContext().bNotes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, c.getResources().getDrawable(R.mipmap.notes), null, null);
                    }
                }

            } else {
                otp.setText("");
                Generic_Methods.getToast(CCU_Details.getSingletonContext(), c.getString(R.string.wrong_otp));
//                sqLliteAdapter.update(ccu_id1, 0);
                Generic_Methods.updateOtpListRow(ccu_id1,0,"");
            }

        } catch (Exception e) {
//            sqLliteAdapter.update(ccu_id1, 0);
            Generic_Methods.updateOtpListRow(ccu_id1,0,"");
        }
    }


}
