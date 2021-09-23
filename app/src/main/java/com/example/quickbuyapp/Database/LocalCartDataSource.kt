package com.example.quickbuyapp.Database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LocalCartDataSource(private val cartDAO: CartDAO):CartDataSource {
    override fun getAllCart(uid: String): Flowable<List<CartItem>> {

        return cartDAO.getAllCart(uid)
    }

    override fun countItemInCart(uid: String): Single<Int> {
        return cartDAO.countItemInCart(uid)
    }

    override fun sumPrice(uid: String): Single<Double> {
        return cartDAO.sumPrice(uid)

    }

    override fun getItemInCart(productId: String, uid: String): Single<CartItem> {
        return cartDAO.getItemInCart(productId,uid)
    }

    override fun insertorReplaceAll(vararg cartItem: CartItem): Completable {

        return cartDAO.insertorReplaceAll(*cartItem)

    }

    override fun updatecart(cart: CartItem): Single<Int> {
        return cartDAO.updatecart(cart)
    }

    override fun deleteCart(cart: CartItem): Single<Int> {
        return cartDAO.deleteCart(cart)
    }

    override fun cleanCart(uid: String): Single<Int> {
        return cartDAO.cleanCart(uid)
    }

    override fun getItemWithAllOptionsInCart(uid: String, productId: String): Single<CartItem> {
        return cartDAO.getItemWithAllOptionsInCart(uid, productId)
    }
}