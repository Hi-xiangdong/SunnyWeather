package com.android.getloctest.network

import com.android.getloctest.model.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Description TODO
 *
 * @Author GXD
 * @Date 2022/3/4
 */
object SunnyWeatherNetwork {
    private val weatherService = ServiceCreator.create(SearchService::class.java)

    suspend fun getRealtimeWeather(lng: String, lat: String): RealtimeResponse =
        weatherService.getRealtimeWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {   continuation ->
            enqueue(object: Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}