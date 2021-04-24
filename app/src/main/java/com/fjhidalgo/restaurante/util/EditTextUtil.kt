package com.fjhidalgo.restaurante.util

import android.util.Log

class EditTextUtil {

    companion object {

        fun isCorrectFormatPrice(price: String): Boolean{
            if(price.contains(".") || price.contains(",")){
                price.replace(",", ".")
                var postPoint: String = price.substringAfter(".")
                Log.e("Post point", postPoint)
                if (postPoint.length < 3 && postPoint.length != 0){
                    return true
                } else {
                    return false
                }
            } else {
                if(price.length < 4 && price.length != 0){
                    return true
                } else {
                    return false
                }
            }
        }
    }
}