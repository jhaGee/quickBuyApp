package com.example.quickbuyapp.Callback

import com.example.quickbuyapp.model.Order

interface ILoadTimeFromFirebaseCallback {
    fun onLoadTimeSucess(order : Order, estimatedTimeMs : Long)
    fun onLoadTimeFailure(message : String)
}