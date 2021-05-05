package com.fjhidalgo.restaurante.data.model.product

class ProductModel {

    var name: String? = null
    var price: String? = null
    var id: String? = null
    var linkImage: String? = null

    constructor(name: String?, price: String?, id: String?){
        this.name = name
        this.price = price
        this.id = id
    }

    constructor(name: String?, price: String?, id: String?, linkImage: String?) {
        this.name = name
        this.price = price
        this.id = id
        this.linkImage = linkImage
    }

    constructor()


    override fun toString(): String {
        return "ProductModel(name=$name, price=$price, id=$id, linkImage=$linkImage)"
    }


}