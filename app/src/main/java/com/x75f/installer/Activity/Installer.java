package com.x75f.installer.Activity;

import android.app.Application;

import com.kinvey.android.Client;
import com.x75f.installer.Utils.Generic_Methods;

/**
 * Created by JASPINDER on 6/29/2016.
 */
public class Installer extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Generic_Methods.initKinvey(this.getApplicationContext());
    }


}
