package com.example.parcialvuelosayd.controller

import com.example.parcialvuelosayd.model.FlightModel
import com.example.parcialvuelosayd.view.FlightView
import kotlin.concurrent.thread

interface FlightController {
    fun fetchVuelos(paisSeleccionado: String)
}

class FlightControllerImpl(
    private val view: FlightView,
    private val model: FlightModel
) : FlightController {

    fun initObserver() {
       view.observerView.subscribe {
           result -> fetchVuelos(result)
        }
    }

    override fun fetchVuelos(paisSeleccionado: String) {
        thread {
            model.fetchVuelos(paisSeleccionado)
        }
    }
}