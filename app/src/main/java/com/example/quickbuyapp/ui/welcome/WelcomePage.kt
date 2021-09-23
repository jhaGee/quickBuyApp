package com.example.quickbuyapp.ui.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.ActivityWelcomePageBinding
import com.example.quickbuyapp.ui.auth.LoginPage
import com.example.quickbuyapp.ui.auth.SignUpPage

class WelcomePage : AppCompatActivity() {
    lateinit var mLoginButton: Button
    private lateinit var binding:ActivityWelcomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_welcome_page)
        binding.buttonSignup.setOnClickListener{
            var context:Context=this@WelcomePage
            var destinationActivity= SignUpPage::class.java
            var intent: Intent =Intent(context,destinationActivity)
            startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            var context:Context=this@WelcomePage
            var destinationActivity= LoginPage::class.java
            var intent: Intent =Intent(context,destinationActivity)
            startActivity(intent)
        }
    }
}