package com.xy.my_retrofit_okhttp;

import android.text.TextUtils;
import android.util.Log;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String TAG = "ExampleUnitTest";

    private static String BASE_URL = "https://www.wanandroid.com/";

    private ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRetrofitExecute() {

        retrofitExecute();
    }

    @Test
    public void testOkHttp() {

        okhttp3Execute();
//        okhttp3Enqueue();

    }

    private void okhttp3Enqueue() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();

        okhttp3.Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.print(TAG + " okhttp3_enqueue_onFailure : " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                String string = response.body().string();
                if (!TextUtils.isEmpty(string))
                    System.out.print(TAG + " okhttp3_enqueue_response : " + string);

            }
        });

    }

    private void okhttp3Execute() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
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

                    System.out.print(TAG + " okhttp3 execute response: code = " + code +
                            " message = " + (body == null ? null : body.string()));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void retrofitExecute() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://logreport-hotfix.xk12.cn")
                .build();
        try {

            HttpService httpService = retrofit.create(HttpService.class);
            Call<ResponseBody> request = httpService.getWxarticle();

            Response<ResponseBody> response = request.execute();

            if (response != null) {

                int code = response.code();
                ResponseBody responseBody = response.body();

                System.out.print("execute response: code = " + code +
                        " message = " + (responseBody == null ? null : responseBody.string()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}