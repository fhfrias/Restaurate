package com.fjhidalgo.restaurante.core.app.interactor

import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper


class AppInteractorImpl constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper)
    : BaseInteractorImpl(preferenceHelper, apiHelper), AppInteractor {

}