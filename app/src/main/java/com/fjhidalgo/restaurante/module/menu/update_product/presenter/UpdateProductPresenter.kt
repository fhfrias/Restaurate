package com.fjhidalgo.restaurante.module.menu.update_product.presenter

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter
import java.net.URI

interface UpdateProductPresenter<V,I>: BasePresenter<V, I> {
    fun getFood(typeFood: String)
    fun getDrink(typeDrink: String)
    fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?)
    fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?)
}