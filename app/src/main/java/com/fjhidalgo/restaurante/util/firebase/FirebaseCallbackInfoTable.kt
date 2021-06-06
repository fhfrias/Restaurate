package com.fjhidalgo.restaurante.util.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

interface FirebaseCallbackInfoTable {
    fun onResponse(dataSnapshot: DataSnapshot)
    fun onError()
}