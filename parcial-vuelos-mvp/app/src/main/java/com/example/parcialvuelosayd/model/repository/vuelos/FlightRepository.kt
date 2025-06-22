package com.example.parcialvuelosayd.model.repository.vuelos

import com.example.parcialvuelosayd.model.BoundingBox
import com.example.parcialvuelosayd.model.repository.vuelos.external.FlightService
import com.example.parcialvuelosayd.model.repository.vuelos.local.FlightLocalStorage

interface FlightRepository {
    fun fetchVuelos(pais: String): MutableList<String>?
}

class FlightRepositoryImpl(
    private val localStorage: FlightLocalStorage,
    private val service: FlightService
) : FlightRepository {
    val paisCoordenadas = mapOf(
        "Argentina" to BoundingBox(-55.0, -20.0, -75.0, -53.0),
        "Chile" to BoundingBox(-56.0, -17.0, -75.0, -66.0),
        "Brasil" to BoundingBox(-35.0, 5.0, -74.0, -35.0),
        "Perú" to BoundingBox(-18.0, 1.0, -82.0, -68.0)
    )


    override fun fetchVuelos(pais: String): MutableList<String>? {
        val savedValue = localStorage.getVuelos(pais)

        if(!savedValue.isEmpty()) {
            return savedValue
        } else {
            val apiResult = paisCoordenadas[pais]?.let { service.getVuelos(it) }
            localStorage.saveVuelos(pais, apiResult)
            return apiResult
        }
    }

}