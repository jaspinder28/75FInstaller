package com.x75f.installer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

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
}
