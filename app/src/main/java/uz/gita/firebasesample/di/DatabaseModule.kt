package uz.gita.firebasesample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.firebasesample.data.pref.MySharedPref
import uz.gita.firebasesample.data.pref.impl.MySharedPrefImpl
import javax.inject.Singleton

// Created by Jamshid Isoqov an 9/18/2022
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext ctx: Context): MySharedPref {
        return MySharedPrefImpl(ctx)
    }

}