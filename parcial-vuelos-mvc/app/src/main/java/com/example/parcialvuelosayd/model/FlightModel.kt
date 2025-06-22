package com.example.parcialvuelosayd.model

import ayds.observer.Observable
import ayds.observer.Subject
import com.example.parcialvuelosayd.model.repository.country.CountryRepository
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepository

interface FlightModel {
    val observerRepo: Observable<Pair<String, MutableList<String>?>>
    fun fetchVuelos(paisSeleccionado: String)
    fun getPaises() : List<Country>
}

class FlightModelImpl(
    private val flightRepository: FlightRepository,
    private val countryRepository: CountryRepository
) : FlightModel {

    override val observerRepo = Subject<Pair<String, MutableList<String>?>>()

    override fun fetchVuelos(paisSeleccionado: String) {
        val pais = getPaises().find { it.name == paisSeleccionado }!!
        val vuelosData = flightRepository.fetchVuelos(pais)
        observerRepo.notify(Pair(pais.name, vuelosData))
    }

    override fun getPaises(): List<Country> {
        return countryRepository.getPaises()
    }

}