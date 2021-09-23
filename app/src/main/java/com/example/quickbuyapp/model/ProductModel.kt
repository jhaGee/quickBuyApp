package com.example.quickbuyapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductModel(
    var description:String?=null,
    var name:String?=null,
    var item_id:String?=null,
    var image:String?=null,
    var price:String?=null,
    var positionInList : Int = 1
)
