package com.fjhidalgo.restaurante.data.network

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.util.firebase.*
import java.net.URI

interface ApiHelper {

    fun performGetFood(typeFood: String, callback: FirebaseCallback)
    fun performGetDrink(typeDrink: String, callback: FirebaseCallback)
    fun performDeleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun performDeleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete)
    fun performUpdateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)
    fun performUpdateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate)

    fun performCreateUser(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup)
    fun performSigninUser(email: String, password: String, callback: FirebaseCallbackSignin)
    fun performGetDataUser(email: String, callback: FirebaseCallbackDataUser)
}
