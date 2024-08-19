package umc.everyones.lck.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import umc.everyones.lck.EveryonesLCKApplication
import umc.everyones.lck.R
import umc.everyones.lck.presentation.party.chat.ViewingPartyChatViewModel
import umc.everyones.lck.presentation.party.chat.WsClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WsModule {

    @Provides
    @Singleton
    @Named("WsClient")
    fun provideWsOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWebSocketService(@Named("WsClient") okHttpClient: OkHttpClient, viewModel: ViewingPartyChatViewModel, request: Request, spf: SharedPreferences): WsClient {
        return WsClient(viewModel, okHttpClient, request, spf)
    }

    @Provides
    @Singleton
    fun provideWebSocketRequest(spf: SharedPreferences): Request {
        return Request.Builder()
            .addHeader("Authorization", "Bearer ${spf.getString("jwt", "")}")
            .url(EveryonesLCKApplication.getString(R.string.ws_url))
            .build()
    }
}