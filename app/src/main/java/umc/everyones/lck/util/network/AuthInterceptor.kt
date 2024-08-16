package umc.everyones.lck.util.network

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val spf: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val authRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${spf.getString("jwt", "")}").build()
        return chain.proceed(authRequest)
    }
}