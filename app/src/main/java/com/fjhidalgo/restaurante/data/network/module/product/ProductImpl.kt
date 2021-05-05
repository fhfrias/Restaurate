package com.fjhidalgo.restaurante.data.network.module.product

import android.util.Log
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallback
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackDelete
import com.google.firebase.storage.StorageReference

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
}