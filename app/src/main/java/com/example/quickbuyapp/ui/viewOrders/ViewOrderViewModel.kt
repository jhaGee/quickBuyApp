package com.example.quickbuyapp.ui.viewOrders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.model.Order

class ViewOrderViewModel : ViewModel() {
    val mutableLiveDataOrderList : MutableLiveData<List<Order>> = MutableLiveData()
    fun setMutableLiveDataOrderList(orderList : List<Order>){
        mutableLiveDataOrderList.value = orderList
    }
}