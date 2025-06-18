package com.example.preparcialayd.model

import com.example.preparcialayd.model.external.CryptoService
import com.example.preparcialayd.model.local.CryptoLocalStorage

interface CryptoRepository {
    fun fetchPrice(currencySymbol: String): Double
}

class CryptoRepositoryImpl (
   private val localStorage: CryptoLocalStorage,
   private val apiService: CryptoService
) : CryptoRepository {

    override fun fetchPrice(currencySymbol: String): Double {
        val bitcoinPrice = localStorage.getRates(currencySymbol)
        if(bitcoinPrice != null) {
            return bitcoinPrice
        } else {
            val bitcoinPriceAPI = apiService.getRates(currencySymbol)
            localStorage.saveRates(currencySymbol, bitcoinPriceAPI)
            return bitcoinPriceAPI
        }
    }
}