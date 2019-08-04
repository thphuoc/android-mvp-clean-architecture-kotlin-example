package com.android.data.remote

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {
    fun <S> createService(header: HashMap<String, String> = hashMapOf(),
                          defaultQuery: HashMap<String, String> = hashMapOf(),
                          baseUrl: String,
                          serviceClass: Class<S>,
                          loggable: Boolean = true): S {

        val prettyLoggingInterceptor = LoggingInterceptor
                .Builder()
                .loggable(loggable)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")

        defaultQuery.entries.forEach {
            prettyLoggingInterceptor.addQueryParam(it.key, it.value)
        }
        header.entries.forEach { entry ->
            prettyLoggingInterceptor.addHeader(entry.key, entry.value)
        }
        val httpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(prettyLoggingInterceptor.build())

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

        return retrofit.create<S>(serviceClass)
    }
}