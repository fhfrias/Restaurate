package com.fjhidalgo.restaurante.module.base.view

interface BaseActivityView : BaseView {
    /**
     * Muestra los errores de la aceptación dep ermisos de ususario
     *
     * @param errorMsg mensaje de error
     */
    fun showPermissionsError(errorMsg: String)
}
