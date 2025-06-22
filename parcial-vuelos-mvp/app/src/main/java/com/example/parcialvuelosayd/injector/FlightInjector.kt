package com.example.parcialvuelosayd.injector

import android.content.Context
import com.example.parcialvuelosayd.model.repository.country.CountryRepository
import com.example.parcialvuelosayd.model.repository.country.CountryRepositoryImpl
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepository
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepositoryImpl
import com.example.parcialvuelosayd.model.repository.vuelos.external.FlightServiceImpl
import com.example.parcialvuelosayd.model.repository.vuelos.external.JsonToVueloResolverImpl
import com.example.parcialvuelosayd.model.repository.vuelos.local.FlightLocalStorageImpl
import com.example.parcialvuelosayd.model.repository.vuelos.local.JsonToListResolverImpl
import com.example.parcialvuelosayd.presenter.FlightPresenter
import com.example.parcialvuelosayd.presenter.FlightPresenterImpl


object FlightInjector {
    lateinit var presenter: FlightPresenter

    fun initializeDependencies(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MY_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val localResolver = JsonToListResolverImpl()
        val flightLocalStorage = FlightLocalStorageImpl(sharedPreferences, localResolver)

        val serviceResolver = JsonToVueloResolverImpl()
        val flightService = FlightServiceImpl(serviceResolver)

        val flightRepository: FlightRepository = FlightRepositoryImpl(flightLocalStorage, flightService)
        val countryRepository: CountryRepository = CountryRepositoryImpl()

        presenter = FlightPresenterImpl(flightRepository, countryRepository)
    }
}