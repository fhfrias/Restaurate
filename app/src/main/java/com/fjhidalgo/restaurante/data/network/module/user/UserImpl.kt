package com.fjhidalgo.restaurante.data.network.module.user

import android.util.Log
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
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

    override fun updateIsAdminUser(id: String, isAdmin: Boolean, callback: FirebaseCallbackUpdate) {

        App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.USER_CHILD).child(id).child("admin").setValue(isAdmin).addOnCompleteListener {
            callback.onResponse()
        }
            .addOnCanceledListener {
                callback.onError()
            }
    }
}