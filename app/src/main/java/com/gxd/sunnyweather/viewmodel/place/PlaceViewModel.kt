package com.gxd.sunnyweather.viewmodel.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gxd.sunnyweather.logic.Repository
import com.gxd.sunnyweather.logic.model.Place

/**
 * @Author guoxiangdong
 * @Date 2021/8/11 11:19
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){  query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}