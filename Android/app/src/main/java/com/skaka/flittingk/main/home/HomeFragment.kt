package com.skaka.flittingk.main.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.skaka.flittingk.R
import com.skaka.flittingk.main.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("HomeFragment", "onCreateView (line 17): ")
        return layoutInflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).updateNavHeader()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeCard1.setOnClickListener {
            NavHostFragment.findNavController(this@HomeFragment)
                .navigate(R.id.action_homeFragment_to_viewRequestsFragment)
        }

        homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val args = Bundle()
                args.putString("query", p0)
                NavHostFragment.findNavController(this@HomeFragment)
                    .navigate(R.id.action_homeFragment_to_viewListingsFragment, args)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }
}