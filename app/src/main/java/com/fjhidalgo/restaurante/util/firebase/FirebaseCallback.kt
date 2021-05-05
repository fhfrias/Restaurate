package com.fjhidalgo.restaurante.util.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface FirebaseCallback {
    fun onResponse(task: Task<DataSnapshot>)
    fun onError(task: Task<DataSnapshot>)
}