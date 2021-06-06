package com.fjhidalgo.restaurante.ui.dialog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.drink.DrinkModel
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.module.bill_activity.adapter.AdapterNote
import com.fjhidalgo.restaurante.module.menu.update_product.adapter.UpdateProductAdapter
import com.fjhidalgo.restaurante.util.EditTextUtil
import com.fjhidalgo.restaurante.util.photo_product.PhotoProduct
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mikhaellopez.circularimageview.CircularImageView
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AddNoteDrinkDialog: DialogFragment() {

    companion object {
        fun newInstance( onAddDrinkDialogButtonClicked: OnAddDrinkDialogButtonClicked): AddNoteDrinkDialog {
            val dialog = AddNoteDrinkDialog()
            dialog.isCancelable = false
            dialog.onAddFoodDialogButtonClicked = onAddDrinkDialogButtonClicked
            return dialog
        }
    }

    interface OnAddDrinkDialogButtonClicked {
        fun onAcceptButtonClicked(dialog: DialogFragment)
        fun onCancelButtonClicked(dialog: DialogFragment)
    }

    //Database
    val refFood = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.FOOD_CHILD)
    val refDrink = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.DRINK_CHILD)

    private var onAddFoodDialogButtonClicked: OnAddDrinkDialogButtonClicked? = null

    private var spinnerTypeDrink: Spinner? = null
    private var btnCancel: ImageButton? = null
    private var recyclerView: RecyclerView? = null
    private var adapterNote: AdapterNote? = null

    private var listProducts: List<ProductModel> = ArrayList<ProductModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.dialog_note_add_drink, container, false)

        initView(rootView)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    private fun initView(view: View){

        spinnerTypeDrink = view.findViewById(R.id.spinnerTypeDrink)
        btnCancel = view.findViewById(R.id.btnCancel)
        recyclerView = view.findViewById(R.id.recyclerView)

        btnCancel!!.setOnClickListener {
            onAddFoodDialogButtonClicked?.onCancelButtonClicked(this)
        }

        spinnerTypeDrink!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getDrinkFromType(spinnerTypeDrink!!.selectedItem.toString())
            }

        }

        spinnerTypeDrink!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, requireActivity().resources.getStringArray(R.array.type_drink))
        setupRecyclerView()
        getDrinkFromType(spinnerTypeDrink!!.selectedItem.toString())
    }


    private fun getDrinkFromType(type: String){
        val productResponse = ProductResponse()
        refDrink.child(type).get().addOnCompleteListener { task ->
            if(task.isSuccessful){

                val result = task.result
                result?.let {
                    productResponse.products = result.children.map { snapShot ->
                        snapShot.getValue(ProductModel::class.java)!!
                    }
                }

                adapterNote!!.productList = productResponse.products!!
                adapterNote!!.notifyDataSetChanged()

            } else {

                Toast.makeText(requireContext(), getString(R.string.no_get_selected), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        recyclerView?.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(false)
            it.itemAnimator = DefaultItemAnimator()

            setAdapter()

        }

    }

    private fun setAdapter() {

        adapterNote = AdapterNote(requireContext(), layoutInflater, listProducts, requireActivity(), AppConstants.DRINK_CHILD)
        recyclerView?.adapter = adapterNote
        adapterNote?.notifyDataSetChanged()

    }

}