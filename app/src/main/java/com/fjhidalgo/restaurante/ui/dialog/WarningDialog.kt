package com.fjhidalgo.restaurante.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.google.android.material.button.MaterialButton

class WarningDialog : DialogFragment() {

    companion object {
        fun newInstance(title: String, text: String, onDialogButtonClicked: OnDialogButtonClicked): WarningDialog {
            val dialog = WarningDialog()
            dialog.title = title
            dialog.text = text
            dialog.isCancelable = false
            dialog.onDialogButtonClicked = onDialogButtonClicked
            return dialog
        }
    }

    interface OnDialogButtonClicked {
        fun onAcceptButtonClicked(dialog: DialogFragment)
    }

    private var onDialogButtonClicked: OnDialogButtonClicked? = null

    lateinit var rootView: View

    val tvTitle: TextView by lazy {
        rootView.findViewById<TextView>(R.id.tvTitle)
    }

    val tvText: TextView by lazy {
        rootView.findViewById<TextView>(R.id.tvText)
    }

    val btnAccpet: MaterialButton by lazy {
        rootView.findViewById<MaterialButton>(R.id.btnAccept)
    }

    val btnCancel: MaterialButton by lazy {
        rootView.findViewById(R.id.btnCancel)
    }

    var title: String? = null
    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.warning_dialog, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTitle.text = title
        tvText.text = text

        btnAccpet.setOnClickListener {
            onDialogButtonClicked?.onAcceptButtonClicked(this)
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}