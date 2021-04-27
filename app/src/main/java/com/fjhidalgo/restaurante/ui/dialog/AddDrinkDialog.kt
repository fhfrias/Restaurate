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
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.drink.DrinkModel
import com.fjhidalgo.restaurante.data.model.food.FoodModel
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
    private var cirImgPhoto: CircularImageView? = null
    private var takeOrSelectPictureDialog: TakeOrSelectPictureDialog? = null
    private var listPermissionsNeeded  = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private var photoDrink: Bitmap? = null
    private var uriDrink: Uri? = null


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
        cirImgPhoto = view.findViewById(R.id.cirImgPhoto)


        btnCancel!!.setOnClickListener {
            onAddFoodDialogButtonClicked?.onCancelButtonClicked(this)
        }

        btnAddDrink!!.setOnClickListener {
            btnAddDrink!!.isEnabled = false
            if(EditTextUtil.isCorrectFormatPrice(etPrice!!.text.toString()) && etName!!.text.toString().length != 0){
                val idDrink = UUID.randomUUID().toString()
                val newDrink = DrinkModel(etName!!.text.toString(), etPrice!!.text.toString(), idDrink)

                if(uriDrink != null){
                    sendImageUriToFirebaseStorage(uriDrink!!, newDrink)
                } else {
                    newDrink.linkImage = "null"
                    sendProductToFirebaseDataBase(newDrink)
                }

            } else {
                Toast.makeText(requireContext(), getString(R.string.price_no_valid_msg), Toast.LENGTH_LONG).show()
                btnAddDrink!!.isEnabled = true
            }
        }

        cirImgPhoto!!.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(requireActivity(), listPermissionsNeeded, 7);
            } else {
                selectPhoto()
            }

        }

        spinnerTypeDrink!!.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, requireActivity().resources.getStringArray(R.array.type_drink))
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

    private fun sendImageUriToFirebaseStorage(uri: Uri, drinkModel: DrinkModel) {

        val pathFile: StorageReference = App.instance.firebaseStorage?.child(AppConstants.DRINK_CHILD)?.child(spinnerTypeDrink!!.selectedItem.toString())?.child(drinkModel.id!! + ".png")!!
        pathFile.putFile(uri).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {

            override fun onSuccess(p0: UploadTask.TaskSnapshot?) {

                val result: Task<Uri> = p0?.getMetadata()?.getReference()?.getDownloadUrl()!!
                result.addOnSuccessListener { uri ->
                    val photoStringLink = uri.toString()
                    drinkModel.linkImage = photoStringLink
                    sendProductToFirebaseDataBase(drinkModel)
                }
                result.addOnCanceledListener {
                    Toast.makeText(requireContext(), getString(R.string.error_add_product_msg), Toast.LENGTH_LONG).show()
                    btnAddDrink!!.isEnabled = true
                }
            }

        }).addOnCanceledListener {
            Toast.makeText(requireContext(), getString(R.string.error_add_product_msg), Toast.LENGTH_LONG).show()
            btnAddDrink!!.isEnabled = true
        }
    }

    private fun sendProductToFirebaseDataBase(foodModel: DrinkModel){
        App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.DRINK_CHILD).child(spinnerTypeDrink!!.selectedItem.toString()).child(foodModel.id!!).setValue(foodModel)
                .addOnCompleteListener {
                    Toast.makeText(requireContext(), getString(R.string.add_product_sucessfull), Toast.LENGTH_LONG).show()
                    this.dismiss()
                }
                .addOnCanceledListener {
                    Toast.makeText(requireContext(), getString(R.string.error_add_product_msg), Toast.LENGTH_LONG).show()
                    btnAddDrink!!.isEnabled = true
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            TakeOrSelectPictureDialog.RC_PICK_IMAGE -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {

                    if (data!!.data != null) {

                        uriDrink = data.data
                        val imageEncoded = PhotoProduct.getPath(requireContext(), uriDrink!!)
                        if (!imageEncoded.isNullOrEmpty()) {
                            photoDrink = BitmapFactory.decodeFile(imageEncoded)
                            cirImgPhoto?.setImageBitmap(photoDrink)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), getString(R.string.not_add_photo_product), Toast.LENGTH_LONG).show()
                }
            }

            else -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    //CAMERA
                    photoDrink = data?.extras?.get("data") as Bitmap
                    cirImgPhoto?.setImageBitmap(photoDrink)
                    uriDrink = getUriFromTakePicture(photoDrink!!)
                }
            }

        }
    }

}