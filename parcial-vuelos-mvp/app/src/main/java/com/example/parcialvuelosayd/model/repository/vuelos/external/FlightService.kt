package com.example.parcialvuelosayd.model.repository.vuelos.external

import com.example.parcialvuelosayd.model.BoundingBox
import java.net.URL

private const val URL = "https://opensky-network.org/api/states/all"
private const val LATITUD_MIN = "?lamin="
private const val LATITUD_MAX = "&lamax="
private const val LONGITUD_MIN = "&lomin="
private const val LONGITUD_MAX = "&lomax="

interface FlightService {
    fun getVuelos(pais: BoundingBox): MutableList<String>
}

class FlightServiceImpl (
    private val resolver: JsonToVueloResolver
) : FlightService {

    override fun getVuelos(pais: BoundingBox): MutableList<String> {
        val jsonInString = fetchFromApi(pais)

        return resolver.parseJsonToList(jsonInString)
    }

    private fun fetchFromApi(pais: BoundingBox?): String {
        val url = prepareURL(pais)

        return try {
            URL(url).readText()
        } catch (e: Exception) {
            return "Error"
        }
    }

    private fun prepareURL(pais: BoundingBox?): String {
        return URL +
                "$LATITUD_MIN${pais?.latMin}" +
                "$LONGITUD_MIN${pais?.lonMin}" +
                "$LATITUD_MAX${pais?.latMax}" +
                "$LONGITUD_MAX${pais?.lonMax}"
    }
}