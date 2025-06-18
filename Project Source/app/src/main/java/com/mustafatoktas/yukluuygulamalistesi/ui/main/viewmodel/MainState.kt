package com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel

import com.mustafatoktas.yukluuygulamalistesi.common.CihazDurumu
import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama


data class MainState(
    val cihazDurumu: CihazDurumu = CihazDurumu.Normal,
    val uygulamaListesi : List<Uygulama> = emptyList(),
    val isLoading : Boolean = false,
    val toplamUygulamaSayisi : Int = 0,
)