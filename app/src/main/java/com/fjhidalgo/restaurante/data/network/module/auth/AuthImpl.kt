package com.fjhidalgo.restaurante.data.network.module.auth

import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignin
import com.fjhidalgo.restaurante.util.firebase.FirebaseCallbackSignup

class AuthImpl: AuthInterface {

    override fun createUserEmailPass(email: String, password: String, userModel: UserModel, callback: FirebaseCallbackSignup) {
        App.instance.firebaseAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if(it.isSuccessful){

                createUserDataBase(userModel, callback)

            } else {

                callback.onError()
            }
        }
    }

    override fun createUserDataBase(userModel: UserModel, callback: FirebaseCallbackSignup) {
        App.instance.databaseReference!!.child(AppConstants.MAIN_CHILD).child(AppConstants.USER_CHILD).child(userModel.id!!).setValue(userModel).addOnCompleteListener {

            if (it.isSuccessful){
                callback.onResponse()
            } else {
                callback.onError()
            }
        }
    }

    override fun signinUser(email: String, password: String, callback: FirebaseCallbackSignin) {
        App.instance.firebaseAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if(it.isSuccessful){
                it.result
                callback.onResponse()
            } else {

                callback.onError()
            }
        }
    }
}