package com.skaka.flittingk.util

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.skaka.flittingk.model.Listing
import com.skaka.flittingk.model.Request
import com.skaka.flittingk.model.User
import com.skaka.flittingk.util.dagger.AppComponent
import com.skaka.flittingk.util.dagger.AppModule
import com.skaka.flittingk.util.dagger.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

object DI {
    lateinit var component: AppComponent
}

class MyApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
    }

    @Inject
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

        DI.component = component
        createDummyData()
    }

    @SuppressLint("CheckResult")
    private fun createDummyData() {
        val user1 = User(1, "user1@gmail.com", "user1", "1234567891", "")
        val user2 = User(2, "user2@gmail.com", "user2", "1234567892", "")
        val user3 = User(3, "user3@gmail.com", "user3", "1234567893", "")
        val user4 = User(4, "user4@gmail.com", "user4", "1234567894", "")

        val listing1 = Listing(
            1, 1, "Syracuse", "USA",
            "This is a sample description. Please don't pay heed to this. If you do, it's on you suckers!",
            ""
        )
        val listing2 = Listing(
            2, 1, "Liverpool", "USA",
            "This is a sample description. Please don't pay heed to this. If you do, it's on you suckers!",
            ""
        )
        val listing3 = Listing(
            3, 2, "Binghamton", "USA",
            "This is a sample description. Please don't pay heed to this. If you do, it's on you suckers!",
            ""
        )
        val listing4 = Listing(
            4, 2, "Rochester", "USA",
            "This is a sample description. Please don't pay heed to this. If you do, it's on you suckers!",
            ""
        )
        val listing5 = Listing(
            5, 4, "Buffalo", "USA",
            "This is a sample description. Please don't pay heed to this. If you do, it's on you suckers!",
            ""
        )

        val request1 = Request(
            1,
            3,
            1,
            "Hi, I'm moving here in a few days. It would be great if you could show me the ropes of this town.",
            false,
            "11 03 2018 06:00:00",
            "11 03 2018 19:00:00"
        )
        val request2 = Request(
            2,
            3,
            2,
            "Hi, I'm moving here in a few days. It would be great if you could show me the ropes of this town.",
            false,
            "11 03 2018 06:00:00",
            "11 03 2018 19:00:00"
        )
        val request3 = Request(
            3,
            4,
            3,
            "Hi, I'm moving here in a few days. It would be great if you could show me the ropes of this town.",
            false,
            "11 30 2018 08:00:00",
            "11 30 2018 18:00:00"
        )
        val request4 = Request(
            4,
            4,
            4,
            "Hi, I'm moving here in a few days. It would be great if you could show me the ropes of this town.",
            false,
            "11 30 2018 08:00:00",
            "11 30 2018 18:00:00"
        )
        val request5 = Request(
            5,
            4,
            5,
            "Hi, I'm moving here in a few days. It would be great if you could show me the ropes of this town.",
            false,
            "11 30 2018 08:00:00",
            "11 30 2018 18:00:00"
        )

        Observable.fromCallable {
            db.userDao().insertAll(user1, user2, user3, user4)
            db.listingDao().insertAll(listing1, listing2, listing3, listing4, listing5)
            db.requestDao().insertAll(request1, request2, request3, request4, request5)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("MyApplication", "createDummyData: dummy data loaded successfully!") },
                { it.printStackTrace() })
    }
}