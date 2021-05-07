package com.fjhidalgo.restaurante.module.menu.update_product.view

import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface UpdateProductView: BaseView {
    fun okUpdate()
    fun errorUpdate()
    fun setProducts(productResponse: ProductResponse)
}