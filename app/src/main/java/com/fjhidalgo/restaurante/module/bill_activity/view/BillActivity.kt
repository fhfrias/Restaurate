package com.fjhidalgo.restaurante.module.bill_activity.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.bill.BillModel
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.bill_activity.adapter.AdapterBill
import com.fjhidalgo.restaurante.module.bill_activity.interactor.BillInteractor
import com.fjhidalgo.restaurante.module.bill_activity.interactor.BillInteractorImpl
import com.fjhidalgo.restaurante.module.bill_activity.presenter.BillPresenter
import com.fjhidalgo.restaurante.module.bill_activity.presenter.BillPresenterImpl
import com.fjhidalgo.restaurante.ui.dialog.AddNoteDrinkDialog
import com.fjhidalgo.restaurante.ui.dialog.AddNoteFoodDialog
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.button.MaterialButton
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class BillActivity: AppCompatActivity(), BillView {

    val presenter: BillPresenter<BillView, BillInteractor> by lazy {
        BillPresenterImpl(BillInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var nameTable = ""
    private var idTable = ""
    private var typeTable = ""
    private var userName = ""

    private var appBarLayout: CustomAppBarLayout? = null
    private var recyclerView: RecyclerView? = null
    private var ll_attend_total: LinearLayout? = null
    private var nameBarman: TextView? = null
    private var total: TextView? = null
    private var btn_amount: MaterialButton? = null
    private var btn_payed: MaterialButton? = null
    private var btn_food: MaterialButton? = null
    private var btn_drink: MaterialButton? = null

    private var listBill: ArrayList<BillModel> = ArrayList<BillModel>()
    private var adapterBill: AdapterBill? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_activity)

        presenter.onAttach(this)

        val extras = intent.extras
        if( extras != null){
            nameTable = extras!!.getString("nameTable", "")
            idTable = extras!!.getString("idTable", "")
            typeTable = extras!!. getString("typeTable", "")
            userName = extras!!.getString("userName", "")
        }

        initView()
        setUpAppBarLayout()
        presenter.getTable(idTable, typeTable)
    }

    private fun initView(){


        appBarLayout = findViewById(R.id.appPartnerBarLayout)
        recyclerView = findViewById(R.id.recyclerView)
        ll_attend_total = findViewById(R.id.ll_attend_total)
        nameBarman = findViewById(R.id.name_barman)
        total = findViewById(R.id.tv_total)
        btn_amount = findViewById(R.id.btn_amount)
        btn_payed = findViewById(R.id.btn_payed)
        btn_food = findViewById(R.id.btn_food)
        btn_drink = findViewById(R.id.btn_drink)

        btn_amount!!.setOnClickListener {

            ll_attend_total!!.visibility = View.VISIBLE
            nameBarman!!.setText("")
            total!!.setText(calculateTotal())
            btn_amount!!.visibility = View.GONE
            btn_payed!!.visibility = View.VISIBLE
            btn_drink!!.isEnabled = false
            btn_food!!.isEnabled = false
            nameBarman!!.setText(userName)
            writeBill()
        }

        btn_payed!!.setOnClickListener {
            //PdfUtils.stringtopdf("Esto es una prueba", this)
            listBill = ArrayList<BillModel>()
            presenter!!.payTable(TableModel(nameTable, idTable, listBill), typeTable)
        }

        btn_food!!.setOnClickListener {
            val addFoodDialog = AddNoteFoodDialog.newInstance(object : AddNoteFoodDialog.OnAddDrinkDialogButtonClicked{
                override fun onAcceptButtonClicked(dialog: DialogFragment) {

                }

                override fun onCancelButtonClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                }

            })
            addFoodDialog.show(supportFragmentManager, "addFoodDialog")
        }

        btn_drink!!.setOnClickListener {
            val addDrinkDialog = AddNoteDrinkDialog.newInstance(object : AddNoteDrinkDialog.OnAddDrinkDialogButtonClicked{
                override fun onAcceptButtonClicked(dialog: DialogFragment) {

                }

                override fun onCancelButtonClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                }

            })
            addDrinkDialog.show(supportFragmentManager, "addFoodDialog")
        }
    }

    private fun calculateTotal(): String {

        var countTotal = 0.0

        for (element in listBill){
            countTotal = countTotal + element.amount!!.toDouble() * element.price!!
        }
        return countTotal.toString()
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

        adapterBill = AdapterBill(this, layoutInflater, listBill, this)
        recyclerView?.adapter = adapterBill
        adapterBill?.notifyDataSetChanged()

    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = nameTable
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

    fun addNoteToBill(product: ProductModel, amount: Int, typeProduct: String){

        var existProduct = false


        for (element in listBill){
            if(element.idProduct.equals(product.id)){
                element.amount = element.amount!! + amount
                existProduct = true
                break
            }
        }

        if (existProduct == false){
            val newBill = BillModel(product.name, amount, product.price!!.toDouble(), product.id , typeProduct)
            listBill.add(newBill)
        }
        val tableModel = TableModel(nameTable, idTable, listBill)
        presenter.updateTable(tableModel, typeTable)
    }

    override fun okUpdate() {
        Toast.makeText(this, getString(R.string.add_note_ok), Toast.LENGTH_LONG).show()
        adapterBill!!.billList = listBill
        adapterBill!!.notifyDataSetChanged()
    }

    override fun errorUpdate() {
        Toast.makeText(this, getString(R.string.no_add_note), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun okGetTable(table: TableModel) {

        if(table.billList != null){
            listBill = ArrayList(table.billList)
        }

        setupRecyclerView()
    }

    override fun errorGetTable() {
        Toast.makeText(this, getString(R.string.no_info_table), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun okPayment() {
        Toast.makeText(this, getString(R.string.payment_ok), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun errorPayment() {
        Toast.makeText(this, getString(R.string.error_payment_bill), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    fun writeBill() {

        val billFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath, "bill.txt")
        billFile.printWriter().use { out ->
            out.println("---------------------------- Restaurante ----------------------------") //nameRestaurant
            out.println("\n")
            out.println("\n")
            out.println("\n")
            out.println("Producto     Cantidad   Precio/U      Total")
            out.println("-----------------------------------------------------------------------------")
            out.println("\n")
            for(bill in listBill){

                var nameProduct = ""
                var lengthName = bill.name!!.length
                if ( bill.name!!.length > 10){
                     nameProduct = bill.name!!.substring(0, 9)
                    for(i in 0..(nameProduct.length - 1)){
                        if(nameProduct.get(i).toLowerCase().equals('l')){
                            nameProduct = nameProduct + " "
                        }
                    }
                } else {
                    var space = 10 - lengthName
                    nameProduct = bill.name!!
                    for(i in 1..space){
                        nameProduct = nameProduct + " "
                    }
                    for(i in 0..(nameProduct.length - 1)){
                        if(nameProduct.get(i).toLowerCase().equals('l')){
                            nameProduct = nameProduct + " "
                        }
                    }
                }
                var total = bill.amount!!.toDouble() * bill.price!!
                out.println(nameProduct + "       " + bill.amount + "        " + bill.price + "       " + total)

            }
            out.println("\n")
            out.println("\n")
            out.println("\n")
            out.println("Le atendio: " + userName)
            var totalBill = calculateTotal()
            out.println("TOTAL = " + totalBill + " EUR")
            val calendar = Calendar.getInstance()
            var day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            if (day.length == 1){
                day = "0" + day
            }
            var month = (calendar.get(Calendar.MONTH) + 1).toString()
            if (month.length == 1){
                month = "0" + month
            }
            val year = calendar.get(Calendar.YEAR)
            out.println("\n")
            out.println("\n")
            out.println("\n")
            out.println("Fecha : " + day + "/" + month + "/" + year)
        }
    }
}