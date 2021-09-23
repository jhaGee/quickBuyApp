package com.example.quickbuyapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.Order
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class MyOrderAdapter(private val context:Context ,
                    private val orderList:MutableList<Order>):
    RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        internal var img_order : ImageView? = null
        internal var txt_order_date : TextView? = null
        internal var txt_order_status : TextView? = null
        internal var txt_order_number : TextView? = null
        internal var txt_order_price : TextView? = null

        init {
            img_order = itemView.findViewById(R.id.img_order) as ImageView
            txt_order_date = itemView.findViewById(R.id.txt_order_date) as TextView
            txt_order_status = itemView.findViewById(R.id.txt_order_status) as TextView
            txt_order_number = itemView.findViewById(R.id.txt_order_number) as TextView
            txt_order_price = itemView.findViewById(R.id.txt_order_price) as TextView
        }
    }


    internal var calendar : Calendar = Calendar.getInstance()
    internal var simpleDateFormat : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context!!)
            .inflate(R.layout.layout_order_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context!!)
            .load(orderList[position].cartItemList!![0].productImage)
            .into(holder.img_order!!)
        calendar.timeInMillis = orderList[position].createDate
        val date = Date(orderList[position].createDate)
        holder.txt_order_date!!.text = StringBuilder(Common.getDateOfWeek(calendar.get(Calendar.DAY_OF_WEEK)))
            .append(" ")
            .append(simpleDateFormat.format(date))
        holder.txt_order_number!!.text = StringBuilder("Order Number : ").append(orderList[position].orderNumber)
        holder.txt_order_price!!.text =  StringBuilder("Order Price : ").append(orderList[position].finalPayment)
        holder.txt_order_status!!.text = StringBuilder("Status : ").append(Common.convertStatusToText(orderList[position].orderStatus))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun getItemAtPosition(position:Int):Order{
        return orderList[position]
    }

    fun setItemAtPosition(position: Int , orderModel : Order){
        orderList[position] = orderModel
    }

}