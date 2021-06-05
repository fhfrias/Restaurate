package com.fjhidalgo.restaurante.module.ubic.bar.fragment.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable

interface BarInteractor: BaseInteractor {

    fun addTable(newTable: TableModel, type: String, callback: FirebaseCallbackAddDeleteTable)
    fun deleteTable(numberTable: String, type: String, callback: FirebaseCallbackAddDeleteTable)
    fun getTables(type: String, callback: FirebaseCallback)
}