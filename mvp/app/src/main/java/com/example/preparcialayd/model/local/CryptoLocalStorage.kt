package com.example.preparcialayd.model.local

import android.content.SharedPreferences
import androidx.core.content.edit


interface CryptoLocalStorage {
    fun getRates(currencySymbol: String): Double?
    fun saveRates(currencySymbol: String, price: Double?)
}

class CryptoLocalStorageImpl(
    private val sharedPreferences: SharedPreferences
) : CryptoLocalStorage {

    override fun getRates(currencySymbol: String): Double? {
        val rate = sharedPreferences.getString(currencySymbol, null)
        return rate?.toDouble()
    }

    override fun saveRates(currencySymbol: String, price: Double?) {
        sharedPreferences.edit { putString(currencySymbol, price.toString()) }
    }
}