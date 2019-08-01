package com.fintechplatform.ui.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.fintechplatform.ui.di.components.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.DaggerApplication
import dagger.android.HasFragmentInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {
    fun init(app: DaggerApplication) {
        DaggerAppComponent.builder().application(app)
                .build().inject(app)

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) { }

            override fun onActivityResumed(activity: Activity) { }

            override fun onActivityPaused(activity: Activity) { }

            override fun onActivityStopped(activity: Activity) { }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) { }

            override fun onActivityDestroyed(activity: Activity) { }
        })

    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(
                                        fm: FragmentManager,
                                        f: Fragment,
                                        savedInstanceState: Bundle?
                                ) {
                                    if (f is Injectable) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true
                    )
        }
    }
}