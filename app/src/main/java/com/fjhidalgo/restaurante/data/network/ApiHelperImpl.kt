package com.fjhidalgo.restaurante.data.network

import android.net.Uri
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.module.auth.AuthImpl
import com.fjhidalgo.restaurante.data.network.module.product.ProductImpl
import com.fjhidalgo.restaurante.data.network.module.table.TableImpl
import com.fjhidalgo.restaurante.data.network.module.user.UserImpl
import com.fjhidalgo.restaurante.util.firebase.*
import java.net.URI

class ApiHelperImpl : ApiHelper {

    override fun performGetFood(typeFood: String, callback: FirebaseCallback) {
        val productImpl = ProductImpl()
        productImpl.getListFood(typeFood, callback)
    }

    override fun performGetDrink(typeDrink: String, callback: FirebaseCallback) {
        val productImpl = ProductImpl()
        productImpl.getListDrink(typeDrink, callback)
    }

    override fun performDeleteFood(id: String, typeFood: String, linkImage: String, callback: FirebaseCallbackDelete) {
        val productImpl = ProductImpl()
        productImpl.deleteFood(id, typeFood, linkImage, callback)
    }

    override fun performDeleteDrink(id: String, typeDrink: String, linkImage: String, callback: FirebaseCallbackDelete) {
        val productImpl = ProductImpl()
        productImpl.deleteDrink(id, typeDrink, linkImage, callback)
    }

    override fun performUpdateFood(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        val productImpl = ProductImpl()
        productImpl.updateFood(updateProduct, type, uri, callback)
    }

    override fun performUpdateDrink(updateProduct: ProductModel, type: String, uri: Uri?, callback: FirebaseCallbackUpdate) {
        val productImpl = ProductImpl()
        productImpl.updateDrink(updateProduct, type, uri, callback)
    }

    override fun performCreateUser(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup) {
        val authImpl: AuthImpl = AuthImpl()
        authImpl.createUserEmailPass(email, password,userModel, callback)
    }

    override fun performSigninUser(email: String, password: String, callback: FirebaseCallbackSignin) {
        val authImpl: AuthImpl = AuthImpl()
        authImpl.signinUser(email, password, callback)
    }

    override fun performGetDataUser(email: String, callback: FirebaseCallbackDataUser) {
        val userImpl = UserImpl()
        userImpl.getDataUser(email, callback)
    }

    override fun performUpdateIsAdmin(id: String, isAdmin: Boolean, callback: FirebaseCallbackUpdate) {
        val userImpl = UserImpl()
        userImpl.updateIsAdminUser(id, isAdmin, callback)
    }

    override fun perfomGetVersion(callback: FirebaseCallbackVersion) {
        val userImpl = UserImpl()
        userImpl.getVersion(callback)
    }

    override fun performRecoryPass(email: String, callback: FirebaseCallbackRecoveryPass) {
        val userImpl = UserImpl()
        userImpl.recoveryPass(email, callback)
    }

    override fun performAddTable(
        newTable: TableModel,
        type: String,
        callbackAddDeleteTable: FirebaseCallbackAddDeleteTable
    ) {
        val tableImpl = TableImpl()
        tableImpl.addTable(newTable, type, callbackAddDeleteTable)
    }

    override fun performDeleteTable(
        numberTable: String,
        type: String,
        callbackAddDeleteTable: FirebaseCallbackAddDeleteTable
    ) {
        val tableImpl = TableImpl()
        tableImpl.deleteTable(numberTable, type, callbackAddDeleteTable)
    }

    override fun performGetTables(type: String, callback: FirebaseCallback) {
        val tableImpl = TableImpl()
        tableImpl.getTables(type, callback)
    }
}
