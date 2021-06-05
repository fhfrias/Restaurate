package com.fjhidalgo.restaurante.module.ubic.indoor.fragment.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

class IndoorInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): IndoorInteractor, BaseInteractorImpl(preferenceHelper, apiHelper) {
    override fun addTable(
        newTable: TableModel,
        type: String,
        callback: FirebaseCallbackAddDeleteTable
    ) {
        apiHelper!!.performAddTable(newTable, type, callback)
    }

    override fun deleteTable(
        numberTable: String,
        type: String,
        callback: FirebaseCallbackAddDeleteTable
    ) {
        apiHelper!!.performDeleteTable(numberTable, type, callback)
    }

    override fun getTables(type: String, callback: FirebaseCallback) {
        apiHelper!!.performGetTables(type, callback)
    }
}