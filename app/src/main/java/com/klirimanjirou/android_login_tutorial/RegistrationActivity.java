package com.klirimanjirou.android_login_tutorial;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import fido.*;

/**
 * Created by Windows on 2017/11/05.
 */


public class RegistrationActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fido_uaf);

        MyApp myApp = (MyApp) this.getApplication();
        String facetID = myApp.getFacetIDString();
        String regReq = myApp.getRegistrationRequest();
        Log.d("facetID",facetID);
        Log.d("regReq",regReq);
        TextView textRegReq = (TextView) findViewById(R.id.textViewOpMsg);
        textRegReq.setText(regReq.toString());
/*
        fidoKeystore = FidoKeystore.createKeyStore(getApplicationContext());
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
*/
    }
}
