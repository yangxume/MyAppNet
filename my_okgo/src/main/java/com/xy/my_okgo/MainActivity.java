package com.xy.my_okgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String BASE_URL = "https://www.wanandroid.com/";
    private static final String URL_LOGIN = "user/login";
    private Button btn_test_okgo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_test_okgo = findViewById(R.id.btn_test_okgo);

        setOnClickListener();
    }

    private void setOnClickListener() {
        btn_test_okgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogin();
            }
        });
    }

    public void testLogin(){

        OkGoUtils.RequestOption requestOption = new OkGoUtils.RequestOption();

        HashMap<String, String> map = new HashMap<>();
        map.put("username","xuyang");
        map.put("password","Xu_Yang011502");

        requestOption.context = MainActivity.this;
        requestOption.url = BASE_URL+URL_LOGIN;
        requestOption.params = map;
        requestOption.isNormalDeal = false;
        requestOption.iPostJsonStringCb = new AbsPostJsonStringCb() {
            @Override
            public void onSuccess(String str, String data) {

                Log.d(TAG, "onSuccess: str : "+str);
                Log.d(TAG, "onSuccess: data: "+data);
                
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, "onError: "+response.getException() );
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
            }
        };

        OkGoUtils.postJsonStringCallback(requestOption);

    }
}
