package com.example.parcialvuelosayd.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observable
import ayds.observer.Subject
import com.example.parcialvuelosayd.R
import com.example.parcialvuelosayd.injector.FlightInjector
import com.example.parcialvuelosayd.model.Country
import com.example.parcialvuelosayd.model.FlightModel

private const val MESSAGE = "Vuelos sobre"

interface FlightView {
    val observerView: Observable<String>
    fun updateUiOnVuelo(pais: String, vuelos: MutableList<String>?)
}

class FlightActivity : AppCompatActivity(), FlightView {
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<Country>
    private lateinit var message: TextView
    private lateinit var messageVuelo: TextView

    private lateinit var paises: List<Country>

    private lateinit var model: FlightModel

    override val observerView = Subject<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePresenter()
        paises = model.getPaises()

        initViewProperties()
        spinner.adapter = adapter

        addListener()
        initializeObserver()
    }


    private fun initializePresenter() {
        FlightInjector.initializeDependencies(this)
        model = FlightInjector.model
    }

    private fun initViewProperties() {
        spinner = findViewById<Spinner>(R.id.spinnerPaises)

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        message = findViewById<TextView>(R.id.textMensaje)
        messageVuelo = findViewById<TextView>(R.id.textVuelo)
    }

    private fun addListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pais = paises[position]
                observerView.notify(pais.name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun initializeObserver() {
        model.observerRepo.subscribe { result ->
            updateUiOnVuelo(result.first, result.second)
        }
    }

    override fun updateUiOnVuelo(pais: String, vuelos: MutableList<String>?) {
        val mensaje = "$MESSAGE$pais"
        val mensajeVuelos = buildText(vuelos)

        runOnUiThread {
            message.text = mensaje
            messageVuelo.text = mensajeVuelos
        }
    }

    private fun buildText(vuelos: MutableList<String>?): String {
        val ret = buildString {
            vuelos?.forEachIndexed { index, vuelo ->
                append("${index + 1}. $vuelo\n")
            }
        }

        return ret
    }
}