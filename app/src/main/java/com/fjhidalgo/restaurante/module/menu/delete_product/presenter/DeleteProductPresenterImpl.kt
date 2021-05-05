package com.fjhidalgo.restaurante.module.menu.delete_product.presenter

import android.util.Log
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.menu.delete_product.interactor.DeleteProductInteractor
import com.fjhidalgo.restaurante.module.menu.delete_product.view.DeleteProductView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class DeleteProductPresenterImpl<V: DeleteProductView, I: DeleteProductInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    DeleteProductPresenter<V, I> {

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

    override fun deleteFood(id: String, typeFood: String, linkImage: String) {
        interactor!!.deleteFood(id, typeFood, linkImage, object : FirebaseCallbackDelete{
            override fun onResponse() {
                getView()!!.okDeleted()
            }

            override fun onError() {
                getView()!!.errorDeleted()
            }

        })
    }

    override fun deleteDrink(id: String, typeDrink: String, linkImage: String) {
        interactor!!.deleteDrink(id, typeDrink, linkImage, object : FirebaseCallbackDelete{
            override fun onResponse() {
                getView()!!.okDeleted()
            }

            override fun onError() {
                getView()!!.errorDeleted()
            }
        })
    }
}