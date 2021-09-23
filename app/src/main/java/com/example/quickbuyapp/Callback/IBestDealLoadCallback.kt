package com.example.quickbuyapp.Callback

import com.example.quickbuyapp.model.BestDealModel

interface IBestDealLoadCallback {
    fun onBestDealLoadSuccess(bestDealList: List<BestDealModel>)
    fun onBestDealLoadFailed(message:String)
}