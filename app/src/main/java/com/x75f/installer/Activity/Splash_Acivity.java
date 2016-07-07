package com.x75f.installer.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.UsersData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Splash_Acivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        try {

            if (isNetworkAvailable(Splash_Acivity.this)) {
                if (getBooleanPreference(Splash_Acivity.this, "isloggedIn", "login")) {
                    if (isNetworkAvailable(this)) {
                        if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                            Generic_Methods.getKinveyClient().user().logout().execute();
                            Generic_Methods.getKinveyClient().user().login(Generic_Methods.getStringPreference(Splash_Acivity.this, "email", "login"), Generic_Methods.getStringPreference(Splash_Acivity.this, "password", "login"), new KinveyUserCallback() {
                                @Override
                                public void onSuccess(final User user) {

                                    checkIfUserIsOfTypeInstaller(user);
//                                        }
//                                    });

                                }

                                @Override
                                public void onFailure(Throwable throwable) {

                                    Intent i = new Intent(Splash_Acivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();


                                }
                            });
                        } else {
                            Generic_Methods.getKinveyClient().user().login(Generic_Methods.getStringPreference(Splash_Acivity.this, "email", "login"), Generic_Methods.getStringPreference(Splash_Acivity.this, "password", "login"), new KinveyUserCallback() {
                                @Override
                                public void onSuccess(final User user) {

                                    checkIfUserIsOfTypeInstaller(user);
//                                        }
//                                    });

                                }

                                @Override
                                public void onFailure(Throwable throwable) {

                                    Intent i = new Intent(Splash_Acivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();


                                }
                            });
                        }
                    }
                } else {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            } else {
                Generic_Methods.getToast(Splash_Acivity.this, getResources().getString(R.string.user_offline));
            }
        } catch (Exception e) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }



    public void checkIfUserIsOfTypeInstaller(User user) {
        String useremail = user.getUsername();
        Query userType = new Query();
        userType.equals("user_type", "ccu");
        userType.equals("installerEmail", useremail);

        Generic_Methods.getKinveyClient().user().retrieve(userType, new KinveyListCallback<User>() {
                    @Override
                    public void onSuccess(final User[] usersDatas) {
                        try {
                            ArrayList<UsersData> ccudata = new ArrayList<>();
                            for (int i = 0; i < usersDatas.length; i++) {
                                JSONObject s = new JSONObject(usersDatas[i].toString());
                                UsersData u = new UsersData(s.getString("_id"), s.getString("username"), s.getString("user_type"),
                                        s.getString("emailAddr"), s.getDouble("lng"), s.getString("address"), "",
                                        s.getString("zipcode"), s.getString("state"), s.getString("country"), s.getString("cloudServer"),
                                        s.getString("installedVersion"), s.getString("installedDate"), s.getString("installerEmail"), s.getString("installedTier"),
                                        s.getDouble("lat"), s.getString("ccuName"));
                                ccudata.add(i, u);
                            }
                            Intent i = new Intent(Splash_Acivity.this, CcuListActivity.class);
                            i.putExtra("ccudata", ccudata);
                            i.putExtra("email", Generic_Methods.getStringPreference(Splash_Acivity.this, "email", "login"));
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Intent i = new Intent(Splash_Acivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                Generic_Methods.unbindDrawables(findViewById(R.id.main_layout));
            }
        });
        h.start();

    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static public Boolean getBooleanPreference(Context c, String key, String sharedpref) {

        SharedPreferences settings = c.getSharedPreferences(sharedpref,
                Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }
}
