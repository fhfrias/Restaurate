package com.fjhidalgo.restaurante.data.model.user

class UserModel {

    var email: String? = null
    var name: String? = null
    var surname: String? = null
    var isAdmin: Boolean? = null
    var id: String? = null

    constructor(email: String?, name: String?, surname: String?, isAdmin: Boolean?, id: String?) {
        this.email = email
        this.name = name
        this.surname = surname
        this.isAdmin = isAdmin
        this.id = id
    }


    override fun toString(): String {
        return "UserModel(email=$email, name=$name, surname=$surname, isAdmin=$isAdmin)"
    }


}