package com.fjhidalgo.restaurante.data.network.module.table

import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable

interface TableInterface {
    fun addTable(newTable: TableModel, type: String, callbackAddDeleteTable: FirebaseCallbackAddDeleteTable)
    fun deleteTable(numberTable: String, type: String, callbackAddDeleteTable: FirebaseCallbackAddDeleteTable)
    fun getTables(type: String, callback: FirebaseCallback)
}