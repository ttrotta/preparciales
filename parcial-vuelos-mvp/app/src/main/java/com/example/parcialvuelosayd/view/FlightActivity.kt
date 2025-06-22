package com.example.parcialvuelosayd.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialvuelosayd.presenter.FlightPresenter
import com.example.parcialvuelosayd.R
import com.example.parcialvuelosayd.injector.FlightInjector
import com.example.parcialvuelosayd.model.Country

private const val MESSAGE = "Vuelos sobre"

class FlightActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<Country>
    private lateinit var message: TextView
    private lateinit var messageVuelo: TextView
    private lateinit var paises: List<Country>

    private lateinit var presenter: FlightPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePresenter()
        paises = presenter.getPaises()

        initViewProperties()
        addListener()
        initializeObserver()
    }


    private fun initializePresenter() {
        FlightInjector.initializeDependencies(this)
        presenter = FlightInjector.presenter
    }

    private fun initViewProperties() {
        spinner = findViewById<Spinner>(R.id.spinnerPaises)

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

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
                presenter.fetchVuelos(pais.name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun initializeObserver() {
        presenter.observer.subscribe { result ->
            onVuelo(result.first, result.second)
        }
    }

    fun onVuelo(pais: String, vuelos: MutableList<String>?) {
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