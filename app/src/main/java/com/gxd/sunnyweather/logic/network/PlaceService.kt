package com.gxd.sunnyweather.logic.network

import com.gxd.sunnyweather.SunnyWeatherApplication
import com.gxd.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author guoxiangdong
 * @Date 2021/8/9 14:41
 */
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}