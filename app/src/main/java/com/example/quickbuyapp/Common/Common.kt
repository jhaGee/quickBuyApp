package com.example.quickbuyapp.Common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.quickbuyapp.R
import com.example.quickbuyapp.Services.MyFCMservices
import com.example.quickbuyapp.model.CategoryModel
import com.example.quickbuyapp.model.ProductModel
import com.example.quickbuyapp.model.TokenModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.random.Random as Random

object Common {
    fun productPrice(price: Double): String {

        if(price != 0.0){
            val df = DecimalFormat("#,##0.00")
            df.roundingMode = RoundingMode.HALF_UP
            val finalPrice = StringBuilder(df.format(price)).toString()
            return finalPrice//.replace(".",",")
        }
        else
            return "0.00"

    }

    fun createOrderNumber(): String {
        return StringBuilder()
            .append(System.currentTimeMillis())
            .append(Math.abs(java.util.Random().nextInt()))
            .toString()
    }

    fun updateToken(context: Context, token: String) {
        FirebaseDatabase.getInstance()
            .getReference(Common.TOKEN_REF)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(TokenModel(FirebaseAuth.getInstance().currentUser!!.email,token))
            .addOnFailureListener { e-> Toast.makeText(context,""+e.message,Toast.LENGTH_LONG).show() }
    }

    fun showNotification(context:Context, id: Int, title: String?, content: String?,intent: Intent?) {
        var pendingIntent: PendingIntent?=null
        if(intent!=null)
            pendingIntent= PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val NOTIFICATION_CHANNEL_ID= "com.example.quickBuy"
        val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationChannel= NotificationChannel(NOTIFICATION_CHANNEL_ID,
            "quickBuy",NotificationManager.IMPORTANCE_DEFAULT)

            notificationChannel.description= "quickBuy"
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor= (Color.RED)
            notificationChannel.vibrationPattern= longArrayOf(0,1000,500,1000)

            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder= NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle(title).setContentText(content).setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.ic_baseline_shopping_cart_24))
        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent)

        val notification= builder.build()

        notificationManager.notify(id,notification)

    }

    val NOTI_CONTENT: String?="content"
    val NOTI_TITLE: String?="title"
    private val TOKEN_REF:String="Tokens"
    fun getDateOfWeek(i: Int): String {
        when(i){
            1 -> return "Monday"
            2 -> return "Tuesday"
            3 -> return "Wednesday"
            4 -> return "Thursday"
            5 -> return "Friday"
            6 -> return "Saturday"
            7 -> return "Sunday"
            else -> return "Unkown Day"
        }
    }

    fun convertStatusToText(orderStatus: Int): String {
        return when(orderStatus){
            0 -> "Placed"
            1 -> "Shipping"
            2 -> "Shipped"
            -1 -> "Cancelled"
            else -> "Unkown"
        }
    }

    val ORDER_REF: String = "Order"
    var categorySelected:CategoryModel ?= null
    var productSelected:ProductModel ?= null
    val CATEGORY_REF: String="Category"
    val FULL_WIDTH_COLUMN: Int=1
    val BEST_DEAL_REF: String = "BestDeals"
    val POPULAR_REF: String = "MostPopular"
    val DEFAULT_COLUMN_COUNT:Int=0
}
