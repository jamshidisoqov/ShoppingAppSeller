package uz.gita.firebasesample.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

// Created by Jamshid Isoqov an 9/18/2022
interface LoginViewModel {

    val errorFlow: Flow<Unit>

    fun login(login: String, password: String)

}