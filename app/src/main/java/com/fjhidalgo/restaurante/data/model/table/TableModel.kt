package com.fjhidalgo.restaurante.data.model.table

import com.fjhidalgo.restaurante.data.model.bill.BillModel

class TableModel {

    var name: String? = null
    var id: String? = null
    var billList: List<BillModel>? = null



    constructor()

    constructor(name: String?, id: String?, billList: List<BillModel>?) {
        this.name = name
        this.id = id
        this.billList = billList
    }

    override fun toString(): String {
        return "TableModel(name=$name, id=$id, billList=$billList)"
    }


}