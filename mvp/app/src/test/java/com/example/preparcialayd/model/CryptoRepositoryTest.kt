package com.example.preparcialayd.model

import com.example.preparcialayd.model.external.CryptoService
import com.example.preparcialayd.model.local.CryptoLocalStorage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import kotlin.test.assertEquals

class CryptoRepositoryTest {

    private val cryptoService: CryptoService = mockk()
    private val cryptoLocalStorage: CryptoLocalStorage = mockk()
    private val repository : CryptoRepository = CryptoRepositoryImpl(cryptoLocalStorage, cryptoService)

    @Test
    fun `on getRate locally is successful`() {
        every { cryptoLocalStorage.getRates("USD") } returns 1.0
        val result = repository.fetchPrice("USD")

        assertEquals(result, 1.0)
    }

    @Test
    fun `on getRate locally is not successful but remotely is`() {
        every { cryptoLocalStorage.getRates("USD") } returns null
        every { cryptoService.getRates("USD") } returns 1.0
        every { cryptoLocalStorage.saveRates("USD", 1.0) } returns Unit


        val result = repository.fetchPrice("USD")

        assertEquals(result, 1.0)
    }
}

