package com.example.parcialvuelosayd.model.repository.vuelos.external

import com.example.parcialvuelosayd.model.BoundingBox
import java.net.URL

private const val URL = "aa"

interface FlightService {
    fun getVuelos(pais: BoundingBox): MutableList<String>
}

class FlightServiceImpl (
    private val resolver: JsonToVueloResolver
) : FlightService {

    val paisCoordenadas = mapOf(
        "Argentina" to BoundingBox(-55.0, -20.0, -75.0, -53.0),
        "Chile" to BoundingBox(-56.0, -17.0, -75.0, -66.0),
        "Brasil" to BoundingBox(-35.0, 5.0, -74.0, -35.0),
        "Perú" to BoundingBox(-18.0, 1.0, -82.0, -68.0)
    )

    override fun getVuelos(pais: BoundingBox): MutableList<String> {
        val jsonInString = fetchFromApi(pais)
        var result = mutableListOf<String>()
        val resultApi = resolver.parseJsonToList(jsonInString)

        if(!resultApi.isEmpty()) {
            result = resultApi
        }

        return result
    }

    private fun fetchFromApi(pais: BoundingBox): String {
        val url = "https://opensky-network.org/api/states/all?lamin=${pais.latMin}&lomin=${pais.lonMin}&lamax=${pais.latMax}&lomax=${pais.lonMax}"
        return URL(url).readText()
    }
}