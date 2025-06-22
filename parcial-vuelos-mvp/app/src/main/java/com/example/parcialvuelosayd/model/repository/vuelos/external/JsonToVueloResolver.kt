package com.example.parcialvuelosayd.model.repository.vuelos.external

import com.google.gson.Gson
import com.google.gson.JsonObject

interface JsonToVueloResolver {
    fun parseJsonToList(text: String): MutableList<String>
}

class JsonToVueloResolverImpl : JsonToVueloResolver {
    override fun parseJsonToList(text: String): MutableList<String> {
        val jsonObject = Gson().fromJson(text, JsonObject::class.java)
        val states = jsonObject["states"].asJsonArray
        val vuelos = mutableListOf<String>()
        for (i in 0 until minOf(5,states.size())){
            val vueloArray = states[i].asJsonArray
            val callsign = vueloArray[1].asString.trim()
            val originCountry = vueloArray[2].asString
            val altitude = if (!vueloArray[7].isJsonNull) vueloArray[7].asDouble.toInt().toString() else "N/A"
            val velocity = if (!vueloArray[9].isJsonNull) vueloArray[9].asDouble.toInt().toString() else "N/A"

            val info = "Vuelo: $callsign – País: $originCountry – Altura: $altitude m – Velocidad: $velocity km/h"
            vuelos.add(info)
        }

        return vuelos
    }
}