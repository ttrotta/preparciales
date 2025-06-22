package com.example.parcialvuelosayd.injector

import android.content.Context
import com.example.parcialvuelosayd.controller.FlightControllerImpl
import com.example.parcialvuelosayd.model.repository.country.CountryRepository
import com.example.parcialvuelosayd.model.repository.country.CountryRepositoryImpl
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepository
import com.example.parcialvuelosayd.model.repository.vuelos.FlightRepositoryImpl
import com.example.parcialvuelosayd.model.repository.vuelos.external.FlightServiceImpl
import com.example.parcialvuelosayd.model.repository.vuelos.external.JsonToVueloResolverImpl
import com.example.parcialvuelosayd.model.repository.vuelos.local.FlightLocalStorageImpl
import com.example.parcialvuelosayd.model.repository.vuelos.local.JsonToListResolverImpl
import com.example.parcialvuelosayd.model.FlightModel
import com.example.parcialvuelosayd.model.FlightModelImpl
import com.example.parcialvuelosayd.view.FlightView


object FlightInjector {
    lateinit var model: FlightModel

    fun initializeDependencies(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MY_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val localResolver = JsonToListResolverImpl()
        val flightLocalStorage = FlightLocalStorageImpl(sharedPreferences, localResolver)

        val serviceResolver = JsonToVueloResolverImpl()
        val flightService = FlightServiceImpl(serviceResolver)

        val flightRepository: FlightRepository = FlightRepositoryImpl(flightLocalStorage, flightService)
        val countryRepository: CountryRepository = CountryRepositoryImpl()

        model = FlightModelImpl(flightRepository, countryRepository)

        val controller = FlightControllerImpl(context as FlightView, model)
        controller.initObserver()
    }
}