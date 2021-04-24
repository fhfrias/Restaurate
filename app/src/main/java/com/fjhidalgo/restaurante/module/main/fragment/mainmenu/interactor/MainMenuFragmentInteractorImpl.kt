package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.interactor

import com.fjhidalgo.restaurante.core.app.interactor.BaseInteractorImpl
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper


class MainMenuFragmentInteractorImpl
constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractorImpl(preferenceHelper, apiHelper), MainMenuFragmentInteractor {

}
