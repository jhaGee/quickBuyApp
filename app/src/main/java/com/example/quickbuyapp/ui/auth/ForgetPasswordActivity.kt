package com.example.quickbuyapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.ActivityForgetPasswordBinding
import com.example.quickbuyapp.utils.startLoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ForgetPasswordActivity : AppCompatActivity(), AuthListener, KodeinAware {
    /*private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_forget_password)
        auth=FirebaseAuth.getInstance()
        binding.buttonSentEmail.setOnClickListener {
            var emailAddress: String =binding.editTextEmailAddressForget.text.toString().trim()
            if (isValidEmail(emailAddress)) {
                if (emailAddress != null) {
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Email Sent",
                                    Toast.LENGTH_SHORT
                                ).show()
                                var intent:Intent= Intent(this@ForgetPasswordActivity,
                                    WelcomePage::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Email not sent email does not exists!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }
    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }*/
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private  lateinit var binding: ActivityForgetPasswordBinding

    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }
    override fun onStarted() {
        binding.progressBarForget.visibility = View.VISIBLE
    }

    override fun onSuccess( message: String) {
        binding.progressBarForget.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startLoginActivity()
    }

    override fun onFailure(message: String) {
        binding.progressBarForget.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}