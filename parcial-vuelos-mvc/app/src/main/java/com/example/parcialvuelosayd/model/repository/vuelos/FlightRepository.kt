package com.example.parcialvuelosayd.model.repository.vuelos

import com.example.parcialvuelosayd.model.Country
import com.example.parcialvuelosayd.model.repository.vuelos.external.FlightService
import com.example.parcialvuelosayd.model.repository.vuelos.local.FlightLocalStorage

interface FlightRepository {
    fun fetchVuelos(pais: Country): MutableList<String>?
}

class FlightRepositoryImpl(
    private val localStorage: FlightLocalStorage,
    private val service: FlightService
) : FlightRepository {

    override fun fetchVuelos(pais: Country): MutableList<String>? {
        val savedValue = localStorage.getVuelos(pais.name)

        if(!savedValue.isEmpty()) {
            return savedValue
        } else {
            val apiResult = service.getVuelos(pais.boundingBox)
            localStorage.saveVuelos(pais.name, apiResult)
            return apiResult
        }
    }

}