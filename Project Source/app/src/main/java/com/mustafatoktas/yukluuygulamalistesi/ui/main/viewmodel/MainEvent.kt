package com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel


sealed class MainEvent {
    data class OnRefreshClick (val ilkAcilisMi: Boolean) : MainEvent()
    data object OnFilterClick : MainEvent()
    data class onUygulamaClick(val packageName: String) : MainEvent()
}