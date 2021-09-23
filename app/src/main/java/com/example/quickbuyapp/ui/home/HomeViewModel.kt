package com.example.quickbuyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.Callback.IBestDealLoadCallback
import com.example.quickbuyapp.Callback.IPopularLoadCallback
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.model.BestDealModel
import com.example.quickbuyapp.model.PopularCategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class HomeViewModel : ViewModel() , IPopularLoadCallback, IBestDealLoadCallback {

    override fun onBestDealLoadSuccess(bestDealList: List<BestDealModel>) {
        bestDealListMutableLiveData!!.value = bestDealList
    }

    override fun onBestDealLoadFailed(message: String) {
        messageError.value = message
    }

    override fun onPopularLoadSuccess(popularModelList: List<PopularCategoryModel>) {
        popularListMutableLiveData!!.value = popularModelList
    }

    override fun onPopularLoadFailed(message: String) {
        messageError.value = message
    }

    private var popularListMutableLiveData: MutableLiveData<List<PopularCategoryModel>>? = null
    private var bestDealListMutableLiveData: MutableLiveData<List<BestDealModel>>? = null
    private lateinit var messageError: MutableLiveData<String>
    private var popularLoadCallbackListener: IPopularLoadCallback
    private var bestDealLoadCallbackListener: IBestDealLoadCallback

    val bestDealList: LiveData<List<BestDealModel>>
        get() {
            if (bestDealListMutableLiveData == null){
                bestDealListMutableLiveData = MutableLiveData()
                messageError = MutableLiveData()
                loadBestDealList()
            }
            return bestDealListMutableLiveData!!
        }

    private fun loadBestDealList() {
        val tempList = ArrayList<BestDealModel>()
        val bestDealRef = FirebaseDatabase.getInstance().getReference(Common.BEST_DEAL_REF)
        bestDealRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                bestDealLoadCallbackListener.onBestDealLoadFailed(error.message!!)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(itemSnapShot in snapshot!!.children)
                {
                    val model = itemSnapShot.getValue<BestDealModel>(BestDealModel::class.java)
                    tempList.add(model!!)
                }
                bestDealLoadCallbackListener.onBestDealLoadSuccess(tempList)
            }
        })
    }
    val popularList : LiveData<List<PopularCategoryModel>>
        get() {
            if(popularListMutableLiveData == null){
                popularListMutableLiveData = MutableLiveData()
                messageError = MutableLiveData()
                loadPopularList()
            }
            return popularListMutableLiveData!!
        }

    private fun loadPopularList() {
        val tempList = ArrayList<PopularCategoryModel>()
        val popularRef = FirebaseDatabase.getInstance().getReference(Common.POPULAR_REF)
        popularRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                popularLoadCallbackListener.onPopularLoadFailed(error.message!!)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(itemSnapShot in snapshot!!.children)
                {
                    val model = itemSnapShot.getValue<PopularCategoryModel>(PopularCategoryModel::class.java)

                    tempList.add(model!!)
                }
                popularLoadCallbackListener.onPopularLoadSuccess(tempList)
            }
        })
    }
    init {
        popularLoadCallbackListener = this
        bestDealLoadCallbackListener = this
    }
}