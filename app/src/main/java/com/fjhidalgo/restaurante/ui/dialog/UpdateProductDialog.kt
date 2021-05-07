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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.drink.DrinkModel
import com.fjhidalgo.restaurante.data.model.product.ProductModel
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

class UpdateProductDialog: DialogFragment() {

    companion object {
        fun newInstance(item: ProductModel, isFood: Boolean, type: String, onUpdateDialogButtonClicked: OnUpdateDialogButtonClicked): UpdateProductDialog {
            val dialog = UpdateProductDialog()
            dialog.product = item
            dialog.isFood = isFood
            dialog.type = type
            dialog.isCancelable = false
            dialog.onUpdateDialogButtonClicked = onUpdateDialogButtonClicked
            return dialog
        }
    }

    interface OnUpdateDialogButtonClicked {
        fun onAcceptButtonClicked(dialog: DialogFragment, product: ProductModel, isFood: Boolean, type: String, uriProduct: Uri?)
        fun onCancelButtonClicked(dialog: DialogFragment)
    }

    private var onUpdateDialogButtonClicked: OnUpdateDialogButtonClicked? = null
    private var product: ProductModel? = null
    private var isFood: Boolean? = null
    private var type: String? = null

    private var etName: TextInputEditText? = null
    private var etPrice: TextInputEditText? = null
    private var btnUpdate: MaterialButton? = null
    private var btnCancel: ImageButton? = null
    private var cirImgPhoto: CircularImageView? = null
    private var takeOrSelectPictureDialog: TakeOrSelectPictureDialog? = null
    private var listPermissionsNeeded  = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private var photoProduct: Bitmap? = null
    private var uriProduct: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = inflater.inflate(R.layout.dialog_update_product, container, false)

        initView(rootView)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return rootView
    }

    private fun initView(view: View){

        etName = view.findViewById(R.id.etNameProduct)
        etPrice = view.findViewById(R.id.etPriceProduct)
        btnUpdate = view.findViewById(R.id.btnAccept)
        btnCancel = view.findViewById(R.id.btnCancel)
        cirImgPhoto = view.findViewById(R.id.cirImgPhoto)


        btnCancel!!.setOnClickListener {
            onUpdateDialogButtonClicked?.onCancelButtonClicked(this)
        }

        btnUpdate!!.setOnClickListener {
            btnUpdate!!.isEnabled = false
            if(EditTextUtil.isCorrectFormatPrice(etPrice!!.text.toString()) && etName!!.text.toString().length != 0){
                val updateProduct = ProductModel(etName!!.text.toString(), etPrice!!.text.toString(), product!!.id, product!!.linkImage)

                onUpdateDialogButtonClicked?.onAcceptButtonClicked(this, updateProduct, isFood!!, type!!, uriProduct )

            } else {
                Toast.makeText(requireContext(), getString(R.string.price_no_valid_msg), Toast.LENGTH_LONG).show()
                btnUpdate!!.isEnabled = true
            }
        }

        cirImgPhoto!!.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(requireActivity(), listPermissionsNeeded, 7);
            } else {
                selectPhoto()
            }

        }

        //Set Data of product
        etName!!.setText(product!!.name)
        etPrice!!.setText(product!!.price)

        if ( !product!!.linkImage.equals("null")){
            Glide.with(requireActivity()).load(product!!.linkImage).into(cirImgPhoto!!)
        }
    }

    private fun selectPhoto(){
        takeOrSelectPictureDialog = TakeOrSelectPictureDialog.newInstance(getString(R.string.attach_since), false)
        takeOrSelectPictureDialog?.also {

            it.setOnCancelButtonListener(object : TakeOrSelectPictureDialog.OnCancelButtonListener {
                override fun onClick() {
                    it.dismiss()
                }
            })
            it.show(requireActivity().supportFragmentManager, "TakeOrSelectPictureDialog")
        }
    }

    private fun getUriFromTakePicture(bitmap: Bitmap): Uri {
        var dir = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath)
        if (!dir.exists()){
            dir.mkdir()
        }
        var photoFile = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath, ".png")
        var out = FileOutputStream(photoFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, out)
        out.flush()
        out.close()


        return Uri.fromFile(photoFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            TakeOrSelectPictureDialog.RC_PICK_IMAGE -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    if (data!!.data != null) {

                        uriProduct = data.data
                        val imageEncoded = PhotoProduct.getPath(requireContext(), uriProduct!!)
                        if (!imageEncoded.isNullOrEmpty()) {
                            photoProduct = BitmapFactory.decodeFile(imageEncoded)
                            cirImgPhoto?.setImageBitmap(photoProduct)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), getString(R.string.not_add_photo_product), Toast.LENGTH_LONG).show()
                }
            }

            else -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    //CAMERA
                    photoProduct = data?.extras?.get("data") as Bitmap
                    cirImgPhoto?.setImageBitmap(photoProduct)
                    uriProduct = getUriFromTakePicture(photoProduct!!)
                }
            }

        }
    }

}