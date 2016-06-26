package com.hpedrorodrigues.gizmodobr.dagger

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.hpedrorodrigues.gizmodobr.constant.GizmodoApiConstant
import com.hpedrorodrigues.gizmodobr.network.GizmodoNetwork
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GizmodoModule(private val application: Application) {

    /**
     * Allow the application context to be injected but require that it be annotated with
     * [ ][ForApplication] to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication fun provideApplicationContext(): Context {
        return application
    }

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GizmodoApiConstant.GizmodoEndpoint).build()
    }

    @Provides fun provideGizmodoNetwork(retrofit: Retrofit): GizmodoNetwork {
        return retrofit.create(GizmodoNetwork::class.java)
    }

    @Provides fun provideLayoutInflater(@ForApplication context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }
}