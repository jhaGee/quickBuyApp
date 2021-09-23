package com.example.quickbuyapp.utils

import android.content.Context
import android.content.Intent
import com.example.quickbuyapp.ui.auth.LoginPage
import com.example.quickbuyapp.ui.dashboard.UserDashboard


fun Context.startHomeActivity() =
    Intent(this,UserDashboard::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginPage::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }