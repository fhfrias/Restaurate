package com.fjhidalgo.restaurante.module.recorved_password.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.module.login.interactor.LoginInteractor
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackRecoveryPass

class RecorvedPassInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractorImpl(preferenceHelper, apiHelper),
    RecorvedPassInteractor {

    override fun recorvedPass(email: String, callback: FirebaseCallbackRecoveryPass) {

        apiHelper!!.performRecoryPass(email, callback)
    }
}