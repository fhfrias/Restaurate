package com.fjhidalgo.restaurante.module.menu.add_product.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.menu.add_product.interactor.AddProductInteractor
import com.fjhidalgo.restaurante.module.menu.add_product.interactor.AddProductInteractorImpl
import com.fjhidalgo.restaurante.module.menu.add_product.presenter.AddProductPresenter
import com.fjhidalgo.restaurante.module.menu.add_product.presenter.AddProductPresenterImpl
import com.fjhidalgo.restaurante.ui.dialog.AddDrinkDialog
import com.fjhidalgo.restaurante.ui.dialog.AddFoodDialog
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout

class AddProductActivity: AppCompatActivity(), AddProductView {

    val presenter: AddProductPresenter<AddProductView, AddProductInteractor> by lazy {
        AddProductPresenterImpl(AddProductInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var appBarLayout: CustomAppBarLayout? = null

    private var btnAddFood: ImageButton? = null
    private var btnAddDrink: ImageButton? = null
    private var fragmentDialog: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)
        presenter.onAttach(this)

        initViews()
        setUpAppBarLayout()

    }

    private fun initViews() {
        appBarLayout = findViewById(R.id.appPartnerBarLayout)
        btnAddFood = findViewById(R.id.btn_food)
        btnAddDrink = findViewById(R.id.btn_drink)

        btnAddFood!!.setOnClickListener {

            val addFoodDialog = AddFoodDialog.newInstance(object : AddFoodDialog.OnAddFoodDialogButtonClicked{
                override fun onAcceptButtonClicked(dialog: DialogFragment) {

                }

                override fun onCancelButtonClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                }

            })
            addFoodDialog.show(supportFragmentManager, "addFoodDialog")
            fragmentDialog = addFoodDialog
        }

        btnAddDrink!!.setOnClickListener {
            val addDrinkDialog = AddDrinkDialog.newInstance(object : AddDrinkDialog.OnAddDrinkDialogButtonClicked{
                override fun onAcceptButtonClicked(dialog: DialogFragment) {

                }

                override fun onCancelButtonClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                }

            })
            addDrinkDialog.show(supportFragmentManager, "addFoodDialog")
            fragmentDialog = addDrinkDialog
        }
    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = getString(R.string.add_product_title)
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

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        fragmentDialog?.onActivityResult(requestCode, resultCode, data)
    }
}