package com.fjhidalgo.restaurante.module.ubic.indoor.fragment.presenter

import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface IndoorPresenter<V,I>: BasePresenter<V, I> {
    fun addTableIndoor(newTable: TableModel, type: String)
    fun deleteTableIndoor(numberTable: String, type: String)
    fun getTablesIndoor()
}