package com.example.preparcialayd.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observable
import ayds.observer.Subject
import com.example.preparcialayd.R
import com.example.preparcialayd.injector.CryptoInjector
import com.example.preparcialayd.model.CryptoModel

interface CryptoView {
    val viewObserver: Observable<String>
}

class CryptoActivity : AppCompatActivity(), CryptoView {
    private val COINS = listOf("USD", "EUR", "CAD", "JPY", "RUB", "GBP", "KRW", "PLN")

    private lateinit var spinner: Spinner
    private lateinit var priceText: TextView

    private lateinit var model: CryptoModel

    override val viewObserver = Subject<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeDependencies()
        observeModel()

        initViewProperties()
        initListener()
    }

    private fun initializeDependencies() {
        CryptoInjector.initializeDependencies(this, this)
        model = CryptoInjector.model
    }

    private fun observeModel() {
        model.modelObserver.subscribe {
            result -> updateUIPrice(result.first, result.second)
        }
    }

    private fun initViewProperties() {
        spinner = findViewById<Spinner>(R.id.spinnerMonedas)
        priceText = findViewById<TextView>(R.id.textPrecio)
        spinner.adapter = initAdapter()
    }

    private fun initAdapter(): ArrayAdapter<String> {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, COINS)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun initListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCoin = COINS[position]
                viewObserver.notify(selectedCoin)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun updateUIPrice(symbol: String, price: Int) {
        val message = "$symbol – $price"
        runOnUiThread {
            priceText.text = message
        }
    }
}