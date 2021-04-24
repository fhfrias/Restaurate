package com.fjhidalgo.restaurante.ui.navigation.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.ui.navigation.interactor.NavigationDrawerInteractor
import com.fjhidalgo.restaurante.ui.navigation.view.NavigationDrawerView


class NavigationDrawerPresenterImpl<V : NavigationDrawerView, I : NavigationDrawerInteractor> constructor(interactor: I)
    : BasePresenterImpl<V, I>(interactor = interactor), NavigationDrawerPresenter<V, I>