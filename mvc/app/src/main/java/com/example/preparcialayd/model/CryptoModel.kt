package com.example.preparcialayd.model

import ayds.observer.Observable
import ayds.observer.Subject
import kotlin.math.roundToInt

interface CryptoModel {
    val modelObserver: Observable<Pair<String, Int>>
    fun fetchPrice(currencySymbol: String)
}

class CryptoModelImpl(
    private val repository: CryptoRepository,
) : CryptoModel {
    override val modelObserver = Subject<Pair<String, Int>>()

    override fun fetchPrice(currencySymbol: String) {
        val priceInRepo = repository.fetchPrice(currencySymbol)
        modelObserver.notify(Pair(currencySymbol, priceInRepo.roundToInt()))
    }
}