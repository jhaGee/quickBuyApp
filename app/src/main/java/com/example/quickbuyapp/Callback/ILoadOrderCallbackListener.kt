package com.example.quickbuyapp.Callback

import com.example.quickbuyapp.model.Order

interface ILoadOrderCallbackListener {
    fun onLoadOrderSuccess(orderList: List<Order>)
    fun onLoadOrderFailure(message:String)
}