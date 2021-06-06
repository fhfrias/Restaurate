package com.fjhidalgo.restaurante.module.bill_activity.presenter

import android.net.Uri
import android.util.Log
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.bill_activity.interactor.BillInteractor
import com.fjhidalgo.restaurante.module.bill_activity.view.BillView
import com.fjhidalgo.restaurante.module.menu.update_product.interactor.UpdateProductInteractor
import com.fjhidalgo.restaurante.module.menu.update_product.view.UpdateProductView
import com.fjhidalgo.restaurante.util.firebase.*
import com.fjhidalgo.restaurante.util.volley.VolleyCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import java.net.URI

class BillPresenterImpl<V: BillView, I: BillInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    BillPresenter<V, I> {

    override fun updateTable(tableModel: TableModel, typeTable: String) {
        interactor!!.updateTable(tableModel, typeTable, object : FirebaseCallbackNote{
            override fun onResponse() {
                getView()?.okUpdate()
            }

            override fun onError() {
                getView()?.errorUpdate()
            }
        })
    }

    override fun getTable(idTable: String, typeTable: String) {
        interactor!!.getTable(idTable, typeTable, object : FirebaseCallbackInfoTable{
            override fun onResponse(dataSnapshot: DataSnapshot) {
                var table: TableModel? = null
                dataSnapshot.let {
                    table = dataSnapshot.getValue(TableModel::class.java)!!
                }
                getView()?.okGetTable(table!!)
            }

            override fun onError() {
                getView()?.errorGetTable()
            }
        })
    }

    override fun payTable(tableModel: TableModel, typeTable: String) {
        interactor!!.updateTable(tableModel, typeTable, object : FirebaseCallbackNote{
            override fun onResponse() {
                getView()?.okPayment()
            }

            override fun onError() {
                getView()?.errorPayment()
            }
        })
    }
}