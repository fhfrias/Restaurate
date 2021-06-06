package com.fjhidalgo.restaurante.module.bill_activity.presenter

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter
import java.net.URI

interface BillPresenter<V,I>: BasePresenter<V, I> {
    fun updateTable(tableModel: TableModel, typeTable: String)
    fun getTable(idTable: String, typeTable: String)
    fun payTable(tableModel: TableModel, typeTable: String)

}