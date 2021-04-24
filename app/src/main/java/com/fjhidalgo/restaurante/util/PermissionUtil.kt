package com.fjhidalgo.restaurante.util

import android.app.Activity
import android.content.Context


object PermissionUtil {

    fun requestPermission(activity: Activity) {

//        ActivityCompat.requestPermissions(activity,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.ACCESS_WIFI_STATE,
//                        Manifest.permission.CHANGE_WIFI_STATE,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.CAMERA),
//                AppConstants.RC_PERMISSIONS)
    }

    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
//            for (permission in permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false
//                }
//            }
//        }
        return true
    }
}
