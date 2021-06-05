package com.fjhidalgo.restaurante.module.ubic.outdoor.fragment.presenter

import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface OutdoorPresenter<V,I>: BasePresenter<V, I> {
    fun addTableIndoor(newTable: TableModel, type: String)
    fun deleteTableIndoor(numberTable: String, type: String)
    fun getTablesIndoor()
}