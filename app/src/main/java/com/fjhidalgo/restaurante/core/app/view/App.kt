package com.fjhidalgo.restaurante.core.app.view

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.core.app.interactor.AppInteractor
import com.fjhidalgo.restaurante.core.app.interactor.AppInteractorImpl
import com.fjhidalgo.restaurante.core.app.presenter.AppPresenter
import com.fjhidalgo.restaurante.core.app.presenter.AppPresenterImpl
import com.fjhidalgo.restaurante.data.model.Auth
import com.fjhidalgo.restaurante.data.network.ApiHelper
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelper
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class App : MultiDexApplication(), AppView {

    companion object {

        private val TAG = App::class.java.simpleName

        @get:Synchronized
        lateinit var instance: App
    }

    private var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    private val presenter: AppPresenter<AppView, AppInteractor> by lazy {
        AppPresenterImpl<AppView, AppInteractor>(
            AppInteractorImpl(
                PreferenceHelperImpl(
                    this,
                    AppConstants.PREF_NAME
                ), ApiHelperImpl()
            )
        )
    }

    val preferenceHelper: PreferenceHelper by lazy {
        PreferenceHelperImpl(this, AppConstants.PREF_NAME)
    }

    private val gson: Gson by lazy {
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    val apiHelper: ApiHelper by lazy {
        ApiHelperImpl()
    }

    //Podría ir el listado de los enseres aquí
    //var customerInfo: CustomerResponse = CustomerResponse()

    override fun onCreate() {
        super.onCreate()

        instance = this

        presenter.onAttach(this)

        inicializarFirebase()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    override fun onTerminate() {
        //unregisterReceiver(wifiBroadcastReceiver)
        presenter.onDetach()
        super.onTerminate()
    }

    override fun onAuthorizeError() {

    }

    override fun onLoginInfoResponseSuccess(auth: Auth) {

    }

    private fun inicializarFirebase() {
        FirebaseApp.initializeApp(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase!!.setPersistenceEnabled(true)
        databaseReference = firebaseDatabase!!.reference
    }

}