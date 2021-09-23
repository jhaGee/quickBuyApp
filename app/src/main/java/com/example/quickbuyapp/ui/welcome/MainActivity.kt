package com.example.quickbuyapp.ui.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.quickbuyapp.R
import com.example.quickbuyapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
                     }

            Handler().postDelayed({
                var context: Context =this@MainActivity
                var destinationActivity= WelcomePage::class.java
                var intent: Intent =Intent(context,destinationActivity)
                startActivity(intent)
                finish()
            },2000)

    }
}