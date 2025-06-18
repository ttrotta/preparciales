package com.example.preparcialayd.model.external

import com.google.gson.Gson
import com.google.gson.JsonObject

interface SymbolToPriceResolver {
    fun getPrice(currencySymbol: String, text: String) : Double
}

private const val DATA_TAG = "data"
private const val FIRST_ELEMENT = "1"
private const val QUOTES_TAG = "quotes"
private const val PRICE_TAG = "price"

internal class SymbolToPriceResolverImpl : SymbolToPriceResolver {
    override fun getPrice(currencySymbol: String, url: String): Double {
        val jsonObject = Gson().fromJson(url, JsonObject::class.java)
        val data = jsonObject[DATA_TAG].asJsonObject
        val firstElement = data[FIRST_ELEMENT].asJsonObject
        val quotes = firstElement[QUOTES_TAG].asJsonObject
        val symbolElement = quotes[currencySymbol].asJsonObject
        val price = symbolElement[PRICE_TAG]

        return price.asDouble
    }

}