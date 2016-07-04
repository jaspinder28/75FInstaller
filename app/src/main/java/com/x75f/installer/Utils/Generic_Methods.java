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
import com.kinvey.java.Query;
import com.kinvey.java.query.AbstractQuery;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.x75f.installer.Activity.CCU_Details;
import com.x75f.installer.Fragments.Summary_Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JASPINDER on 6/13/2016.
 */
public class Generic_Methods {
    private static Pubnub pubnub;
    public static boolean initialized = false;
    private static Client mKinveyClient;
    public static Handler h = new Handler();
    public static Handler h1 = new Handler();
    private static String ccuid;
    private Generic_Methods mInstance;
    public static Thread mThraed = new Thread(new Runnable() {
        @Override
        public void run() {
            Query newquery = new Query();
            newquery.equals("_id", ccuid);
            AsyncAppData<GenericJson> summary = Generic_Methods.getKinveyClient().appData("00CCUSummary", GenericJson.class);
            if (summary.isOnline()) {

                summary.get(newquery, new KinveyListCallback<GenericJson>() {

                    @Override
                    public void onSuccess(GenericJson[] genericJsons) {
                        Log.e("success",genericJsons[0].toString());
                        createEditSummarySharedPreference(CCU_Details.getSingletonContext(), genericJsons[0].toString());
                        h.postDelayed(mThraed, 60000);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                    }
                });
            }

        }
    });


    public static Thread mThraedtimeseries = new Thread(new Runnable() {
        @Override
        public void run() {
            Query newquery = new Query();
            String collectionName = ccuid + "SystemTS1";
            newquery.addSort("date_time", AbstractQuery.SortOrder.DESC);
            newquery.setLimit(1);
            AsyncAppData<Data_Log> summary = Generic_Methods.getKinveyClient().appData(collectionName, Data_Log.class);
            if (summary.isOnline()) {

                summary.get(newquery, new KinveyListCallback<Data_Log>() {

                    @Override
                    public void onSuccess(Data_Log[] data_logs) {

                        if(data_logs.length !=0) {
                            createEditDataLogSharedPreference(CCU_Details.getSingletonContext(), data_logs[0].toString());
                            Log.e("success111",data_logs[0].toString());
                        }else {
                            createEditDataLogSharedPreference(CCU_Details.getSingletonContext(), "");
                        }
                        h1.postDelayed(mThraedtimeseries, 60000);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("result", "failed");
                    }
                });
            }

        }
    });


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

        return mKinveyClient;
    }

    public static void createEditSummarySharedPreference(Context c, String summary) {
        SharedPreferences.Editor editor = c.getSharedPreferences("summarydata", Context.MODE_PRIVATE).edit();
        editor.putString("summary", summary);
        editor.apply();
    }

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

    public static void PublishToChannel(String MY_PUBNUB_CHANNEL, String msg) {
        Log.d("pubnubcall", msg);
        JSONObject x = null;
        try {
            x = new JSONObject(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                Log.d("pubnubresponse", response.toString());
            }

            public void errorCallback(String channel, PubnubError error) {
                Log.d("pubnubresponse", error.toString());
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
            Log.d("drawableerror", e.getStackTrace().toString());
        }
    }

    public static String FetchSummaryData(final String ccuid1) {

        ccuid = ccuid1;
        try {
            if (mThraed.isAlive()) {
                h.postDelayed(mThraed, 30000);
            } else {
                mThraed.start();
            }
        } catch (Exception e) {
            h.postDelayed(mThraed, 30000);
        }

        return null;
    }

    public static void PauseCalled() {
        try {
            mThraed.interrupt();
            h.removeCallbacks(mThraed);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void PauseCalled1() {
        try {
            mThraedtimeseries.interrupt();
            h1.removeCallbacks(mThraedtimeseries);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String FetchDatalogData(final String ccuid1) {

        ccuid = ccuid1;
        try {
            if (mThraedtimeseries.isAlive()) {
                h1.postDelayed(mThraedtimeseries, 30000);
            } else {
                mThraedtimeseries.start();
            }
        } catch (Exception e) {
            h1.postDelayed(mThraedtimeseries, 30000);
        }

        return null;
    }
}
