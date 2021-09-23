package com.example.quickbuyapp.model

import com.example.quickbuyapp.Database.CartItem

class Order {
    var userId : String? = null
    var userMailId : String? = null
    var shippingAddress : String? = null
    var transactionId : String? = null
    var finalPayment : Double = 0.0
    var totalPayment : Double = 0.0
    var isCod : Boolean = false
    var discount : Int = 0
    var cartItemList : List<CartItem>? = null
    var createDate: Long = 0
    var orderNumber:String? = null
    var orderStatus: Int = 0
}