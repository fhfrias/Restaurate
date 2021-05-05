package com.fjhidalgo.restaurante.module.menu.delete_product.view

import android.os.Bundle
import android.util.Log
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
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.adapter.MenuItemAdapter
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.listener.MenuItemTouchListener
import com.fjhidalgo.restaurante.module.menu.delete_product.adapter.DeleteProductAdapter
import com.fjhidalgo.restaurante.module.menu.delete_product.interactor.DeleteProductInteractor
import com.fjhidalgo.restaurante.module.menu.delete_product.interactor.DeleteProductInteractorImpl

import com.fjhidalgo.restaurante.module.menu.delete_product.presenter.DeleteProductPresenter
import com.fjhidalgo.restaurante.module.menu.delete_product.presenter.DeleteProductPresenterImpl
import com.fjhidalgo.restaurante.ui.dialog.WarningDialog
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.radiobutton.MaterialRadioButton

class DeleteProductActivity: AppCompatActivity(), DeleteProductView {

    val presenter: DeleteProductPresenter<DeleteProductView, DeleteProductInteractor> by lazy {
        DeleteProductPresenterImpl(DeleteProductInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var appBarLayout: CustomAppBarLayout? = null
    private var spinnerType: Spinner? = null
    private var rdbFood: MaterialRadioButton? = null
    private var recyclerView: RecyclerView? = null
    private var adapterDeleteProduct: DeleteProductAdapter? = null
    private var listProducts: List<ProductModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_product)
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
            it.title = getString(R.string.delete_product_title)
        }
    }

    override fun setProducts(productResponse: ProductResponse) {

        if (productResponse.products != null){
            if (listProducts == null ){
                listProducts = productResponse.products
                setupRecyclerView()
            } else {
                adapterDeleteProduct!!.productList = productResponse.products!!
                adapterDeleteProduct!!.notifyDataSetChanged()
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

        adapterDeleteProduct = DeleteProductAdapter(this, layoutInflater, listProducts!!, this)
        recyclerView?.adapter = adapterDeleteProduct
        adapterDeleteProduct?.notifyDataSetChanged()

    }

    fun deleteProduct(id: String, linkImage: String, position: Int){

        val warningDialog: WarningDialog = WarningDialog.newInstance(getString(R.string.title_warning_delete), getString(R.string.txt_warning_delete), object : WarningDialog.OnDialogButtonClicked{
            override fun onAcceptButtonClicked(dialog: DialogFragment) {

                if (rdbFood!!.isChecked){
                    presenter.deleteFood(id, spinnerType!!.selectedItem.toString(), linkImage)
                } else {
                    presenter.deleteDrink(id, spinnerType!!.selectedItem.toString(), linkImage)
                }
                dialog.dismiss()
            }
        })
        warningDialog.show(supportFragmentManager, "warningDialog")

    }

    override fun okDeleted() {
        Toast.makeText(this, getString(R.string.msg_succesfull_delete), Toast.LENGTH_LONG).show()
        if (rdbFood!!.isChecked) {
            presenter.getFood(spinnerType!!.selectedItem.toString())
        } else {
            presenter.getDrink(spinnerType!!.selectedItem.toString())
        }
    }

    override fun errorDeleted() {
        Toast.makeText(this, getString(R.string.msg_error_delete), Toast.LENGTH_LONG).show()
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
}