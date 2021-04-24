package com.fjhidalgo.restaurante.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

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
    private var spinnerTypeFood: Spinner? = null
    private var btnAddFood: MaterialButton? = null
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
        spinnerTypeFood = view.findViewById(R.id.spinnerTypeDrink)
        btnAddFood = view.findViewById(R.id.btnAccept)
        btnCancel = view.findViewById(R.id.btnCancel)

        btnCancel!!.setOnClickListener {
            onAddFoodDialogButtonClicked?.onCancelButtonClicked(this)
        }
    }

}