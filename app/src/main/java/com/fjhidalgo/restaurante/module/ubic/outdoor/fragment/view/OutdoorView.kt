package com.fjhidalgo.restaurante.module.ubic.outdoor.fragment.view

import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface OutdoorView: BaseView {
    fun okAddTable()
    fun errorAddTable()
    fun okDeleteTable()
    fun errorDeleteTable()
    fun setTables(tableList: List<TableModel>?)
}