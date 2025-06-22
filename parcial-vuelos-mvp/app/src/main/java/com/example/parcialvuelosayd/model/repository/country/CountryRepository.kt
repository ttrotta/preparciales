package com.example.parcialvuelosayd.model.repository.country

import com.example.parcialvuelosayd.model.Country

interface CountryRepository {
    fun obtenerPaises(): List<Country>
}

class CountryRepositoryImpl : CountryRepository {

    override fun obtenerPaises(): List<Country> = listOf(
        Country("Argentina"),
        Country("Chile"),
        Country("Brasil"),
        Country("Perú")
    )

}