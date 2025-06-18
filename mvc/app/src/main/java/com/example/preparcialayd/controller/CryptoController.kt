package com.example.preparcialayd.controller

import com.example.preparcialayd.model.CryptoModel
import com.example.preparcialayd.view.CryptoView
import kotlin.concurrent.thread

interface CryptoController {
    fun setCryptoView(cryptoView: CryptoView)
}

class CryptoControllerImpl(
    private val cryptoModel: CryptoModel,
) : CryptoController {

    private lateinit var cryptoView: CryptoView

    override fun setCryptoView(cryptoView: CryptoView) {
        this.cryptoView = cryptoView
        cryptoView.viewObserver.subscribe {
            value -> fetchPrice(value)
        }
    }

//    private val observer: Observer<String> =
//        Observer {
//            value -> fetchPrice(value)
//        }

    private fun fetchPrice(selectedCurrency: String) {
        thread {
            cryptoModel.fetchPrice(selectedCurrency)
        }
    }
}