package com.x75f.installer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Fragments.Summary_Fragment;
import com.x75f.installer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JASPINDER on 6/13/2016.
 */
public class Generic_Methods {
    public static ArrayList<Otp_List> otpLists;
    private static Pubnub pubnub;
    public static boolean initialized = false;
    private static Client mKinveyClient;
    private static Handler summaryDataHandler;
    public static Handler datalogDataHandler;
    public static Handler pingHandler;
    private static String ccuid;
    private Generic_Methods mInstance;
    public static Thread mThreadSummary = new Thread(new Runnable() {
        @Override
        public void run() {
            if (summaryDataHandler != null) {
                GettingSummaryData();
            } else {
                summaryDataHandler = new Handler();
                GettingSummaryData();
            }
        }
    });

    public static void initOtpList() {
        otpLists = new ArrayList<>();
    }

    public static void initHandler(final Context c) {
        pingHandler = new Handler();
        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                if (pingHandler != null) {
                    if (isNetworkAvailable(c)) {
                        ping();
                        if(pingHandler != null)
                        {
                            pingHandler.postDelayed(this,20000);
                        }else {
                            pingHandler = new Handler();
                            pingHandler.postDelayed(this, 20000);
                        }
                    }
                }
            }
        });
        pingHandler.postDelayed(h, 10000);

    }

    public static void GettingSummaryData() {
        Log.e("FetchSummaryData", "step4");
        Query newquery = new Query();
        newquery.equals("_id", ccuid);
        if (isNetworkAvailable(CCU_Details.getSingletonContext())) {
            AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);
            if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                Log.e("FetchSummaryData", "step5");
                summary.get(newquery, new KinveyListCallback<GenericJson>() {

                    @Override
                    public void onSuccess(GenericJson[] genericJsons) {
                        Log.e("FetchSummaryData", "step6");
                        Log.e("success", genericJsons[0].toString());
                        createEditSummarySharedPreference(CCU_Details.getSingletonContext(), genericJsons[0].toString());
                        if (summaryDataHandler != null) {
                            summaryDataHandler.postDelayed(mThreadSummary, 60000);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                    }
                });
            } else {
                ping();
            }
        } else {
            getToast(CCU_Details.getSingletonContext(), CCU_Details.getSingletonContext().getResources().getString(R.string.user_offline));
        }
    }


    public static Thread mThraedtDatalog = new Thread(new Runnable() {
        @Override
        public void run() {
            if (datalogDataHandler != null) {
                GettingDatalogData();
            } else {
                datalogDataHandler = new Handler();
                GettingDatalogData();
            }
        }
    });

    public static void ping() {
        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e("TAG", "Kinvey Ping Failed", t);
            }

            public void onSuccess(Boolean b) {
                Log.d("TAG", "Kinvey Ping Success");
            }
        });
    }

    public static void GettingDatalogData() {

        Query newquery = new Query();
        String collectionName = ccuid + "SystemTS1";
        newquery.addSort("date_time", AbstractQuery.SortOrder.DESC);
        newquery.setLimit(1);
        if (isNetworkAvailable(CCU_Details.getSingletonContext())) {
            AsyncAppData<Data_Log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Data_Log.class);
            if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {

                summary.get(newquery, new KinveyListCallback<Data_Log>() {

                    @Override
                    public void onSuccess(Data_Log[] data_logs) {

                        if (data_logs.length != 0) {
                            createEditDataLogSharedPreference(CCU_Details.getSingletonContext(), data_logs[0].toString());
                            Log.e("success111", data_logs[0].toString());
                        } else {
                            createEditDataLogSharedPreference(CCU_Details.getSingletonContext(), "");
                        }
                        if (datalogDataHandler != null)
                            datalogDataHandler.postDelayed(mThraedtDatalog, 60000);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                    }
                });
            } else {
                getToast(CCU_Details.getSingletonContext(), CCU_Details.getSingletonContext().getResources().getString(R.string.user_offline));
            }
        } else {
            ping();
        }

    }


    Generic_Methods() {

    }

    public Generic_Methods getInstance() {
        if (mInstance == null)
            mInstance = new Generic_Methods();
        return mInstance;
    }

    public static void initKinvey(Context c) {
        mKinveyClient = new Client.Builder(c).build();
        mKinveyClient.enableDebugLogging();
    }

    public static Client getKinveyClient() {

//        mKinveyClient.ping(new KinveyPingCallback() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                Log.e("ping", "success");
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Log.e("ping", "fail");
//            }
//        });


        return mKinveyClient;
    }

    public static void createEditSummarySharedPreference(Context c, String summary) {
        SharedPreferences.Editor editor = c.getSharedPreferences("summarydata", Context.MODE_PRIVATE).edit();
        editor.putString("summary", summary);
        editor.apply();
    }

//    public static void createEditLastOtp(Context c, String otp) {
//        SharedPreferences.Editor editor = c.getSharedPreferences("lastotp", Context.MODE_PRIVATE).edit();
//        editor.putString("otp", otp);
//        editor.apply();
//    }

    public static void createEditDataLogSharedPreference(Context c, String summary) {
        SharedPreferences.Editor editor = c.getSharedPreferences("datalog", Context.MODE_PRIVATE).edit();
        editor.putString("data", summary);
        editor.apply();
    }


    public static void createEditLoginSharedPreference(Context c, boolean isLoggedIn, String email, String password, Boolean Otp_Verified) {
        SharedPreferences.Editor editor = c.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isloggedIn", isLoggedIn);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public static int getIntPref(Context c, String key, String sharedpref) {
        SharedPreferences settings = c.getSharedPreferences(sharedpref,
                Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

    static public Boolean getBooleanPreference(Context c, String key, String sharedpref) {

        SharedPreferences settings = c.getSharedPreferences(sharedpref,
                Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    static public String getStringPreference(Context c, String key, String sharedpref) {
        SharedPreferences settings = c.getSharedPreferences(sharedpref,
                Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    //check for internet connection
    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void getToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static Pubnub getPubNub() {
        if (null == pubnub) {
            pubnub = new Pubnub(App_Constants.publishKey, App_Constants.subscribeKey, true);
            pubnub.setHeartbeat(360, sendCall);
            pubnub.setHeartbeatInterval(30);
            pubnub.setResumeOnReconnect(true);
            pubnub.setSubscribeTimeout(20000);
            initialized = true;

        }
        return pubnub;
    }

    private static final Callback sendCall = new Callback() {
        @Override
        public void successCallback(String channel, Object message) {
            super.successCallback(channel, message);
        }
    };

    public static Otp_List getDataFromOtpBasedOnCcuId(String ccuid) {
        Otp_List otp_list = null;
        if (otpLists != null) {

            for (int i = 0; i < otpLists.size(); i++) {
                if (otpLists.get(i).ccuid.equalsIgnoreCase(ccuid)) {
                    otp_list = otpLists.get(i);
                }
            }
        }
        return otp_list;
    }

    public static void updateOtpListRow(String ccuid, int checked, String otp) {
        if (otpLists != null) {
            for (int i = 0; i < otpLists.size(); i++) {
                if (otpLists.get(i).ccuid.equalsIgnoreCase(ccuid)) {
                    Otp_List otp_list = new Otp_List(ccuid, checked, otp);
                    otpLists.set(i, otp_list);
                }
            }
        }
    }

    public static void PublishToChannel(String MY_PUBNUB_CHANNEL, String msg) {
        Log.e("pubnubcall", msg);
        JSONObject x = null;
        try {
            x = new JSONObject(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                Log.e("pubnubresponse", response.toString());
            }

            public void errorCallback(String channel, PubnubError error) {
                Log.e("pubnubresponse", error.toString());
            }
        };

        getPubNub().publish(MY_PUBNUB_CHANNEL, x, callback);

    }


    public static String createPubnubSystemTestMsg(int a1, int a2, int a3, int a4, int r1, int r2, int r3, int r4, int r5, int r6, int r7) {
        String x;
        JSONObject LogObj = new JSONObject();
        try {
            LogObj.put("a1", a1);
            LogObj.put("a2", a2);
            LogObj.put("a3", a3);
            LogObj.put("a4", a4);
            LogObj.put("r1", r1);
            LogObj.put("r2", r2);
            LogObj.put("r3", r3);
            LogObj.put("r4", r4);
            LogObj.put("r5", r5);
            LogObj.put("r6", r6);
            LogObj.put("r7", r7);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject detail = new JSONObject();
        try {
            detail.put("", LogObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        x = LogObj.toString();
        Log.e("pubnub", x);
        return x;
    }

    public static String createPubnubSystemDamperMsg(int a1, int a2) {
        String x;
        JSONObject LogObj = new JSONObject();
        try {
            LogObj.put("damper_pos", a1);
            LogObj.put("fsv_address", a2);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject detail = new JSONObject();
        try {
            detail.put("", LogObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        x = LogObj.toString();
        return x;
    }


    public static void unbindDrawables(View view) {
        try {
            System.gc();
            Runtime.getRuntime().gc();
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                    View view1 = ((ViewGroup) view).getChildAt(i);
                    if (view1 instanceof EditText || view1 instanceof SearchView) {
                        ((EditText) view).setText("");
                    }
                }
                ((ViewGroup) view).removeAllViews();
            }
        } catch (Exception e) {
            Log.e("drawableerror", e.getStackTrace().toString());
        }
    }

    public static String FetchSummaryData(final String ccuid1) {
        Log.e("FetchSummaryData", "step1");

        summaryDataHandler = new Handler();
        ccuid = ccuid1;
        try {
            if (mThreadSummary != null) {
                Log.e("FetchSummaryData", "step2");
                summaryDataHandler.postDelayed(mThreadSummary, 30000);
            } else {
                Log.e("FetchSummaryData", "step3");
                summaryDataHandler.postDelayed(mThreadSummary, 0);
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static void PauseCalledSummary() {
        try {
            if (mThreadSummary != null && summaryDataHandler != null) {

                mThreadSummary.interrupt();
                summaryDataHandler.removeCallbacks(mThreadSummary);
//                mThreadSummary = null;
                summaryDataHandler = null;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String FetchDatalogData(final String ccuid1) {
        datalogDataHandler = new Handler();
        ccuid = ccuid1;
//        Generic_Methods.getToast(CCU_Details.getSingletonContext(), "Threaddatalogstart");
        try {
            if (mThraedtDatalog != null) {
                datalogDataHandler.postDelayed(mThraedtDatalog, 30000);
//                Generic_Methods.getToast(CCU_Details.getSingletonContext(), "ThreadSummaryStartstp1");
            } else {
                datalogDataHandler.postDelayed(mThraedtDatalog, 30000);
//                Generic_Methods.getToast(CCU_Details.getSingletonContext(), "ThreadSummaryStartstp2");
            }
        } catch (Exception e) {
            datalogDataHandler.postDelayed(mThraedtDatalog, 0);
//            Generic_Methods.getToast(CCU_Details.getSingletonContext(), "ThreaddatalogStartException");
        }

        return null;
    }

    public static void PauseCalledDatalog() {
        try {
            if (mThraedtDatalog != null && datalogDataHandler != null) {

                mThraedtDatalog.interrupt();
                datalogDataHandler.removeCallbacks(mThraedtDatalog);
//                mThraedtDatalog = null;
                datalogDataHandler = null;
//                Generic_Methods.getToast(CCU_Details.getSingletonContext(), "Threaddatalogpause");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
