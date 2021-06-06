package com.fjhidalgo.restaurante.module.bill_activity.interactor

import android.net.Uri
import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackInfoTable
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackNote


interface BillInteractor: BaseInteractor {
    fun updateTable(tableModel: TableModel, typeTable: String, callback: FirebaseCallbackNote)
    fun getTable(idTable:String, typeTable: String, callback: FirebaseCallbackInfoTable)
}