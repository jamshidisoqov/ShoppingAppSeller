package uz.gita.firebasesample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.firebasesample.repository.firebase.StoreRepository
import uz.gita.firebasesample.repository.firebase.StoreRepositoryImpl
import uz.gita.firebasesample.repository.local.Repository
import uz.gita.firebasesample.repository.local.impl.RepositoryImpl

// Created by Jamshid Isoqov an 9/17/2022

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindStore(storeRepositoryImpl: StoreRepositoryImpl): StoreRepository

    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}