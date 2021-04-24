package com.fjhidalgo.restaurante.module.menu.add_product.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.menu.add_product.interactor.AddProductInteractor
import com.fjhidalgo.restaurante.module.menu.add_product.view.AddProductView

class AddProductPresenterImpl <V: AddProductView, I: AddProductInteractor>(interactor:I): BasePresenterImpl<V,I>(interactor), AddProductPresenter<V,I> {
}