package com.fjhidalgo.restaurante.module.bill_activity.interactor

import android.net.Uri
import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.util.firebase.*
import java.net.URI

class BillInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): BillInteractor, BaseInteractorImpl(preferenceHelper, apiHelper)  {

    override fun updateTable(tableModel: TableModel, typeTable: String, callback: FirebaseCallbackNote) {
        apiHelper!!.performUpdateTable(tableModel, typeTable, callback)
    }

    override fun getTable(idTable: String, typeTable: String, callback: FirebaseCallbackInfoTable) {
        apiHelper!!.performGetTableIngo(idTable, typeTable, callback)
    }
}