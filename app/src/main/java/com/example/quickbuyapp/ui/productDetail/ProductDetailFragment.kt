package com.example.quickbuyapp.ui.productDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.andremion.counterfab.CounterFab
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.Database.CartDataSource
import com.example.quickbuyapp.Database.CartDatabase
import com.example.quickbuyapp.Database.CartItem
import com.example.quickbuyapp.Database.LocalCartDataSource
import com.example.quickbuyapp.EventBus.CountCartEvent
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class ProductDetailFragment : Fragment() {

    private lateinit var viewModel: ProductDetailViewModel
    private val compositeDisposable= CompositeDisposable()
    private lateinit var cartDataSource:CartDataSource

    private var img_product:ImageView ?= null
    private var btnCart:CounterFab?=null
    //private var btnRating:FloatingActionButton?=null
    private var product_name:TextView?=null
    private var product_description:TextView?=null
    private var product_price:TextView?=null
    //private var number_button:ElegantNumberButton?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        val root = inflater.inflate(R.layout.product_detail_fragment, container, false)
        initView(root)

        viewModel.getMutableLiveDataProduct().observe(viewLifecycleOwner, Observer {
            displayInfo(it)
        })
        return root
    }

    private fun displayInfo(it: ProductModel?) {
        Glide.with(requireContext()).load(it!!.image).into(img_product!!)
        product_name!!.text = StringBuilder(it!!.name)
        product_description!!.text = StringBuilder(it!!.description)
        product_price!!.text = StringBuilder(it!!.price)
        //calculateTotalPrice()
    }

    /*private fun calculateTotalPrice(){
        val totalPrice = Common.productSelected!!.price!!.toDouble()
        var displayPrice: Double

        // to get total price
        displayPrice = totalPrice*(number_button!!.number.toInt())
        displayPrice = Math.round(displayPrice*100.0)/100.0
        product_price!!.text = StringBuilder("").append(Common.productPrice(displayPrice)).toString()
    }*/

    private fun initView(root: View?) {

        cartDataSource= LocalCartDataSource(CartDatabase.getInstance(context).cartDAO())
        btnCart = root!!.findViewById(R.id.btnCart) as CounterFab
        img_product = root.findViewById(R.id.img_product) as ImageView
        //btnRating = root!!.findViewById(R.id.btn_rating) as FloatingActionButton
        product_name = root.findViewById(R.id.product_name) as TextView
        product_description = root.findViewById(R.id.product_description) as TextView
        product_price = root.findViewById(R.id.product_price) as TextView
        //number_button = root.findViewById(R.id.number_button_detail) as ElegantNumberButton

        btnCart!!.setOnClickListener {
            //calculateTotalPrice()
            val cartItem= CartItem()
            cartItem.uid= FirebaseAuth.getInstance().currentUser!!.uid
            cartItem.userPhone= FirebaseAuth.getInstance().currentUser!!.email

            cartItem.productId=Common.productSelected!!.item_id!!
            cartItem.productName=Common.productSelected!!.name
            cartItem.productImage=Common.productSelected!!.image
            cartItem.productPrice=Common.productSelected!!.price!!.toDouble()
            cartItem.productQuantity=1

            cartDataSource.getItemWithAllOptionsInCart(FirebaseAuth.getInstance().currentUser!!.uid,
                cartItem.productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<CartItem> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(cartItemFromDB: CartItem) {
                        if(cartItemFromDB.equals(cartItem)){

                            cartItemFromDB.productQuantity = cartItemFromDB.productQuantity+cartItem.productQuantity

                            cartDataSource.updatecart(cartItemFromDB)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : SingleObserver<Int> {
                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onSuccess(t: Int) {
                                        Toast.makeText(context,"Update Cart Success", Toast.LENGTH_LONG).show()
                                        EventBus.getDefault().postSticky(CountCartEvent(true))
                                    }

                                    override fun onError(e: Throwable) {
                                        Toast.makeText(context,"[UPDATE CART]"+e.message, Toast.LENGTH_LONG).show()
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
                            Toast.makeText(context,"[CART ERROR]"+e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }
    }
}