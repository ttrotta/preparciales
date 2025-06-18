package com.example.preparcialayd.injector

import android.content.Context
import com.example.preparcialayd.model.CryptoRepositoryImpl
import com.example.preparcialayd.model.external.CryptoServiceImpl
import com.example.preparcialayd.model.external.SymbolToPriceResolverImpl
import com.example.preparcialayd.model.local.CryptoLocalStorageImpl
import com.example.preparcialayd.presenter.CryptoPresenter
import com.example.preparcialayd.presenter.CryptoPresenterImpl

private const val PREFERENCES = "MY_SHARED_PREFERENCES"

object CryptoInjector {
    lateinit var presenter : CryptoPresenter

    fun initializeDependencies(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val localStorage = CryptoLocalStorageImpl(sharedPreferences)

        val resolver = SymbolToPriceResolverImpl()
        val cryptoService = CryptoServiceImpl(resolver)

        val repository = CryptoRepositoryImpl(localStorage, cryptoService)

        presenter = CryptoPresenterImpl(repository)
    }
}