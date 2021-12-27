package com.gxd.sunnyweather

import android.app.Application
import android.content.Context

/**
 * @Author guoxiangdong
 * @Date 2021/8/9 14:05
 */
class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "WA21CRpq2DShCN6j"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}