package com.fjhidalgo.restaurante.module.bill_activity.view

import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface BillView: BaseView {
    fun okUpdate()
    fun errorUpdate()
    fun okGetTable(table: TableModel)
    fun errorGetTable()
    fun okPayment()
    fun errorPayment()
}