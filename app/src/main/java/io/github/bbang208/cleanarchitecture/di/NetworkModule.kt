package io.github.bbang208.cleanarchitecture.di

import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.bbang208.cleanarchitecture.BuildConfig
import io.github.bbang208.cleanarchitecture.data.source.remote.TestApiService
import io.github.bbang208.cleanarchitecture.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val OKHTTP_TAG = "OKHTTP"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor { message ->
            try {
                JSONObject(message)
                Logger.t(OKHTTP_TAG).json(message)
            } catch (error: JSONException) {
                if (message.contains("http"))
                    Logger.t(OKHTTP_TAG).d(message)
            }
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApiService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl("http://time.jsontest.com")
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TestApiService =
        retrofit.create(TestApiService::class.java)
}