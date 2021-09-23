package com.example.quickbuyapp.Callback

import com.example.quickbuyapp.model.PopularCategoryModel


interface IPopularLoadCallback {
    fun onPopularLoadSuccess(popularModelList: List<PopularCategoryModel>)
    fun onPopularLoadFailed(message:String)
}