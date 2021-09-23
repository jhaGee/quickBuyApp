package com.example.quickbuyapp.ui.auth

import android.view.View


interface AuthListener {
    fun onStarted()
    fun onSuccess( message: String)
    fun onFailure( message: String)
}