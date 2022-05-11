package com.android.getloctest

import androidx.lifecycle.liveData
import com.android.getloctest.model.RealtimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import com.android.getloctest.network.SunnyWeatherNetwork

/**
 * @Description TODO
 *
 * @Author GXD
 * @Date 2022/3/4
 */
object Repository {
    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {

                val realtimeResponse = SunnyWeatherNetwork.getRealtimeWeather(lng, lat)

                if (realtimeResponse.status == "ok") {

                    val result = realtimeResponse.result
                    Result.success(result.realtime)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}"
                        )
                    )
                }

        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}