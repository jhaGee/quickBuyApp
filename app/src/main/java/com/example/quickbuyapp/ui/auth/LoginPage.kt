package com.example.quickbuyapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.ActivityLoginPageBinding
import com.example.quickbuyapp.utils.startHomeActivity
import com.google.firebase.iid.FirebaseInstanceId
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginPage : AppCompatActivity(), AuthListener, KodeinAware {

    /*private lateinit var binding:ActivityLoginPageBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login_page)
        binding.buttonLogin.setOnClickListener {
            var mEmail =binding.editTextEmailAddressLogin.text.toString().trim()
            var mPassword =binding.editTextPasswordLogin.text.toString().trim()
            if (isValidEmail(mEmail)) {
                if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(this@LoginPage, "Enter Email", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(this@LoginPage, "Enter Password", Toast.LENGTH_SHORT).show()
                } else {
                    validateLogin(mEmail, mPassword)
                    binding.progressBarLogin.visibility = View.VISIBLE
                }
            }
            else{
                Toast.makeText(this@LoginPage, "Email Invalid", Toast.LENGTH_SHORT).show()
            }
        }
        binding.textViewForgetPassword.setOnClickListener {
            var intent:Intent= Intent(this@LoginPage,
                ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validateLogin(mEmail: String, mPassword: String) {
        auth=FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener (this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this@LoginPage,"Login in successful",Toast.LENGTH_SHORT).show()
                    val intent:Intent= Intent(this@LoginPage,
                        UserDashboard::class.java)
                    startActivity(intent)
                    binding.progressBarLogin.visibility=View.GONE
                }
                else{
                    Toast.makeText(this@LoginPage,"Login in unsuccesful",Toast.LENGTH_SHORT).show()
                    binding.progressBarLogin.visibility=View.GONE
                }
            }
    }
    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }*/
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    private  lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_login_page)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        binding.progressBarLogin.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressBarLogin.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        FirebaseInstanceId.getInstance()
            .instanceId
            .addOnFailureListener { e-> Toast.makeText(this,""+e.message,Toast.LENGTH_LONG).show()
                startHomeActivity()}
            .addOnCompleteListener {  task ->
                if (task.isSuccessful){
                    Common.updateToken(this, task.result!!.token)
                    startHomeActivity()
                }

            }

    }

    override fun onFailure(message: String) {
        binding.progressBarLogin.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            FirebaseInstanceId.getInstance()
                .instanceId
                .addOnFailureListener { e-> Toast.makeText(this,""+e.message,Toast.LENGTH_LONG).show()
                    startHomeActivity()}
                .addOnCompleteListener {  task ->
                    if (task.isSuccessful){
                        Common.updateToken(this, task.result!!.token)
                        startHomeActivity()
                    }
                }
        }
    }
}