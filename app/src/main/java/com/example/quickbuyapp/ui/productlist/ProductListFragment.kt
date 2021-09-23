package com.example.quickbuyapp.ui.productlist

import android.app.SearchManager
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbuyapp.Adapter.MyProductListAdapter
import com.example.quickbuyapp.Common.Common
import com.example.quickbuyapp.R
import com.example.quickbuyapp.model.ProductModel
import java.util.ArrayList

class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductListViewModel

    var recycler_product_list : RecyclerView ?= null
    var layoutAnimationController : LayoutAnimationController ?= null

    var adapter : MyProductListAdapter ?= null

    override fun onStop() {
        if(adapter != null)
            adapter!!.onStop()
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        val root = inflater.inflate(R.layout.product_list_fragment, container, false)

        initViews(root)

        viewModel.getMutableProductModelListData().observe(viewLifecycleOwner , Observer {
            adapter = MyProductListAdapter(requireContext(),it)
            recycler_product_list!!.adapter = adapter
            recycler_product_list!!.layoutAnimation = layoutAnimationController
        })

        return root
    }

    private fun initViews(root: View?) {

        setHasOptionsMenu(true) // Enables option menu on fragment

        recycler_product_list = root!!.findViewById(R.id.recycler_product_list) as RecyclerView
        recycler_product_list!!.setHasFixedSize(true)
        recycler_product_list!!.layoutManager = LinearLayoutManager(context)

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_item_from_left)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        // Create search view
        val menuItem = menu.findItem(R.id.action_search)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName!!))

        // Event
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(search: String?): Boolean {
                setSearchProduct(search!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        // Clear text when click to clear button
        val closeButton = searchView.findViewById<View>(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener{
            val ed = searchView.findViewById<View>(R.id.search_src_text) as EditText
            // Clear Text
            ed.setText("")
            // clear query
            searchView.setQuery("",false)
            // collapse the action view
            searchView.onActionViewCollapsed()
            // collapse the search widget
            menuItem.collapseActionView()
            // restore result to original
            viewModel.getMutableProductModelListData().value = Common.categorySelected!!.product
        }

    }

    private fun setSearchProduct(s : String) {
        val resultProduct : MutableList<ProductModel> = ArrayList()
        for(dcount in Common.categorySelected!!.product!!.indices){

            val productModel = Common.categorySelected!!.product!![dcount]

            if(productModel.name!!.toLowerCase().contains(s.toLowerCase())){

                // Here we'll save the index of search result item
                productModel.positionInList = dcount
                resultProduct.add(productModel)
            }
        }
        // Update the Search Result
        viewModel!!.getMutableProductModelListData().value = resultProduct
    }

}