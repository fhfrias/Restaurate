package com.fjhidalgo.restaurante.data.model.product

class ProductResponse {
    var products: List<ProductModel>? = null
    var exception: Exception? = null

    override fun toString(): String {
        return "ProductResponse(products=$products, exception=$exception)"
    }


}