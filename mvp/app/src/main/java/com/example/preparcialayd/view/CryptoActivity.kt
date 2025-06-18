package com.example.preparcialayd.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.preparcialayd.presenter.CryptoPresenter
import com.example.preparcialayd.R
import com.example.preparcialayd.injector.CryptoInjector

class CryptoActivity : AppCompatActivity() {
    private val COINS = listOf("USD", "EUR", "CAD", "JPY", "RUB", "GBP", "KRW", "PLN", "ARS")

    private lateinit var spinner: Spinner
    private lateinit var priceText: TextView

    private lateinit var presenter: CryptoPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePresenter()
        observePresenter()

        initViewProperties()
        initListener()
    }

    private fun initializePresenter() {
        CryptoInjector.initializeDependencies(this)
        presenter = CryptoInjector.presenter
    }

    private fun observePresenter() {
        presenter.observer.subscribe {
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
                presenter.fetchPrice(selectedCoin)
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