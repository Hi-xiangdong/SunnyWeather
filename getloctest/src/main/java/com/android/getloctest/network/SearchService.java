package com.android.getloctest.network;

import com.android.getloctest.model.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * @Description TODO
 * @Author GXD
 * @Date 2022/3/4
 */
interface SearchService {
    @GET("v2.5/${Constant.TOKEN}/{lng},{lat}/realtime.json")
    Call<RealtimeResponse> getRealtimeWeather(@Part("lng") String lng, @Path("lat") String lat);
}
