package com.example.quickbuyapp.model.repositories

import com.example.quickbuyapp.model.firebasesource.FirebaseSource

class UserRepository (
    private val firebase: FirebaseSource
){
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun forget(email: String)=firebase.forget(email)

    fun change(password: String)=firebase.change(password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}