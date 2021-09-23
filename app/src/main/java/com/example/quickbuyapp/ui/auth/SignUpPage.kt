package com.example.quickbuyapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.ActivitySignUpPageBinding
import com.example.quickbuyapp.utils.startLoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpPage : AppCompatActivity(), AuthListener, KodeinAware {
    /*private lateinit var auth:FirebaseAuth
    private lateinit var rootRef:DatabaseReference
    lateinit var uid:String
    private lateinit var binding:ActivitySignUpPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_sign_up_page)
        binding.buttonSignup.setOnClickListener{
            var mName:String=binding.editTextPersonNameSignup.text.toString().trim()
            var mEmail=binding.editTextEmailAddressSignup.text.toString().trim()
            var mPassword =binding.editTextPasswordSignup.text.toString().trim()
            var mMobile =binding.editTextPhoneSignup.text.toString().trim()
            if(isValidEmail(mEmail)) {
                if (TextUtils.isEmpty(mName)) {
                    Toast.makeText(this@SignUpPage, "Enter Name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(this@SignUpPage, "Enter Email", Toast.LENGTH_SHORT).show()

                } else if (TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(this@SignUpPage, "Enter Password", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(mMobile)) {
                    Toast.makeText(this@SignUpPage, "Enter Mobile", Toast.LENGTH_SHORT).show()
                } else {
                    signUpusers(mName, mEmail, mPassword, mMobile)
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
            else{
                Toast.makeText(this@SignUpPage, "Email Invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun signUpusers(mName: String, mEmail: String, mPassword: String, mMobile: String) {
        auth=FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(mEmail,mPassword)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        uid=user.uid
                    }
                    rootRef=FirebaseDatabase.getInstance().getReference()
                    val userDetails= Users(mName,mEmail,mPassword,mMobile)
                    rootRef.addValueEventListener(object:ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            rootRef.child("users").child(uid).setValue(userDetails)
                        }
                    })
                    Toast.makeText(this@SignUpPage,"Sign up successful",Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.GONE
                }
                else{
                    Toast.makeText(this@SignUpPage,"User already exist!",Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.GONE
                }
            }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }*/

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    private  lateinit var binding: ActivitySignUpPageBinding

    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_page)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }
    override fun onStarted() {
        binding.progressBar.visibility = View.VISIBLE
        /*Intent(this, UserDashboard::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }*/
    }

    override fun onSuccess( message: String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startLoginActivity()
    }

    override fun onFailure( message: String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}