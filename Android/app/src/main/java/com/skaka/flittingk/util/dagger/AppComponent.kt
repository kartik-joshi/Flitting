package com.skaka.flittingk.util.dagger

import com.skaka.flittingk.main.MainActivity
import com.skaka.flittingk.main.listing.ViewListingsFragment
import com.skaka.flittingk.main.profile.ViewProfileFragment
import com.skaka.flittingk.main.request.ViewRequestsFragment
import com.skaka.flittingk.util.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(f: ViewListingsFragment)
    fun inject(a: MyApplication)
    fun inject(viewRequestsFragment: ViewRequestsFragment)
    fun inject(viewProfileFragment: ViewProfileFragment)
    fun inject(mainActivity: MainActivity)
}