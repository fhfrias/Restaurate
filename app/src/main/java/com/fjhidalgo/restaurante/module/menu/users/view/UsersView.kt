package com.fjhidalgo.restaurante.module.menu.users.view

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.view.BaseView

interface UsersView: BaseView {

    fun setUsers(listUserResponse: List<UserModel>)
    fun setErrorUsers()
    fun updateAdminOK()
    fun updateAdminError()
}