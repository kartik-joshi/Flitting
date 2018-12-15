package com.skaka.flittingk.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skaka.flittingk.R
import com.skaka.flittingk.model.UserDetail
import com.skaka.flittingk.model.UserProfile
import com.skaka.flittingk.util.BaseURLImage
import com.skaka.flittingk.util.DI
import com.skaka.flittingk.util.asyncAwait
import com.skaka.flittingk.util.launchAsync
import com.skaka.flittingk.util.retrofit.API
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var api: API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DI.component.inject(this)
        initNavController()
    }

    fun initNavController() {
        val finalHost = NavHostFragment.create(R.navigation.nav_graph)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, finalHost)
            .commit()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment),
            drawerLayout
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
//        setSupportActionBar(toolbar)

//        navView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navView, navController)
        navView.getHeaderView(0).helperImage.setOnClickListener {
            navController.navigate(
                R.id.viewProfileFragment2,
                null,
                NavOptions.Builder().setLaunchSingleTop(true).build()
            )
            drawerLayout.closeDrawers()
        }
    }

    fun updateNavHeader() {
        launchAsync {
            val profile = asyncAwait {
                api.getUser(4).execute().body() ?: UserProfile("", "", UserDetail("", "", ""))
            }
            setTitle("Welcome, Mr. ${profile.details.last_name}")

            Glide.with(this@MainActivity)
                .load(BaseURLImage + profile.image)
                .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.bird_white))
                .into(helperImage)
//
//            head.visibility = View.GONE
            listingDateText.text = profile.getName()
            helperEmailText.text = profile.details.email
        }
    }

    fun setTitle(title: String) {
        toolbar.title = title
    }
}