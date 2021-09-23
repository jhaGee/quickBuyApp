package com.example.quickbuyapp.Callback

import com.example.quickbuyapp.model.CategoryModel

interface ICategoryCallBackListener {
    fun onCategoryLoadSuccess(categoriesList: List<CategoryModel>)
    fun onCategoryLoadFailed(message:String)
}