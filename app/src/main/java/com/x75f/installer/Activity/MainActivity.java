package com.x75f.installer.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.etEmail)
    EditText etEmail;
    @InjectView(R.id.etPassword)
    EditText etPassword;
    @InjectView(R.id.bLogin)
    Button bLogin;
    @InjectView(R.id.bForgotPass)
    Button bForgotPass;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    private static Context _singleton;

    public static Context getSingletonContext() {
        return _singleton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        _singleton = this;
        bForgotPass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        //initilize kinvey client
        bLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.bLogin):
                if (Generic_Methods.isNetworkAvailable(MainActivity.getSingletonContext())) {
                    if (etEmail.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(MainActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
                    } else if (etEmail.getText().toString().indexOf("@") == -1 || etEmail.getText().toString().indexOf(".") == -1) {
                        Toast.makeText(MainActivity.this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
                    } else if (etPassword.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(MainActivity.this, R.string.password_empty, Toast.LENGTH_SHORT).show();
                    } else if (!etEmail.getText().toString().equalsIgnoreCase("installer@75f.io")) {
                        Toast.makeText(MainActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
                    } else {


                        if (Generic_Methods.getKinveyClient().user().isUserLoggedIn()) {
                            Generic_Methods.getKinveyClient().user().logout().execute();
                        }
                        progressBar.setVisibility(View.VISIBLE);
                        Generic_Methods.getKinveyClient().user().login(etEmail.getText().toString().toLowerCase(), etPassword.getText().toString(), new KinveyUserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                Generic_Methods.createEditLoginSharedPreference(MainActivity.getSingletonContext(), true, etEmail.getText().toString(), etPassword.getText().toString(), false);
                                checkIfUserIsOfTypeInstaller(user);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Generic_Methods.getToast(MainActivity.getSingletonContext(), getString(R.string.user_offline));
                }
                break;
            case (R.id.bForgotPass):

                break;
        }


    }


    public void checkIfUserIsOfTypeInstaller(User user) {
        String useremail = user.getUsername();
        Query userType = new Query();
        userType.equals("user_type", "ccu");
        userType.equals("installerEmail", useremail);
        Generic_Methods.getKinveyClient().user().retrieve(userType, new KinveyListCallback<User>() {
            @Override
            public void onSuccess(User[] usersDatas) {
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
//                    Toast.makeText(MainActivity.this,ccudata.size()+"",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, CcuListActivity.class);
                    i.putExtra("ccudata", ccudata);
                    i.putExtra("email", etEmail.getText().toString());
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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


}
