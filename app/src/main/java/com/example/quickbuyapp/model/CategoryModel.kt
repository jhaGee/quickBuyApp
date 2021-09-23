package com.example.quickbuyapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CategoryModel(
    var item_id:String?=null,
    var name:String?=null,
    var image:String?=null,
    var product:List<ProductModel>?=null
)