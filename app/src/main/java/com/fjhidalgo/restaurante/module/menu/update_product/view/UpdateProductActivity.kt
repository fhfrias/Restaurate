package com.fjhidalgo.restaurante.module.menu.update_product.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.menu.update_product.adapter.UpdateProductAdapter
import com.fjhidalgo.restaurante.module.menu.update_product.interactor.UpdateProductInteractor
import com.fjhidalgo.restaurante.module.menu.update_product.interactor.UpdateProductInteractorImpl
import com.fjhidalgo.restaurante.module.menu.update_product.presenter.UpdateProductPresenter
import com.fjhidalgo.restaurante.module.menu.update_product.presenter.UpdateProductPresenterImpl
import com.fjhidalgo.restaurante.ui.dialog.UpdateProductDialog
import com.fjhidalgo.restaurante.ui.dialog.WarningDialog
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.radiobutton.MaterialRadioButton

class UpdateProductActivity: AppCompatActivity(), UpdateProductView {

    val presenter: UpdateProductPresenter<UpdateProductView, UpdateProductInteractor> by lazy {
        UpdateProductPresenterImpl(UpdateProductInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var appBarLayout: CustomAppBarLayout? = null
    private var spinnerType: Spinner? = null
    private var rdbFood: MaterialRadioButton? = null
    private var recyclerView: RecyclerView? = null
    private var adapterUpdateProduct: UpdateProductAdapter? = null
    private var listProducts: List<ProductModel>? = null
    private var fragmentDialog: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_product)
        presenter.onAttach(this)

        initView()
        setUpAppBarLayout()
    }

    private fun initView(){
        appBarLayout = findViewById(R.id.appPartnerBarLayout)
        recyclerView = findViewById(R.id.recyclerView)
        rdbFood = findViewById(R.id.rdbFood)

        spinnerType = findViewById(R.id.spinnerType)
        spinnerType!!.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.type_food))
        spinnerType!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (rdbFood!!.isChecked) {
                    presenter.getFood(spinnerType!!.selectedItem.toString())
                } else {
                    presenter.getDrink(spinnerType!!.selectedItem.toString())
                }
            }

        }

        rdbFood!!.isChecked = true
        rdbFood!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                spinnerType!!.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.type_food))
            } else {
                spinnerType!!.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.type_drink))
            }
        }

    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = getString(R.string.update_product_title)
        }
    }

    override fun setProducts(productResponse: ProductResponse) {

        if (productResponse.products != null){
            if (listProducts == null ){
                listProducts = productResponse.products
                setupRecyclerView()
            } else {
                adapterUpdateProduct!!.productList = productResponse.products!!
                adapterUpdateProduct!!.notifyDataSetChanged()
            }

        } else {
            Toast.makeText(this, getString(R.string.no_load_product_selected), Toast.LENGTH_LONG).show()
        }

    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView?.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(false)
            it.itemAnimator = DefaultItemAnimator()

            setAdapter()

        }

    }

    private fun setAdapter() {

        adapterUpdateProduct = UpdateProductAdapter(this, layoutInflater, listProducts!!, this)
        recyclerView?.adapter = adapterUpdateProduct
        adapterUpdateProduct?.notifyDataSetChanged()

    }


    fun updateProduct(item: ProductModel, position: Int){

        val updateProductDialog: UpdateProductDialog = UpdateProductDialog.newInstance(item, rdbFood!!.isChecked, spinnerType!!.selectedItem.toString(), object : UpdateProductDialog.OnUpdateDialogButtonClicked{
            override fun onAcceptButtonClicked(dialog: DialogFragment, product: ProductModel, isFood: Boolean, type: String, uriProduct: Uri?) {

                if(isFood){
                    presenter.updateFood(product, type, uriProduct)

                } else {
                    presenter.updateDrink(product, type, uriProduct)
                }
                dialog.dismiss()
            }

            override fun onCancelButtonClicked(dialog: DialogFragment) {
                dialog.dismiss()
            }
        })
        fragmentDialog = updateProductDialog
        updateProductDialog.show(supportFragmentManager, "updateProductDialog")

    }

    override fun okUpdate() {
        Toast.makeText(this, getString(R.string.msg_succesfull_update), Toast.LENGTH_LONG).show()
        if (rdbFood!!.isChecked) {
            presenter.getFood(spinnerType!!.selectedItem.toString())
        } else {
            presenter.getDrink(spinnerType!!.selectedItem.toString())
        }
    }

    override fun errorUpdate() {
        Toast.makeText(this, getString(R.string.msg_error_update), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        fragmentDialog?.onActivityResult(requestCode, resultCode, data)
    }
}