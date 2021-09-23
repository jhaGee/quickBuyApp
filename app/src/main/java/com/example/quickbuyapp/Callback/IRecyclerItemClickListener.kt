package com.example.quickbuyapp.Callback

import android.view.View

interface IRecyclerItemClickListener {
    fun onItemClick(view: View, pos: Int)
}