package com.example.preparcialayd.controller

import ayds.observer.Subject
import com.example.preparcialayd.model.CryptoModel
import com.example.preparcialayd.view.CryptoView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CryptoControllerTest {

    private val cryptoModel : CryptoModel = mockk()

    private val onActionSubject = Subject<String>()
    private val cryptoView: CryptoView = mockk(relaxUnitFun = true) {
        every { viewObserver } returns onActionSubject
    }

    private val controller = CryptoControllerImpl(cryptoModel).apply { setCryptoView(cryptoView) }

    @Test
    fun `on a view action should call model fetch price`() {
        onActionSubject.notify("USD")
        verify { cryptoModel.fetchPrice("USD") }
    }

}

//interface CryptoController {
//    fun setCryptoView(cryptoView: CryptoView)
//}
//
//class CryptoControllerImpl(
//    private val cryptoModel: CryptoModel,
//) : CryptoController {
//
//    private lateinit var cryptoView: CryptoView
//
//    override fun setCryptoView(cryptoView: CryptoView) {
//        this.cryptoView = cryptoView
//        cryptoView.viewObserver.subscribe {
//                value -> fetchPrice(value)
//        }
//    }
//  }
//
//    private fun fetchPrice(selectedCurrency: String) {
//        thread {
//            cryptoModel.fetchPrice(selectedCurrency)
//        }
//    }
//}