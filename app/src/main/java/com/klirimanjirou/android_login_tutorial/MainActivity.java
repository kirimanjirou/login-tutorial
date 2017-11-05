package com.klirimanjirou.android_login_tutorial;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import fido.*;

/**
 * Created by Windows on 2017/11/05.
 */
public class MainActivity extends Activity {

    private TextView facetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp myApp = (MyApp) this.getApplication();
        String str = myApp.getTestString();
        TextView textViewSub = (TextView) findViewById(R.id.textview);
        textViewSub.setText(str);
    }

    public void regRequest(View view) {
        TextView textViewUser = (TextView) findViewById(R.id.textview);
        String username = textViewUser.getText().toString();
        registrationStart(username);

        TextView textViewconsole = (TextView) findViewById(R.id.console);
        String regReq = textViewconsole.getText().toString();
        registrationProcess(regReq);
    }

    public void registrationStart(String username){
        AsyncThriftRequest reqMsg = new AsyncThriftRequest(this);
        reqMsg.execute(username);
        Log.d("THRIFT:",reqMsg.toString());
    }

    public void registrationProcess(String regReq){

    }



    public class AsyncThriftRequest extends AsyncTask<String, Void, String> {
        private Activity mainActivity;

        public AsyncThriftRequest(Activity activity){
            this.mainActivity = activity;
        }

        @Override
        protected String doInBackground(String...strings){
            try {
                TTransport transport;
                TSSLTransportParameters params = new TSSLTransportParameters();
                transport = TSSLTransportFactory.getClientSocket("test.kirimanjirou.com", 9090, 0);
                TProtocol protocol = new  TBinaryProtocol(transport);
                fidoUAF.Client client = new fidoUAF.Client(protocol);
                RegistrationRequest reqMsg = client.registrationStart(strings[0]);

                transport.close();
                return reqMsg.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView console = (TextView) mainActivity.findViewById(R.id.console);
            console.setText(result);
        }
    }

    public void facetIDRequest(View view) {
        String facetIDval = "";
        try {
            facetIDval = getFacetID(this.getPackageManager().getPackageInfo(this.getPackageName(), this.getPackageManager().GET_SIGNATURES));
            Log.d("facetID: ", facetIDval);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        facetID = (TextView) findViewById(R.id.textViewFacetID);
        facetID.setText(facetIDval);
    }

    private String getFacetID(PackageInfo paramPackageInfo) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramPackageInfo.signatures[0].toByteArray());
            Certificate certificate = CertificateFactory.getInstance("X509").generateCertificate(byteArrayInputStream);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            String facetID = "android:apk-key-hash:" + Base64.encodeToString(((MessageDigest) messageDigest).digest(certificate.getEncoded()), 3);
            return facetID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
