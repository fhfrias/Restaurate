package com.fjhidalgo.restaurante.ui.navigation.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper

class NavigationDrawerInteractorImpl
constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper)
    : BaseInteractorImpl(preferenceHelper, apiHelper), NavigationDrawerInteractor