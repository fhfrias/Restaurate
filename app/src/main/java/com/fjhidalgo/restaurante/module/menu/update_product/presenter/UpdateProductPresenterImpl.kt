package com.fjhidalgo.restaurante.module.menu.update_product.presenter

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.menu.update_product.interactor.UpdateProductInteractor
import com.fjhidalgo.restaurante.module.menu.update_product.view.UpdateProductView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import com.fjhidalgo.restaurante.util.volley.VolleyCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import java.net.URI

class UpdateProductPresenterImpl<V: UpdateProductView, I: UpdateProductInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    UpdateProductPresenter<V, I> {

    override fun getFood(typeFood: String) {
        val productResponse = ProductResponse()
        interactor!!.getFood(typeFood, object : FirebaseCallback{
            override fun onResponse(task: Task<DataSnapshot>) {
                val result = task.result
                result?.let {
                    productResponse.products = result.children.map { snapShot ->
                        snapShot.getValue(ProductModel::class.java)!!
                    }
                }
                getView()!!.setProducts(productResponse)
            }

            override fun onError(task: Task<DataSnapshot>) {
                productResponse.exception = task.exception
                getView()!!.setProducts(productResponse)
            }
        })
    }

    override fun getDrink(typeDrink: String) {
        val productResponse = ProductResponse()
        interactor!!.getDrink(typeDrink, object : FirebaseCallback{
            override fun onResponse(task: Task<DataSnapshot>) {
                val result = task.result
                result?.let {
                    productResponse.products = result.children.map { snapShot ->
                        snapShot.getValue(ProductModel::class.java)!!
                    }
                }
                getView()!!.setProducts(productResponse)
            }

            override fun onError(task: Task<DataSnapshot>) {
                productResponse.exception = task.exception
                getView()!!.setProducts(productResponse)
            }

        })
    }

    override fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?) {
        interactor!!.updateFood(updateProduct, type, uri, object : FirebaseCallbackUpdate{
            override fun onResponse() {
                getView()!!.okUpdate()
            }

            override fun onError() {
                getView()!!.errorUpdate()
            }

        })
    }

    override fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?) {
        interactor!!.updateDrink(updateProduct, type, uri, object : FirebaseCallbackUpdate{
            override fun onResponse() {
                getView()!!.okUpdate()
            }

            override fun onError() {
                getView()!!.errorUpdate()
            }

        })
    }
}