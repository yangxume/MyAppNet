package com.xy.my_retrofit_okhttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HttpService {

    @GET("wxarticle/chapters/json")
    Call<ResponseBody> getWxarticle();


}
