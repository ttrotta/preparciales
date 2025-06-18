package com.example.preparcialayd.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import com.example.preparcialayd.model.CryptoRepository
import kotlin.concurrent.thread
import kotlin.math.roundToInt

interface CryptoPresenter {
    val observer: Observable<Pair<String, Int>>
    fun fetchPrice(selectedCurrency: String)
}

class CryptoPresenterImpl(
    private val repo: CryptoRepository
) : CryptoPresenter {

    override val observer = Subject<Pair<String, Int>>()

    override fun fetchPrice(selectedCurrency: String) {
        thread {
            val priceInRepo = repo.fetchPrice(selectedCurrency)
            observer.notify(Pair(selectedCurrency, priceInRepo.roundToInt()))
        }
    }
}