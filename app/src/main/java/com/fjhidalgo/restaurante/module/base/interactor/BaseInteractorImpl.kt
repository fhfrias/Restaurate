package com.fjhidalgo.restaurante.core.app.interactor

import com.fjhidalgo.restaurante.data.model.Auth
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper


open class BaseInteractorImpl(var preferenceHelper: PreferenceHelper, protected var apiHelper: ApiHelper)
    : BaseInteractor {

    override val isUserLoggedIn: Boolean?
        get() {
            val auth = this.preferenceHelper.getUserPref(Auth::class.java)
            //return auth.accessToken != null
            return true
        }

    override val auth: Auth
        get() {
            return preferenceHelper.getUserPref(Auth::class.java)
        }

    override fun saveAuth(auth: Auth) {
        this.preferenceHelper.setUserPref(auth)
    }

    /***
     * Borra del SharedPreferences los datos de [Auth]
     */
    override fun removeAuth() {
        this.preferenceHelper.resetUserPref()
    }
}
