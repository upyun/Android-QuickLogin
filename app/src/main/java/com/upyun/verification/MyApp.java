package com.upyun.verification;

import android.app.Application;
import android.util.Log;

import com.upyun.verifysdk.api.RequestCallback;
import com.upyun.verifysdk.api.UpVerificationInterface;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UpVerificationInterface.setDebugMode(true);
        final long start = System.currentTimeMillis();
        UpVerificationInterface.init(getApplicationContext(), new RequestCallback<String>() {
            @Override
            public void onResult(int code, String result) {
                Log.d("MyApp", "[init] code = " + code + " result = " + result + " consists = " + (System.currentTimeMillis() - start));
            }
        });
    }
}
