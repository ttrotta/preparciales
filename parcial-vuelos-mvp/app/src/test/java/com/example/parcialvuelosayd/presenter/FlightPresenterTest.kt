package com.example.parcialvuelosayd.presenter

import com.example.parcialvuelosayd.model.BoundingBox
import com.example.parcialvuelosayd.model.Country
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
        every {
            flightRepository.fetchVuelos(Country("Argentina", BoundingBox(-55.0, -20.0, -75.0, -53.0)))
        } returns mutableListOf("Vuelo1", "Vuelo2")

        val observerTest: (Pair<String, MutableList<String>?>) -> Unit = mockk(relaxed = true)

        presenter.observer.subscribe(observerTest)
        presenter.fetchVuelos("Argentina")

        verify { observerTest(Pair("Argentina", mutableListOf("Vuelo1", "Vuelo2"))) }
    }

    @Test
    fun `on getPaises it should return a list of all countries`() {

        val list = listOf(
            Country("Argentina", BoundingBox(-55.0, -20.0, -75.0, -53.0)),
            Country("Chile", BoundingBox(-56.0, -17.0, -75.0, -66.0)),
            Country("Brasil", BoundingBox(-35.0, 5.0, -74.0, -35.0)),
            Country("Perú", BoundingBox(-18.0, 1.0, -82.0, -68.0))
        )

        every { countryRepository.getPaises() } returns list

        val result = presenter.getPaises()

        assert(result.size == 4)
        assert(result[0].name == "Argentina")
        assert(result[1].name == "Chile")
        assert(result[2].name == "Brasil")
        assert(result[3].name == "Perú")
    }

}