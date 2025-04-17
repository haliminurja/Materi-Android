package id.ac.unuja.sampel.server.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    private const val BASE_URL = "https://sso.unuja.ac.id/portal/mobile_computing/"
    private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB

    private class AuthInterceptor(context: Context) : Interceptor {
        private val appContext = context.applicationContext

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().apply {
                addHeader("Accept", "application/json")
                addHeader("Content-Type", "application/json")
                TokenManager.getToken(appContext)?.let { token ->
                    addHeader("Authorization", "Bearer $token")
                }
            }.build()
            return chain.proceed(request)
        }
    }

    fun createApiService(context: Context): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private fun createOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(Cache(context.cacheDir, CACHE_SIZE.toLong()))
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(AuthInterceptor(context))
            addInterceptor(loggingInterceptor)
        }.build()
    }

}