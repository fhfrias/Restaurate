package com.fjhidalgo.restaurante.module.recorved_password.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackRecoveryPass

interface RecorvedPassInteractor: BaseInteractor {

    fun recorvedPass(email: String, callback: FirebaseCallbackRecoveryPass)
}