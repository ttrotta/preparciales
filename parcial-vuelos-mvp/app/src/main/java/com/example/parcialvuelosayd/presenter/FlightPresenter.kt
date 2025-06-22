package com.example.parcialvuelosayd.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import com.example.parcialvuelosayd.model.Country
import com.example.parcialvuelosayd.model.repository.country.CountryRepository
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepository
import kotlin.concurrent.thread

interface FlightPresenter {
    val observer: Observable<Pair<String, MutableList<String>?>>
    fun fetchVuelos(paisSeleccionado: String)
    fun getPaises() : List<Country>
}

class FlightPresenterImpl(
    private val flightRepository: FlightRepository,
    private val countryRepository: CountryRepository
) : FlightPresenter {

    override val observer = Subject<Pair<String, MutableList<String>?>>()

    override fun fetchVuelos(paisSeleccionado: String) {
        thread {
            val result = flightRepository.fetchVuelos(paisSeleccionado)
            observer.notify(Pair(paisSeleccionado, result))
        }
    }

    override fun getPaises(): List<Country> {
        return countryRepository.obtenerPaises()
    }
}