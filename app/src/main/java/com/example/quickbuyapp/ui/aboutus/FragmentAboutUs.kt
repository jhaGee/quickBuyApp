package com.example.quickbuyapp.ui.aboutus

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickbuyapp.R

class FragmentAboutUs : Fragment() {

    companion object {
        fun newInstance() = FragmentAboutUs()
    }

    private lateinit var viewModel: FragmentAboutUsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentAboutUsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}