package com.robert.trucksimtelemetry.data.api

import com.robert.trucksimtelemetry.data.main.AuthTokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * An {@see Interceptor} that adds an auth token to requests if one is provided, otherwise
 * adds a client id.
 */
class ClientAuthInterceptor(
    private val authTokenDataSource: AuthTokenLocalDataSource,
    private val clientId: String
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (!authTokenDataSource.authToken.isNullOrEmpty()) {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer ${authTokenDataSource.authToken}"
            )
        } else {
            val url = chain.request().url().newBuilder()
                .addQueryParameter("client_id", clientId).build()
            requestBuilder.url(url)
        }
        return chain.proceed(requestBuilder.build())
    }
}
