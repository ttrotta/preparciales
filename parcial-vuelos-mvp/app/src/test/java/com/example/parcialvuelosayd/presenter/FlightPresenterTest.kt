package com.example.parcialvuelosayd.presenter

import com.example.parcialvuelosayd.model.repository.country.CountryRepository
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class FlightPresenterTest {
    private val flightRepository: FlightRepository = mockk()
    private val countryRepository: CountryRepository = mockk()

    private val presenter = FlightPresenterImpl(flightRepository, countryRepository)

    @Test
    fun `on fetchVuelos the observer should be notified correctly`() {
        every { flightRepository.fetchVuelos("Argentina") } returns mutableListOf("Vuelo1", "Vuelo2")

        val observerTest: (Pair<String, MutableList<String>?>) -> Unit = mockk(relaxed = true)

        presenter.observer.subscribe(observerTest)
        presenter.fetchVuelos("Argentina")

        verify { observerTest(Pair("Argentina", mutableListOf("Vuelo1", "Vuelo2"))) }


    }

}