package com.fjhidalgo.restaurante.core.app.interactor

import com.fjhidalgo.restaurante.data.model.Auth

interface BaseInteractor {

    /**
     * Comprueba si el usuario tiene los datos de [Auth] gurdados en SharedPreferences
     *
     * @return true si los tienes, false si no
     */
    val isUserLoggedIn: Boolean?

    /**
     * Obtiene la la referencia a [Auth] guardado en SharedPreferences
     *
     * @return reference to [Auth]
     */
    val auth: Auth

    /**
     * [BaseInteractor.getAuth]
     */
    fun saveAuth(auth: Auth)

    /***
     * Borra del SharedPreferences los datos de [Auth]
     */
    fun removeAuth()
}
