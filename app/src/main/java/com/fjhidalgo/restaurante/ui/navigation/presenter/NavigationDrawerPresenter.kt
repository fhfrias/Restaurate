package com.fjhidalgo.restaurante.ui.navigation.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter
import com.fjhidalgo.restaurante.ui.navigation.interactor.NavigationDrawerInteractor
import com.fjhidalgo.restaurante.ui.navigation.view.NavigationDrawerView

interface NavigationDrawerPresenter<V : NavigationDrawerView, I : NavigationDrawerInteractor> :
    BasePresenter<V, I>