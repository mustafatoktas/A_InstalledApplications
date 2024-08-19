package com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel


sealed class MainEvent {
    data object OnRefreshClick : MainEvent()
    data object OnFilterClick : MainEvent()
    data class onUygulamaClick(val packageName: String) : MainEvent()
}