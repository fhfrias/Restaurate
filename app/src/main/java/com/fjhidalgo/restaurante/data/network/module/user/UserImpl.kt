package com.fjhidalgo.restaurante.data.network.module.user

import android.util.Log
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.google.firebase.database.*

class UserImpl: UserInterface {

    override fun getDataUser(email: String, callback: FirebaseCallbackDataUser) {

        val refUsers = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.USER_CHILD).get()
            .addOnCompleteListener { task ->

            if (task.isSuccessful){

                callback.onResponse(task.result!!)
            } else {
                callback.onError()
            }
        }
    }
}