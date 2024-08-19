package com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel


sealed class MainEventUi {
    data class SnackBarGoster(val mesaj: String): MainEventUi()
}