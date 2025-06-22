package com.example.parcialvuelosayd.model.repository.country

import com.example.parcialvuelosayd.model.BoundingBox
import com.example.parcialvuelosayd.model.Country

interface CountryRepository {
    fun getPaises(): List<Country>
}

class CountryRepositoryImpl : CountryRepository {

    override fun getPaises(): List<Country> = listOf(
        Country("Argentina", BoundingBox(-55.0, -20.0, -75.0, -53.0)),
        Country("Chile", BoundingBox(-56.0, -17.0, -75.0, -66.0)),
        Country("Brasil", BoundingBox(-35.0, 5.0, -74.0, -35.0)),
        Country("Perú", BoundingBox(-18.0, 1.0, -82.0, -68.0))
    )
}
