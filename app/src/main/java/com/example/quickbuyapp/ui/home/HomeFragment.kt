package com.example.quickbuyapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingViewPager
import com.example.quickbuyapp.MyBestDealAdapter
import com.example.quickbuyapp.MyPopularCategoriesAdapter
import com.example.quickbuyapp.R
import dmax.dialog.SpotsDialog

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dialog: AlertDialog
    private var recyclerView:RecyclerView? = null
    private var viewPager: LoopingViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initView(root)

        // Bind Data
        homeViewModel.popularList.observe(viewLifecycleOwner , Observer {
            val listData = it
            val adapter = MyPopularCategoriesAdapter(requireContext(),listData)
            recyclerView!!.adapter = adapter
        })

        homeViewModel.bestDealList.observe(viewLifecycleOwner , Observer {
            dialog.dismiss()
            val adapter = MyBestDealAdapter(requireContext() , it , false)
            viewPager!!.adapter = adapter
        })

        return root
    }

    private fun initView(root:View){
        dialog = SpotsDialog.Builder().setContext(context)
            .setCancelable(false).build()
        dialog.show()
        viewPager = root.findViewById(R.id.viewpager) as LoopingViewPager
        recyclerView = root.findViewById(R.id.recycler_popular) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL, false)
    }

    override fun onResume() {
        super.onResume()
        viewPager!!.resumeAutoScroll()
    }

    /*override fun onPause() {
        super.onPause()
        viewPager!!.pauseAutoScroll()
    }*/
}