package com.fjhidalgo.restaurante.data.model.table

class TableModel {

    var name: String? = null
    var id: String? = null

    constructor(name: String?, id: String?) {
        this.name = name
        this.id = id
    }

    constructor()

    override fun toString(): String {
        return "TableModel(number=$name, id=$id)"
    }


}