package com.fjhidalgo.restaurante.module.menu.users.presenter

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.menu.users.interactor.UsersInteractor
import com.fjhidalgo.restaurante.module.menu.users.view.UsersView
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDataUser
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import com.google.firebase.database.DataSnapshot

class UsersPresenterImpl<V: UsersView, I: UsersInteractor>(interactor:I): BasePresenterImpl<V, I>(interactor),
    UsersPresenter<V, I> {

    override fun getUsers() {
        interactor!!.getUsers(object : FirebaseCallbackDataUser{
            override fun onResponse(dataSnapshot: DataSnapshot) {
                var users: List<UserModel>? = null
                dataSnapshot.let {
                    users = dataSnapshot.children.map { snapShot ->
                        snapShot.getValue(UserModel::class.java)!!
                    }
                }
                if (users != null){
                    getView()?.setUsers(users!!)
                } else {
                    getView()?.setErrorUsers()
                }
            }

            override fun onError() {
                getView()?.setErrorUsers()
            }
        })
    }

    override fun updateIsAdmin(id: String, isAdmin: Boolean) {

        interactor!!.updateIsAdmin(id, isAdmin, object : FirebaseCallbackUpdate{

            override fun onResponse() {
                getView()?.updateAdminOK()
            }

            override fun onError() {
                getView()?.updateAdminError()
            }
        })
    }
}