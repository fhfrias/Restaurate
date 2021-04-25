package com.fjhidalgo.restaurante.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.food.FoodModel
import com.fjhidalgo.restaurante.util.EditTextUtil
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import java.io.File
import java.util.*

class AddFoodDialog: DialogFragment() {

    companion object {
        fun newInstance( onAddFoodDialogButtonClicked: OnAddFoodDialogButtonClicked): AddFoodDialog {
            val dialog = AddFoodDialog()
            dialog.isCancelable = false
            dialog.onAddFoodDialogButtonClicked = onAddFoodDialogButtonClicked
            return dialog
        }
    }

    interface OnAddFoodDialogButtonClicked {
        fun onAcceptButtonClicked(dialog: DialogFragment)
        fun onCancelButtonClicked(dialog: DialogFragment)
    }

    private var onAddFoodDialogButtonClicked: OnAddFoodDialogButtonClicked? = null

    private var etName: TextInputEditText? = null
    private var etPrice: TextInputEditText? = null
    private var spinnerTypeFood: Spinner? = null
    private var btnAddFood: MaterialButton? = null
    private var btnCancel: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.dialog_add_food, container, false)

        initView(rootView)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    private fun initView(view: View){

        etName = view.findViewById(R.id.etNameFood)
        etPrice = view.findViewById(R.id.etPriceFood)
        spinnerTypeFood = view.findViewById(R.id.spinnerTypefood)
        btnAddFood = view.findViewById(R.id.btnAccept)
        btnCancel = view.findViewById(R.id.btnCancel)

        //Subir imagen del producto
        //App.instance.storageReference!!.child("tapas")


        btnCancel!!.setOnClickListener {
            onAddFoodDialogButtonClicked?.onCancelButtonClicked(this)
        }

        btnAddFood!!.setOnClickListener {
            btnAddFood!!.isEnabled = false
            if(EditTextUtil.isCorrectFormatPrice(etPrice!! .text.toString())){
                val newFood = FoodModel(etName!!.text.toString(), etPrice!!.text.toString(), UUID.randomUUID().toString())
                App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.FOOD_CHILD).child(spinnerTypeFood!!.selectedItem.toString()).setValue(newFood)
                        .addOnCompleteListener {
                    Toast.makeText(requireContext(), getString(R.string.add_product_sucessfull), Toast.LENGTH_LONG).show()
                            this.dismiss()
                }
                btnAddFood!!.isEnabled = true
            } else {
                Toast.makeText(requireContext(), getString(R.string.price_no_valid_msg), Toast.LENGTH_LONG).show()
                btnAddFood!!.isEnabled = true
            }
        }

       spinnerTypeFood!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, requireActivity().resources.getStringArray(R.array.type_food))
    }

}