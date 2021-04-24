package com.fjhidalgo.restaurante.module.base.presenter

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.module.base.view.BaseActivityView
import com.fjhidalgo.restaurante.module.base.view.BaseView
import com.fjhidalgo.restaurante.util.PermissionUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder


abstract class BasePresenterImpl<V : BaseView, I : BaseInteractor> constructor(protected var interactor: I?)
    : BasePresenter<V, I> {

    internal var app: App = App.instance

    internal var gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    private var view: V? = null

    private val isViewAttached: Boolean
        get() = view != null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
        this.interactor = null
    }

    override fun getView(): V? {
        return this.view
    }

    /**
     * Pide los permisos de usuario
     *
     * @param activity referencia a la Activity que los pide
     */
    override fun requestPermissions(activity: Activity) {

        PermissionUtil.requestPermission(activity)
    }

    /**
     * Gestiona el resultado de la aceptación de permisos de usuario
     *
     * @param permissions  permisos
     * @param grantResults resultado de la aceptacion
     */
    override fun managePermissions(mView: BaseActivityView, TAG: String, permissions: Array<String>, grantResults: IntArray) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                var value: String? = if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    "Accepted"
                } else {
                    "Denied"
                }
                Log.d(TAG, "Permission " + value + " -> " + permissions[i])
                value = null
            }
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            mView.showPermissionsError("Permissions denied")
        }
    }
}
