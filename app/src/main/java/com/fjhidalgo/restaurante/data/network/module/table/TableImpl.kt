package com.fjhidalgo.restaurante.data.network.module.table

import android.widget.Toast
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable

class TableImpl: TableInterface {

    //Database
    val refTable = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.UBIC)

    override fun addTable(
        newTable: TableModel,
        type: String,
        callbackAddDeleteTable: FirebaseCallbackAddDeleteTable
    ) {
        refTable.child(type).child(newTable.id.toString()).setValue(newTable).addOnCompleteListener {
            if(it.isSuccessful){
                callbackAddDeleteTable.onResponse()
            } else {
                callbackAddDeleteTable.onError()
            }
        }
        .addOnCanceledListener {
            callbackAddDeleteTable.onError()
        }
    }

    override fun deleteTable(
        numberTable: String,
        type: String,
        callbackAddDeleteTable: FirebaseCallbackAddDeleteTable
    ) {
        refTable.child(type).child(numberTable).removeValue().addOnCompleteListener { task ->
            if(task.isSuccessful){
                callbackAddDeleteTable.onResponse()
            } else {
                callbackAddDeleteTable.onError()
            }
        }
    }

    override fun getTables(type: String, callback: FirebaseCallback) {
        refTable.child(type).get().addOnCompleteListener { task ->
            if(task.isSuccessful){

                callback.onResponse(task)

            } else {
                callback.onError(task)
            }
        }
    }
}