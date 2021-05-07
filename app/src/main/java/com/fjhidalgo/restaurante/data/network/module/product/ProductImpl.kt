package com.fjhidalgo.restaurante.data.network.module.product

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackUpdate
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.net.URI

class ProductImpl: ProductInterface {

    //Database
    val refFood = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.FOOD_CHILD)
    val refDrink = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.DRINK_CHILD)

    //Storage
    val storageRefFood = App.instance.firebaseStorage!!.child(AppConstants.FOOD_CHILD)
    val storageRefDrink = App.instance.firebaseStorage!!.child(AppConstants.DRINK_CHILD)

    override fun getListFood(typeFood: String, callback: FirebaseCallback) {
        refFood.child(typeFood).get().addOnCompleteListener { task ->
            if(task.isSuccessful){

                callback.onResponse(task)

            } else {
                callback.onError(task)
            }
        }

    }

    override fun getListDrink(typeDrink: String, callback: FirebaseCallback) {
        refDrink.child(typeDrink).get().addOnCompleteListener { task ->
            if(task.isSuccessful){

                callback.onResponse(task)

            } else {

                callback.onError(task)
            }
        }
    }

    override fun deleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete) {
        refFood.child(typeFood).child(id).removeValue().addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback.onResponse()
                if (!linkImage.equals("null")){
                    storageRefFood.child(typeFood).child(id + ".png").delete()
                }
            } else {
                callback.onError()
            }
        }
    }

    override fun deleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete) {
        refDrink.child(typeDrink).child(id).removeValue().addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback.onResponse()
                if (!linkImage.equals("null")){
                    storageRefDrink.child(typeDrink).child(id + ".png").delete()
                }
            } else {

                callback.onError()
            }
        }
    }

    override fun updateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        if (uri != null){
            sendImageUriToFirebaseStorage(uri!!, updateProduct, type, true, callback)
        } else {
            sendProductToFirebaseDataBase(updateProduct, type, true, callback)
        }
    }

    override fun updateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        if ( uri != null){
            sendImageUriToFirebaseStorage(uri!!, updateProduct, type, false, callback)
        } else {
            sendProductToFirebaseDataBase(updateProduct, type, false, callback)
        }
    }

    private fun sendImageUriToFirebaseStorage(uri: Uri, productModel: ProductModel, type: String, isFood: Boolean, callback: FirebaseCallbackUpdate) {

        var pathFile: StorageReference? = null

        if (isFood){
            pathFile = App.instance.firebaseStorage?.child(AppConstants.FOOD_CHILD)?.child(type)?.child(productModel.id!! + ".png")!!
        } else {
            pathFile = App.instance.firebaseStorage?.child(AppConstants.DRINK_CHILD)?.child(type)?.child(productModel.id!! + ".png")!!
        }

        pathFile.putFile(uri).addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {

            override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                val result: Task<Uri> = p0?.getMetadata()?.getReference()?.getDownloadUrl()!!
                result.addOnSuccessListener { uri ->
                    val photoStringLink = uri.toString()
                    productModel.linkImage = photoStringLink
                    sendProductToFirebaseDataBase(productModel, type, isFood, callback)
                }
                result.addOnCanceledListener {
                    callback.onError()
                }
            }

        }).addOnCanceledListener {

            callback.onError()
        }
    }

    private fun sendProductToFirebaseDataBase(productModel : ProductModel, type: String, isFood: Boolean, callback: FirebaseCallbackUpdate){

        var databaseReference: DatabaseReference? = null

        if(isFood){
            databaseReference = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.FOOD_CHILD)
        } else {
            databaseReference = App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.DRINK_CHILD)
        }
        Log.e("El producto", productModel.toString())
        Log.e("El tipo", type)
        databaseReference.child(type).child(productModel.id!!).setValue(productModel)
                .addOnCompleteListener {
                    callback.onResponse()
                }
                .addOnCanceledListener {
                    callback.onError()
                }
    }
}