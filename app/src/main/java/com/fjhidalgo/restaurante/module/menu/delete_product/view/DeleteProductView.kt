package com.fjhidalgo.restaurante.module.menu.delete_product.view

import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface DeleteProductView: BaseView {
    fun setProducts(productResponse: ProductResponse)
    fun okDeleted()
    fun errorDeleted()
}