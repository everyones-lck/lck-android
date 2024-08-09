package umc.everyones.lck.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import umc.everyones.lck.EveryonesLCKApplication
import umc.everyones.lck.R

class NaverInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-NCP-APIGW-API-KEY-ID", EveryonesLCKApplication.getString(R.string.naver_client_id))
            .addHeader("X-NCP-APIGW-API-KEY", EveryonesLCKApplication.getString(R.string.naver_client_secret))
            .build()

        proceed(newRequest)
    }
}
