package com.fjhidalgo.restaurante.module.menu.users.presenter

import com.fjhidalgo.restaurante.module.base.presenter.BasePresenter

interface UsersPresenter<V,I>: BasePresenter<V, I> {

    fun getUsers()
    fun updateIsAdmin(id: String, isAdmin: Boolean)
}