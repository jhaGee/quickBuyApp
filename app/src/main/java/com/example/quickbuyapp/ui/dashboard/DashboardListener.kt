package com.example.quickbuyapp.ui.dashboard

import android.view.View

interface DashboardListener {
    fun onStarted(view:View)
    fun onSuccess( view:View,message: String)
    fun onFailure( view:View,message: String)
}