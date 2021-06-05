package com.fjhidalgo.restaurante.module.ubic.indoor.fragment.presenter

import android.util.Log
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.interactor.IndoorInteractor
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.view.IndoorView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

class IndoorPresenterImpl<V: IndoorView, I: IndoorInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    IndoorPresenter<V, I> {

    override fun addTableIndoor(newTable: TableModel, type: String) {
        interactor!!.addTable(newTable, type, object : FirebaseCallbackAddDeleteTable{
            override fun onResponse() {
                getView()?.okAddTable()
            }

            override fun onError() {
                getView()?.errorAddTable()
            }
        })
    }

    override fun deleteTableIndoor(numberTable: String, type: String) {
        interactor!!.deleteTable(numberTable, type, object : FirebaseCallbackAddDeleteTable{
            override fun onResponse() {
                getView()?.okDeleteTable()
            }

            override fun onError() {
                getView()?.errorDeleteTable()
            }
        })
    }

    override fun getTablesIndoor() {
        interactor!!.getTables(AppConstants.INDOOR,object : FirebaseCallback{
            override fun onResponse(task: Task<DataSnapshot>) {
                var tables: List<TableModel>? = null
                task.result.let {
                    tables = task.result!!.children.map { snapShot ->
                        snapShot.getValue(TableModel::class.java)!!
                    }
                }
                getView()?.setTables(tables)
            }

            override fun onError(task: Task<DataSnapshot>) {

            }
        })
    }
}