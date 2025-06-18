package com.example.preparcialayd.model.external

import android.util.Log
import com.example.preparcialayd.model.local.CryptoLocalStorage
import java.net.URL

interface CryptoService {
    fun getRates(currencySymbol: String): Double
}

private const val API_URL = "https://api.alternative.me/v2/ticker/1/?convert="

internal class CryptoServiceImpl(
    private val resolver: SymbolToPriceResolver
) : CryptoService {

    override fun getRates(currencySymbol: String): Double {
        val jsonInString = fetchData(currencySymbol)
        return resolver.getPrice(currencySymbol, jsonInString)
    }

    private fun fetchData(currencySymbol: String): String {
        val preparedUrl = "$API_URL$currencySymbol"
        val jsonInString = URL(preparedUrl).readText()
        return jsonInString
    }

}