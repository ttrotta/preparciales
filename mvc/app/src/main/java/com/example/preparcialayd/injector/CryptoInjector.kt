package com.example.preparcialayd.injector

import android.content.Context
import com.example.preparcialayd.controller.CryptoControllerImpl
import com.example.preparcialayd.model.CryptoRepositoryImpl
import com.example.preparcialayd.model.external.CryptoServiceImpl
import com.example.preparcialayd.model.external.SymbolToPriceResolverImpl
import com.example.preparcialayd.model.local.CryptoLocalStorageImpl
import com.example.preparcialayd.model.CryptoModel
import com.example.preparcialayd.model.CryptoModelImpl
import com.example.preparcialayd.view.CryptoView

private const val PREFERENCES = "MY_SHARED_PREFERENCES"

object CryptoInjector {
    lateinit var model: CryptoModel

    fun initializeDependencies(context: Context, cryptoView: CryptoView) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val localStorage = CryptoLocalStorageImpl(sharedPreferences)

        val resolver = SymbolToPriceResolverImpl()
        val cryptoService = CryptoServiceImpl(resolver)

        val repository = CryptoRepositoryImpl(localStorage, cryptoService)

        model = CryptoModelImpl(repository)

        CryptoControllerImpl(model).apply { setCryptoView(cryptoView) }
    }
}