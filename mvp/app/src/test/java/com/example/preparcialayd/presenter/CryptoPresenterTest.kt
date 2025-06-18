package com.example.preparcialayd.presenter

import com.example.preparcialayd.model.CryptoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CryptoPresenterTest {

    private val cryptoRepository: CryptoRepository = mockk()
    private val cryptoPresenter: CryptoPresenter = CryptoPresenterImpl(cryptoRepository)

    @Test
    fun `on fetchPrice should return price`() {
        every { cryptoRepository.fetchPrice("USD")} returns 1.0

        val observerTest: (Pair<String, Int>) -> Unit = mockk(relaxed = true)

        cryptoPresenter.observer.subscribe(observerTest)

        cryptoPresenter.fetchPrice("USD")

        verify { observerTest(Pair("USD", 1)) }
    }
}
