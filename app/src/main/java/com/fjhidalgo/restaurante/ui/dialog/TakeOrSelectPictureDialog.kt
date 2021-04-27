package com.fjhidalgo.restaurante.ui.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.google.android.material.button.MaterialButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class TakeOrSelectPictureDialog : DialogFragment() {

    companion object {

        const val RC_TAKE_PICTURE = 2700
        const val RC_PICK_IMAGE = 2071

        val instance: TakeOrSelectPictureDialog
            get() = TakeOrSelectPictureDialog()

        fun newInstance(title: String, cancelable: Boolean?): TakeOrSelectPictureDialog {

            val takeOrSelectPictureDialog = instance

            takeOrSelectPictureDialog.isCancelable = cancelable!!
            takeOrSelectPictureDialog.title = title

            return takeOrSelectPictureDialog
        }
    }

    private var linearLayoutCamera: LinearLayout? = null
    private var linearLayoutGallery: LinearLayout? = null
    private var tvTitle: TextView? = null
    private var btnCancelar: MaterialButton? = null
    private var rootView: View? = null

    private var onCancelButtonListener: OnCancelButtonListener? = null

    interface OnCancelButtonListener {
        fun onClick()
    }

    fun setOnCancelButtonListener(onCancelButtonListener: OnCancelButtonListener) {
        this.onCancelButtonListener = onCancelButtonListener
    }

    var title: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.take_or_select_picture_dialog, container, false)

        initView(rootView!!)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    private fun initView(rootView: View) {
        linearLayoutCamera = rootView.findViewById(R.id.linearLayoutCamera)
        linearLayoutGallery = rootView.findViewById(R.id.linearLayoutGallery)
        btnCancelar = rootView.findViewById(R.id.btnCancelar)
        tvTitle = rootView.findViewById(R.id.tvTitle)

        tvTitle?.text = title

        linearLayoutCamera?.setOnClickListener { v ->
            /*val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity?.startActivityForResult(takePicture, RC_TAKE_PICTURE)*/

            takePicture()

            this.dismiss()
        }

        linearLayoutGallery?.setOnClickListener { v ->
            //Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //getActivity().startActivityForResult(pickPhoto, RC_PICK_IMAGE);

            val intent = Intent()
            intent.type = "image/*"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            intent.action = Intent.ACTION_GET_CONTENT
            requireActivity().startActivityForResult(Intent.createChooser(intent, "Choose application"), RC_PICK_IMAGE)

            this.dismiss()
        }

        btnCancelar?.setOnClickListener { v ->
            if (onCancelButtonListener != null) {
                onCancelButtonListener!!.onClick()
            }
        }
    }

    var currentPhotoPath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun takePicture(){

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, RC_TAKE_PICTURE)
    }

}
