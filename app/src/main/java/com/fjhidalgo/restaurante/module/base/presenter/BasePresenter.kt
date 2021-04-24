package com.fjhidalgo.restaurante.module.base.presenter

import android.app.Activity
import com.fjhidalgo.restaurante.module.base.view.BaseActivityView

interface BasePresenter<V, I> {

    fun getView(): V?

    fun onAttach(view: V)

    fun onDetach()

    fun requestPermissions(activity: Activity)

    /**
     * Gestiona el resultado de la aceptación de permisos de usuario
     *
     * @param permissions  permisos
     * @param grantResults resultado de la aceptacion
     */
    fun managePermissions(mView: BaseActivityView, TAG: String, permissions: Array<String>, grantResults: IntArray)
}
