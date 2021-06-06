package com.fjhidalgo.restaurante.data.network.module.table

import android.widget.Toast
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackInfoTable
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackNote

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

    override fun updateTable(tableModel: TableModel, typeTable: String, callback: FirebaseCallbackNote) {
        refTable.child(typeTable).child(tableModel.id.toString()).setValue(tableModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback.onResponse()
            } else {
                callback.onError()
            }
        }
            .addOnCanceledListener {
                callback.onError()
            }
    }

    override fun getInfoTable(
        idTable: String,
        typeTable: String,
        callback: FirebaseCallbackInfoTable
    ) {
        refTable.child(typeTable).child(idTable).get().addOnCompleteListener { task ->
            if(task.isSuccessful){

                callback.onResponse(task.result!!)

            } else {
                callback.onError()
            }
        }
    }
}