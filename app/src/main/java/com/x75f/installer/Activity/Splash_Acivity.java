package com.x75f.installer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class Splash_Acivity extends Activity {
    private Client mKinveyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        try {
            if (Generic_Methods.getBooleanPreference(Splash_Acivity.this, "isloggedIn", "login")) {
                if (Generic_Methods.isNetworkAvailable(this)) {
                    mKinveyClient = new Client.Builder(this.getApplicationContext()).build();
                    if (mKinveyClient.user().isUserLoggedIn()) {
                        mKinveyClient.user().logout().execute();
                        mKinveyClient.user().login(Generic_Methods.getStringPreference(Splash_Acivity.this, "email", "login"), Generic_Methods.getStringPreference(Splash_Acivity.this, "password", "login"), new KinveyUserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                checkIfUserIsOfTypeInstaller(user);
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

        mKinveyClient.user().retrieve(userType, new KinveyListCallback<User>() {
            @Override
            public void onSuccess(final User[] usersDatas) {

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
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
                };
                r.run();

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
