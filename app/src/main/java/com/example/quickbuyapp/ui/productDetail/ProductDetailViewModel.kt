package com.example.quickbuyapp.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.model.ProductModel


class ProductDetailViewModel : ViewModel() {

    private var mutableLiveDataProduct:MutableLiveData<ProductModel>?=null

    fun getMutableLiveDataProduct():MutableLiveData<ProductModel>{
        if(mutableLiveDataProduct == null)
            mutableLiveDataProduct = MutableLiveData()
        mutableLiveDataProduct!!.value = Common.productSelected
        return mutableLiveDataProduct!!
    }

}