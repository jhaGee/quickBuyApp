package com.example.quickbuyapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.quickbuyapp.Database.CartDataSource
import com.example.quickbuyapp.Database.CartDatabase
import com.example.quickbuyapp.Database.CartItem
import com.example.quickbuyapp.Database.LocalCartDataSource
import com.example.quickbuyapp.EventBus.UpdateItemInCart
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.kodein.di.generic.contextFinder
import java.lang.StringBuilder

class MyCartAdapter(internal var context: Context,
                    internal var cartItems: List<CartItem>) :
    RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {

    internal var compositeDisposable:CompositeDisposable
    internal var cartDataSource:CartDataSource
    init {
        compositeDisposable= CompositeDisposable()
        cartDataSource= LocalCartDataSource(CartDatabase.getInstance(context).cartDAO())
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cart_item,parent ,false))
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        Glide.with(context).load(cartItems[position].productImage)
            .into(holder.img_cart)
        holder.txt_product_name.text= StringBuilder(cartItems[position].productName)
        holder.txt_product_price.text=StringBuilder().append(cartItems[position].productPrice)
        holder.number_button.number=cartItems[position].productQuantity.toString()
        //Event
        holder.number_button.setOnValueChangeListener { view, oldValue, newValue ->
            cartItems[position].productQuantity=newValue
            EventBus.getDefault().postSticky(UpdateItemInCart(cartItems[position]))
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun getItemAtPosition(pos: Int): CartItem {
        return cartItems[pos]
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img_cart:ImageView
        var txt_product_name:TextView
        var txt_product_price:TextView
        var number_button:ElegantNumberButton
        init {
            img_cart=itemView.findViewById(R.id.image_cart) as ImageView
            txt_product_name=itemView.findViewById(R.id.txt_product_name)
            txt_product_price=itemView.findViewById(R.id.txt_product_price)
            number_button=itemView.findViewById(R.id.number_button) as ElegantNumberButton
        }
    }
}