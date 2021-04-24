package com.fjhidalgo.restaurante.data.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.Serializable

/**
 * Modelo Base
 */
open class BaseModel : Serializable {

    /**
     * Instancia de Gson para parsear los JSON a Modelos POJO
     * definida como transient para que no se parsee a si misma
     */
    @Transient
    protected val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    /**
     * Convierte el POJO a JSON
     *
     * @return representacion en JSON String del Modelo
     */
    fun toJson(): String {
        return gson.toJson(this)
    }
}
