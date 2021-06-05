package com.fjhidalgo.restaurante.module.ubic.bar.fragment.presenter

import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.interactor.BarInteractor
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.view.BarView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackAddDeleteTable
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

class BarPresenterImpl<V: BarView, I: BarInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    BarPresenter<V, I> {

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
        interactor!!.getTables(AppConstants.BAR,object : FirebaseCallback{
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