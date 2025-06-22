package com.example.parcialvuelosayd.model.repository.vuelos.external

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonElement

interface JsonToVueloResolver {
    fun parseJsonToList(text: String): MutableList<String>
}

class JsonToVueloResolverImpl : JsonToVueloResolver {
    override fun parseJsonToList(text: String): MutableList<String> {
        var vuelos = mutableListOf<String>()
        val gson = Gson()

        try {
            val jsonObject = gson.fromJson(text, JsonObject::class.java)

            if (jsonObject == null) {
                return mutableListOf("No hay vuelos disponibles")
            }

            val statesElement: JsonElement? = jsonObject["states"]

            if (statesElement != null && statesElement.isJsonArray) {
                val states = statesElement.asJsonArray

                for (i in 0 until minOf(5, states.size())) {
                    val vueloElement = states[i]
                    if (vueloElement != null && vueloElement.isJsonArray) {
                        val vueloArray = vueloElement.asJsonArray

                        val callsign = if (vueloArray.size() > 1 && !vueloArray[1].isJsonNull) vueloArray[1].asString.trim() else "N/A"
                        val originCountry = if (vueloArray.size() > 2 && !vueloArray[2].isJsonNull) vueloArray[2].asString else "N/A"

                        val altitudeValue = if (vueloArray.size() > 7 && !vueloArray[7].isJsonNull) vueloArray[7] else null
                        val altitude = altitudeValue?.asDouble?.toInt()?.toString() ?: "N/A"

                        val velocityValue = if (vueloArray.size() > 9 && !vueloArray[9].isJsonNull) vueloArray[9] else null
                        val velocity = velocityValue?.asDouble?.toInt()?.toString() ?: "N/A"

                        val info = "Vuelo: $callsign – País: $originCountry – Altura: $altitude m – Velocidad: $velocity km/h"
                        vuelos.add(info)
                    }
                }
            }
        } catch (e: Exception) {
            vuelos = mutableListOf("Error al parsear el JSON")
        }

        return vuelos
    }
}