package com.example.preparcialayd.model

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CryptoModelTest {

    private val repository: CryptoRepository = mockk()

    private val cryptoModel = CryptoModelImpl(repository)

    @Test
    fun `on fetchPrice should notify the view`() {
        every { repository.fetchPrice("USD") } returns 1.0

        val observerTest: (Pair<String, Int>) -> Unit = mockk(relaxed = true)

        cryptoModel.modelObserver.subscribe(observerTest)

        cryptoModel.fetchPrice("USD")

        verify { observerTest(Pair("USD", 1)) }
    }
}