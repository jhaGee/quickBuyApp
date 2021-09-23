package com.example.quickbuyapp.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.quickbuyapp.R
import com.example.quickbuyapp.databinding.FragmentLogoutBinding
import com.example.quickbuyapp.ui.dashboard.DashboardViewModel
import com.example.quickbuyapp.ui.dashboard.DashboardViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LogoutFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory :DashboardViewModelFactory by instance()
    private lateinit var viewModel:DashboardViewModel
    private lateinit var binding:FragmentLogoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this,factory).get(DashboardViewModel::class.java)
      /*  val root = inflater.inflate(R.layout.fragment_logout, container, false)*/
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_logout,container,false)
        //val textView: TextView = root.findViewById(R.id.text_slideshow)
       /* slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        binding.viewmodel = viewModel
        return binding.root
    }
}