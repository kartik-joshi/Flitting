package com.skaka.flittingk.util.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.skaka.flittingk.util.AppDatabase
import com.skaka.flittingk.util.BaseURL
import com.skaka.flittingk.util.retrofit.API
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides @Singleton
    fun appDatabase() = Room.databaseBuilder(context, AppDatabase::class.java, "db").build()


    @Provides @Singleton
    fun api(): API {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseURL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(API::class.java)
    }
}