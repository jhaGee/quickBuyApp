package com.example.quickbuyapp.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.Callback.ICategoryCallBackListener
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.model.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class MenuViewModel : ViewModel(), ICategoryCallBackListener {
    private var categoriesListMutable: MutableLiveData<List<CategoryModel>>?=null
    private var messageError: MutableLiveData<String> = MutableLiveData()
    private var categoryCallBackListener:ICategoryCallBackListener

    init{
        categoryCallBackListener=this
    }

    override fun onCategoryLoadSuccess(categoriesList: List<CategoryModel>) {
        categoriesListMutable!!.value=categoriesList
    }

    override fun onCategoryLoadFailed(message: String) {
        messageError.value=message
    }

    fun getCategoryList(): MutableLiveData<List<CategoryModel>> {
        if(categoriesListMutable == null){
            categoriesListMutable = MutableLiveData()
            loadCategory()
        }
        return categoriesListMutable!!
    }

    fun getMessageError(): MutableLiveData<String> {
        return messageError
    }

    private fun loadCategory() {
        val tempList = ArrayList<CategoryModel>()
        val categoryRef = FirebaseDatabase.getInstance().getReference(Common.CATEGORY_REF)
        categoryRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                categoryCallBackListener.onCategoryLoadFailed(error.message!!)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(itemSnapShot in snapshot!!.children)
                {
                    val model = itemSnapShot.getValue<CategoryModel>(CategoryModel::class.java)
                    model!!.item_id=itemSnapShot.key
                    tempList.add(model!!)
                }
                categoryCallBackListener.onCategoryLoadSuccess(tempList)
            }
        })
    }

}