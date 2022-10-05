package com.geektech.permissions.local

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private var preferenceHelper: SharedPreferences =
        context.getSharedPreferences("pref",Context.MODE_PRIVATE)

    fun setBooleanValue(nameCell:String, value: Boolean){
        preferenceHelper.edit().putBoolean(nameCell,value).apply()

    }

    fun getBooleanValue(nameCell:String): Boolean{
        return preferenceHelper.getBoolean(nameCell,false)

    }
}