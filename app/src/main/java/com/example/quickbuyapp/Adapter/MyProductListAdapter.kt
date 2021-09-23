package com.example.quickbuyapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickbuyapp.Callback.IRecyclerItemClickListener
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.Database.CartDataSource
import com.example.quickbuyapp.Database.CartDatabase
import com.example.quickbuyapp.Database.CartItem
import com.example.quickbuyapp.Database.LocalCartDataSource
import com.example.quickbuyapp.EventBus.CountCartEvent
import com.example.quickbuyapp.EventBus.ProductItemClick
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MyProductListAdapter(internal var context: Context,
                           internal var productList: List<ProductModel>) :
    RecyclerView.Adapter<MyProductListAdapter.MyViewHolder>(){

    private val compositeDisposable:CompositeDisposable
    private val cartDataSource:CartDataSource

    init {
        compositeDisposable= CompositeDisposable()
        cartDataSource= LocalCartDataSource(CartDatabase.getInstance(context!!).cartDAO())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductListAdapter.MyViewHolder {
        return  MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_product_item ,
                parent , false))
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(productList.get(position).image).into(holder.item_image!!)
        holder.text_item_name!!.setText(productList.get(position).name)
        holder.text_item_price!!.setText(productList.get(position).price)

        // Event
        holder.setListener(object:IRecyclerItemClickListener{
            override fun onItemClick(view: View, pos: Int) {
                Common.productSelected = productList.get(pos)
                EventBus.getDefault().postSticky(ProductItemClick(true , productList.get(pos)))
            }
        })
        holder.image_cart!!.setOnClickListener {
            val cartItem= CartItem()
            cartItem.uid=FirebaseAuth.getInstance().currentUser!!.uid
            cartItem.userPhone=FirebaseAuth.getInstance().currentUser!!.email

            cartItem.productId=productList.get(position).item_id!!
            cartItem.productName=productList.get(position).name!!
            cartItem.productImage=productList.get(position).image!!
            cartItem.productPrice=productList.get(position).price!!.toDouble()
            cartItem.productQuantity=1

            cartDataSource.getItemWithAllOptionsInCart(FirebaseAuth.getInstance().currentUser!!.uid,
            cartItem.productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :SingleObserver<CartItem>{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(cartItemFromDB: CartItem) {
                        if(cartItemFromDB.equals(cartItem)){

                            cartItemFromDB.productQuantity = cartItemFromDB.productQuantity+cartItem.productQuantity

                            cartDataSource.updatecart(cartItemFromDB)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object :SingleObserver<Int>{
                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onSuccess(t: Int) {
                                        Toast.makeText(context,"Update Cart Success",Toast.LENGTH_LONG).show()
                                        EventBus.getDefault().postSticky(CountCartEvent(true))
                                    }

                                    override fun onError(e: Throwable) {
                                        Toast.makeText(context,"[UPDATE CART]"+e.message,Toast.LENGTH_LONG).show()
                                    }

                                })

                        }
                        else
                        {
                            //if item not available in database, just insert
                            compositeDisposable.add(cartDataSource.insertorReplaceAll(cartItem)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Toast.makeText(context, "Add to cart Success", Toast.LENGTH_LONG).show()
                                    //Here we will send a notify to UserDashboard to update Counterfab
                                    EventBus.getDefault().postSticky(CountCartEvent(true))
                                }, {
                                        t: Throwable? -> Toast.makeText(context, "[INSERT CART]"+t!!.message,
                                    Toast.LENGTH_LONG).show()
                                }))
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (e.message!!.contains("empty"))
                        {
                            compositeDisposable.add(cartDataSource.insertorReplaceAll(cartItem)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Toast.makeText(context, "Add to cart Success", Toast.LENGTH_LONG).show()
                                    //Here we will send a notify to UserDashboard to update Counterfab
                                    EventBus.getDefault().postSticky(CountCartEvent(true))
                                }, {
                                        t: Throwable? -> Toast.makeText(context, "[INSERT CART]"+t!!.message,
                                    Toast.LENGTH_LONG).show()
                                }))
                        }
                        else{
                            Toast.makeText(context,"[CART ERROR]"+e.message,Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }
    }

    fun onStop()
    {
        if(compositeDisposable != null){
            compositeDisposable.clear()
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var text_item_name : TextView? = null
        var text_item_price : TextView? = null
        var item_image : ImageView? = null
        var image_fav : ImageView? = null
        var image_cart : ImageView? = null

        internal var listener: IRecyclerItemClickListener?= null

        fun setListener(listener: IRecyclerItemClickListener){
            this.listener = listener
        }

        init {
            text_item_name = itemView.findViewById(R.id.text_item_name) as TextView
            text_item_price = itemView.findViewById(R.id.text_item_price) as TextView
            item_image = itemView.findViewById(R.id.item_image) as ImageView
            image_cart = itemView.findViewById(R.id.img_quick_cart) as ImageView
            // image_fav = itemView.findViewById(R.id.img_fav) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener!!.onItemClick(view!! , adapterPosition)
        }
    }
}