package com.xy.my_retrofit_okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static String BASE_URL = "https://www.wanandroid.com";

    private static ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test_okhttp3_execute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp3Execute();
            }
        });

        findViewById(R.id.btn_test_okhttp3_enqueue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp3Enqueue();
            }
        });

        findViewById(R.id.btn_test_retrofit_execute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExecutorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        retrofitExecute();
                    }
                });
            }
        });

        findViewById(R.id.btn_test_retrofit_enqueue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitEnqueue();
            }
        });

    }

    private static String url = "https://www.wanandroid.com/wxarticle/chapters/json";

    private void okhttp3Enqueue() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Call call = okHttpClient.newCall(request);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                int code = response.code();
                boolean successful = response.isSuccessful();
                ResponseBody body = response.body();

                Log.d(TAG,  " okhttp3 enqueue response: code = " + code +
                        " message = " + (body == null ? null : body.string()));
            }
        });

    }

    private void okhttp3Execute() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        final okhttp3.Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    okhttp3.Response response = call.execute();

                    int code = response.code();
                    boolean successful = response.isSuccessful();
                    ResponseBody body = response.body();

                    Log.d(TAG,  " okhttp3 execute response: code = " + code +
                            " message = " + (body == null ? null : body.string()));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void retrofitExecute() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        try {

            HttpService httpService = retrofit.create(HttpService.class);
            Call<ResponseBody> request = httpService.getWxarticle();

            Response<ResponseBody> response = request.execute();

            if (response != null) {

                int code = response.code();
                ResponseBody responseBody = response.body();

                Log.d(TAG, "retrofit execute response: code = "+code+
                        " message = "+ (responseBody == null ? null : responseBody.string()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void retrofitEnqueue() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        Call<ResponseBody> request = retrofit.create(HttpService.class)
                .getWxarticle();

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response != null) {

                    int code = response.code();
                    ResponseBody responseBody = response.body();

                    try {
                        Log.d(TAG, "retrofit  enqueue onResponse: code = "+code+
                                " message = "+ (responseBody == null ? null : response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e(TAG, "enqueue: onResponse "+t.getMessage() );

            }
        });
    }
}
