/*
 * Copyright 2016 Pedro Rodrigues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @Provides @Singleton fun provideApplicationContext(): Context {
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

    @Provides fun provideLayoutInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }
}