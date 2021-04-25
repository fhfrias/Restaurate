package com.fjhidalgo.restaurante.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import java.util.*

class AddDrinkDialog: DialogFragment() {

    companion object {
        fun newInstance( onAddDrinkDialogButtonClicked: OnAddDrinkDialogButtonClicked): AddDrinkDialog {
            val dialog = AddDrinkDialog()
            dialog.isCancelable = false
            dialog.onAddFoodDialogButtonClicked = onAddDrinkDialogButtonClicked
            return dialog
        }
    }

    interface OnAddDrinkDialogButtonClicked {
        fun onAcceptButtonClicked(dialog: DialogFragment)
        fun onCancelButtonClicked(dialog: DialogFragment)
    }

    private var onAddFoodDialogButtonClicked: OnAddDrinkDialogButtonClicked? = null

    private var etName: TextInputEditText? = null
    private var etPrice: TextInputEditText? = null
    private var spinnerTypeDrink: Spinner? = null
    private var btnAddDrink: MaterialButton? = null
    private var btnCancel: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.dialog_add_drink, container, false)

        initView(rootView)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    private fun initView(view: View){

        etName = view.findViewById(R.id.etNameDrink)
        etPrice = view.findViewById(R.id.etPriceDrink)
        spinnerTypeDrink = view.findViewById(R.id.spinnerTypeDrink)
        btnAddDrink = view.findViewById(R.id.btnAccept)
        btnCancel = view.findViewById(R.id.btnCancel)

        btnCancel!!.setOnClickListener {
            onAddFoodDialogButtonClicked?.onCancelButtonClicked(this)
        }

        btnAddDrink!!.setOnClickListener {
            btnAddDrink!!.isEnabled = false
            if(EditTextUtil.isCorrectFormatPrice(etPrice!! .text.toString())){
                val newFood = FoodModel(etName!!.text.toString(), etPrice!!.text.toString(), UUID.randomUUID().toString())
                App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.DRINK_CHILD).child(spinnerTypeDrink!!.selectedItem.toString()).setValue(newFood)
                        .addOnCompleteListener {
                            Toast.makeText(requireContext(), getString(R.string.add_product_sucessfull), Toast.LENGTH_LONG).show()
                            this.dismiss()
                        }
                btnAddDrink!!.isEnabled = true
            } else {
                Toast.makeText(requireContext(), getString(R.string.price_no_valid_msg), Toast.LENGTH_LONG).show()
                btnAddDrink!!.isEnabled = true
            }
        }

        spinnerTypeDrink!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, requireActivity().resources.getStringArray(R.array.type_drink))
    }

}