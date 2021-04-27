package com.fjhidalgo.restaurante.data.model.food

class FoodModel {

    var name: String? = null
    var price: String? = null
    var id: String? = null
    var linkImage: String? = null

    constructor(name: String?, price: String?, id: String?){
        this.name = name
        this.price = price
        this.id = id
    }

    override fun toString(): String {
        return "FoodModel(name=$name, price=$price, id=$id)"
    }


}