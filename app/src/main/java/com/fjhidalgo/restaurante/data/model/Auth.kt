package com.fjhidalgo.restaurante.data.model

import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Auth : BaseModel() {

//    @Expose
//    @SerializedName("access_token")
//    var accessToken: String? = null
    @Expose
    @SerializedName("userModel")
    var userModel: UserModel? = null

}
