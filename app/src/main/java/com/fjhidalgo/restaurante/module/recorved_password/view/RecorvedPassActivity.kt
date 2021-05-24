package com.fjhidalgo.restaurante.module.recorved_password.view

import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.recorved_password.interactor.RecorvedPassInteractor
import com.fjhidalgo.restaurante.module.recorved_password.interactor.RecorvedPassInteractorImpl
import com.fjhidalgo.restaurante.module.recorved_password.presenter.RecorvedPassPresenter
import com.fjhidalgo.restaurante.module.recorved_password.presenter.RecorvedPassPresenterImpl
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RecorvedPassActivity: AppCompatActivity(), RecorvedPassView {

    private val presenter: RecorvedPassPresenter<RecorvedPassView, RecorvedPassInteractor> by lazy {
        RecorvedPassPresenterImpl<RecorvedPassView, RecorvedPassInteractor>(
            RecorvedPassInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl())
        )
    }

    private var appBarLayout: CustomAppBarLayout? = null
    private var etEmail: TextInputEditText? = null
    private var btnRecorved: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
        setContentView(R.layout.recorvedpass_activity)

        initView()
        setUpAppBarLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
        setContentView(R.layout.recorvedpass_activity)
    }

    private fun initView(){
        appBarLayout = findViewById(R.id.appPartnerBarLayout)
        etEmail = findViewById(R.id.etEmail)
        btnRecorved = findViewById(R.id.btnRecorvedPass)

        btnRecorved!!.setOnClickListener {

            if (Patterns.EMAIL_ADDRESS.matcher(etEmail!!.text!!.toString()).matches()){

                presenter.recorvedPass(etEmail!!.text.toString())

            } else {
                Toast.makeText(this, getString(R.string.email_no_valid_msg), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = getString(R.string.recorved_title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun recoveryPassOK() {
        Toast.makeText(this, getString(R.string.recovery_pass_ok), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun recoveryPassError() {
        Toast.makeText(this, getString(R.string.recovery_pass_error), Toast.LENGTH_LONG).show()
    }
}