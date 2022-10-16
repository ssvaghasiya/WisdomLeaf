package com.example.wisdomleafdemo

import android.app.Application
import com.example.wisdomleafdemo.di.ApplicationComponent
import com.example.wisdomleafdemo.di.DaggerApplicationComponent

class MyApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}