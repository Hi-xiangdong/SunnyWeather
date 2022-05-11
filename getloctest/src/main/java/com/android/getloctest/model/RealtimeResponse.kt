package com.android.getloctest.model

/**
 * @Description TODO
 *
 * @Author GXD
 * @Date 2022/3/4
 */
data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)

    data class Realtime(val temperature: Float, val humidity: Float)
}
