package com.example.parcialvuelosayd.model.repository.vuelos.local

import android.content.SharedPreferences
import androidx.core.content.edit

interface FlightLocalStorage {
    fun getVuelos(pais: String) : MutableList<String>
    fun saveVuelos(pais: String, list: MutableList<String>?)
}

class FlightLocalStorageImpl(
    private val sharedPreferences: SharedPreferences,
    private val jsonToListResolver: JsonToListResolver
) : FlightLocalStorage {

    override fun getVuelos(pais: String): MutableList<String> {
        var result: MutableList<String> = mutableListOf()
        val vuelo = sharedPreferences.getString(pais, null)

        if(vuelo != null) {
            result = jsonToListResolver.resolver(vuelo)
        }

        return result
    }

    override fun saveVuelos(pais: String, list: MutableList<String>?) {
        sharedPreferences.edit { putString(pais, jsonToListResolver.jsonToString(list)) }
    }


}