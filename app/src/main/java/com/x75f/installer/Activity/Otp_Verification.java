package com.x75f.installer.Activity;

import android.app.Dialog;
import android.content.Context;
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
import com.x75f.installer.DB_Local.SQLliteAdapter;
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
    private SQLliteAdapter sqLliteAdapter;


    public Otp_Verification(Context c, String ccu_id) {
        super(c);
        this.c = c;
        ccu_id1 = ccu_id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                dismiss();
                break;
            case (R.id.ok):
                if (otp.getText().toString().equalsIgnoreCase("")) {
                    Generic_Methods.getToast(CCU_Details.getSingletonContext(), "Please enter otp");
                } else {
                    Query newquery = new Query();
                    newquery.equals("_id", ccu_id1);
                    if (Generic_Methods.isNetworkAvailable(CCU_Details.getSingletonContext())) {
                        AsyncAppData<GenericJson> summary = CCU_Details.mKinveyClient.appData("00CCUOneTimePassword", GenericJson.class);
                        summary.get(newquery, new KinveyListCallback<GenericJson>() {
                            @Override
                            public void onSuccess(GenericJson[] genericJsons) {
                                try {
                                    if (genericJsons.length == 0) {
                                        Generic_Methods.getToast(CCU_Details.getSingletonContext(), "Your OTP has expird.Please regenerate OTP.");
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
                        Generic_Methods.getToast(CCU_Details.getSingletonContext(),CCU_Details.getSingletonContext().getResources().getString(R.string.user_offline));
                    }
                }
                break;
        }

    }

    public void VerifyOTP(String otp1) {
        try {
            JSONObject s = new JSONObject(otp1);
            if (s.getString("oneTimePassword").equalsIgnoreCase(otp.getText().toString())) {
                sqLliteAdapter = new SQLliteAdapter(CCU_Details.getSingletonContext());
                int x = sqLliteAdapter.update(ccu_id1, 1);
                Log.d("updaterow", x + "");
                dismiss();
            } else {
                otp.setText("");

                Generic_Methods.getToast(CCU_Details.getSingletonContext(), c.getString(R.string.wrong_otp));
                sqLliteAdapter.update(ccu_id1, 0);
            }
        } catch (Exception e) {
            sqLliteAdapter.update(ccu_id1, 0);
        }
    }
}
