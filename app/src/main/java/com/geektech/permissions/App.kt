package com.geektech.permissions

import android.app.Application
import com.geektech.permissions.local.PreferenceHelper

class App: Application() {

    companion object{
        lateinit var preferences: PreferenceHelper

    }

    override fun onCreate() {
        super.onCreate()
        initPreferenceHelper()
    }

    private fun initPreferenceHelper(){
        preferences = PreferenceHelper(this)

    }
}