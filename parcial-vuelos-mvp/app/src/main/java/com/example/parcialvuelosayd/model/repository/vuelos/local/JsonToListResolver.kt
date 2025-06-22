package com.example.parcialvuelosayd.model.repository.vuelos.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface JsonToListResolver {
    fun resolver(json: String) : MutableList<String>
    fun jsonToString(list: MutableList<String>?): String
}

class JsonToListResolverImpl() : JsonToListResolver {

    val gson = Gson()

    override fun resolver(json: String): MutableList<String> {
        return try {
            gson.fromJson(json, object : TypeToken<MutableList<String>>(){}.type)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    override fun jsonToString(list: MutableList<String>?): String {
        return gson.toJson(list)
    }

}