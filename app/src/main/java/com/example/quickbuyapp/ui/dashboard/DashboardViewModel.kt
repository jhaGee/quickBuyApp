package com.example.quickbuyapp.ui.dashboard

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.quickbuyapp.model.repositories.UserRepository
import com.example.quickbuyapp.ui.auth.AuthListener
import com.example.quickbuyapp.utils.startLoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(private val repository: UserRepository) : ViewModel() {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text*/
    var password: String? = null
    var password1: String? = null
    var authListener: DashboardListener? = null
    private val disposables = CompositeDisposable()
    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View) {
        repository.logout()
        view.context.startLoginActivity()
    }
    fun change(view:View) {

        //validating email and password
        if ( password.isNullOrEmpty()||password1.isNullOrEmpty()) {
            Toast.makeText(view.context,"Enter Password",Toast.LENGTH_SHORT).show()
            return
        }
        //validating if both the password are equal
        if (!password.equals(password1)) {
            Toast.makeText(view.context,"Password doesn't match",Toast.LENGTH_SHORT).show()
            return
        }
        //calling login from repository to perform the actual authentication
        val disposable = repository.change(password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                Toast.makeText(view.context,"Password Changed",Toast.LENGTH_SHORT).show()
            }, {
                //sending a failure callback
                Toast.makeText(view.context,it.message!!,Toast.LENGTH_SHORT).show()
            })
        disposables.add(disposable)
    }
    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}