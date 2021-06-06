package com.fjhidalgo.restaurante.data.model.bill

class BillModel {

    var name: String? = null
    var amount: Int? = null
    var price: Double? = null
    var idProduct: String? = null
    var typeProduct: String? = null

    constructor(
        name: String?,
        amount: Int?,
        price: Double?,
        idProduct: String?,
        typeProduct: String?
    ) {
        this.name = name
        this.amount = amount
        this.price = price
        this.idProduct = idProduct
        this.typeProduct = typeProduct
    }

    constructor()


    override fun toString(): String {
        return "BillModel(name=$name, amount=$amount, price=$price, idProduct=$idProduct, typeProduct=$typeProduct)"
    }


}