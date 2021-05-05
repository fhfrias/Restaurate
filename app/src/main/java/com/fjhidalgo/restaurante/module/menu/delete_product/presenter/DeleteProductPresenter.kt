package com.fjhidalgo.restaurante.module.menu.delete_product.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface DeleteProductPresenter<V,I>: BasePresenter<V, I> {
    fun getFood(typeFood: String)
    fun getDrink(typeDrink: String)
    fun deleteFood(id: String, typeFood: String, linkImage: String)
    fun deleteDrink(id: String, typeDrink: String, linkImage: String)
}