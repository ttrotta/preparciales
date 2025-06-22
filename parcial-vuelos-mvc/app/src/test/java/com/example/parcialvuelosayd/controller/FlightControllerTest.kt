package com.example.parcialvuelosayd.controller

import ayds.observer.Subject
import com.example.parcialvuelosayd.model.FlightModel
import com.example.parcialvuelosayd.view.FlightView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class FlightControllerTest {

    private val flightModel: FlightModel = mockk()

    private val onActionSubject = Subject<String>()
    private val flightView: FlightView = mockk(relaxUnitFun = true) {
        every { observerView } returns onActionSubject
    }

    private val controller = FlightControllerImpl(flightView, flightModel)

    @Test
    fun `on a view action should fetch the flights`() {
        controller.initObserver()
        onActionSubject.notify("Argentina")
        verify(timeout = 1_000) { flightModel.fetchVuelos("Argentina") }
    }
}