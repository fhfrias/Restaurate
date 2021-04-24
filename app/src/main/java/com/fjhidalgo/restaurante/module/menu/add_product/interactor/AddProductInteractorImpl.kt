package com.fjhidalgo.restaurante.module.menu.add_product.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper

class AddProductInteractorImpl(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper): AddProductInteractor, BaseInteractorImpl(preferenceHelper, apiHelper) {

}