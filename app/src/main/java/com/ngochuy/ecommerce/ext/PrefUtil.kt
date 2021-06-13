package com.ngochuy.ecommerce.ext

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.User


//KEY WORD

const val USER_PROFILE = "USER"

class PrefUtil constructor(
    private val context: Context,
    private val prefs: SharedPreferences,
    private val gSon: Gson
) {
    fun clearAllData() = prefs.edit().clear().commit()

    var user: User?
        get() {
            return try {
                gSon.fromJson(
                    prefs.getString(USER_PROFILE, null),
                    User::class.java
                )
            } catch (e: Exception) {
                null
            }
        }
        set(value) = prefs.edit().putString(
            USER_PROFILE,
            gSon.toJson(value)
        ).apply()
}

const val PREFS_NAME = "PREFERENCES"
var USER_ID = -1
var CHECK_FINGER = false
var USER_TOUCHID = ""
var PASS_TOUCHID = ""

fun Context.removeValueSharePrefs(KEY_NAME: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()
    editor.remove(KEY_NAME)
    editor.apply()
}

fun Context.getIntPref(valueName: String): Int {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getInt(valueName, -1)
}

fun Context.getBooleanPref(valueName: String): Boolean {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getBoolean(valueName, false)
}


var Context.darkMode
    get() = this.getIntPref("THEME")
    set(value) = this.setIntPref("THEME", value)

fun Context.setIntPref(valueName: String, value: Int) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putInt(valueName, value)
    editor.apply()
}


fun Context.getStringPref(valueName: String): String? {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getString(valueName,"")
}

fun Context.setStringPref(valueName: String, value: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(valueName, value)
    editor.apply()
}

fun Context.setBooleanPref(valueName: String, value: Boolean) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(valueName, value)
    editor.apply()
}


fun isBiometricHardWareAvailable(con: Context):Boolean {
    var result=false
    val biometricManager = BiometricManager.from(con)
    when (biometricManager.canAuthenticate()) {
        BiometricManager.BIOMETRIC_SUCCESS ->result=true
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->result=false
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> result=false
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->result=false
    }
    return result
}


fun deviceHasPasswordPinLock(con: Context):Boolean {
    val keymgr=con.getSystemService(AppCompatActivity.KEYGUARD_SERVICE) as KeyguardManager
    if(keymgr.isKeyguardSecure)
        return true
    return false
}