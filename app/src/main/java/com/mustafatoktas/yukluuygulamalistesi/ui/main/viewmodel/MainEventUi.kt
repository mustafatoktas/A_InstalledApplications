package com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel


sealed class MainEventUi {
    data class SnackBarGoster(val mesaj: String): MainEventUi()
}