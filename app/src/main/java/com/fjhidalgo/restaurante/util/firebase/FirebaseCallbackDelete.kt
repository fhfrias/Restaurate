package com.fjhidalgo.restaurante.util.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

interface FirebaseCallbackDelete {
    fun onResponse()
    fun onError()
}