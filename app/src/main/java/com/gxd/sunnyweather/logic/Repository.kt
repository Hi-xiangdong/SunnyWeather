package com.gxd.sunnyweather.logic

import androidx.lifecycle.liveData
import com.gxd.sunnyweather.logic.model.Place
import com.gxd.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @Author guoxiangdong
 * @Date 2021/8/11 10:51
 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e : Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}