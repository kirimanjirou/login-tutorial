package com.klirimanjirou.android_login_tutorial;

/**
 * Created by Windows on 2017/11/05.
 */
import android.app.Application;
import fido.*;

public class MyApp extends Application {

    private String intentString = "default";

    public String getTestString() {
        return intentString;
    }

    public void setTestString(String str) {
        intentString = str;
    }

    private String regReq = null;

    public String getRegistrationRequest() {
        return regReq ;
    }

    public void setRegistrationRequest(String regReqMsg) {
        regReq = regReqMsg;
    }

    private String facetID = null;

    public String getFacetIDString() {
        return facetID;
    }

    public void setFacetIDSrting(String facetMsg) {
        facetID = facetMsg;
    }


}
