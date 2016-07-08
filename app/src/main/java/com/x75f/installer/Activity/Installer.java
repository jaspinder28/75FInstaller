package com.x75f.installer.Activity;

import android.app.Application;
import android.os.Handler;

import com.kinvey.android.Client;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.Otp_List;

import java.util.ArrayList;

/**
 * Created by JASPINDER on 6/29/2016.
 */
public class Installer extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Generic_Methods.initKinvey(this.getApplicationContext());
        Generic_Methods.initOtpList();
        Generic_Methods.initHandler(this.getApplicationContext());
    }


}
