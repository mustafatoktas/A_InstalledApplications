package com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel

import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama


data class MainState(
    val uygulamaListesi : List<Uygulama> = emptyList(),
    val isLoading : Boolean = false,
    val toplamUygulamaSayisi : Int = 0,
)