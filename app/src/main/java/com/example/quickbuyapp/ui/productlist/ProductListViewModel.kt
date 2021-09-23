package com.example.quickbuyapp.ui.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.model.ProductModel


class ProductListViewModel : ViewModel() {
    private var mutableProductModelListData : MutableLiveData<List<ProductModel>> ?= null

    fun getMutableProductModelListData() :  MutableLiveData<List<ProductModel>>{
        if(mutableProductModelListData == null)
            mutableProductModelListData = MutableLiveData()
        mutableProductModelListData!!.value = Common.categorySelected!!.product
        return mutableProductModelListData!!
    }
}