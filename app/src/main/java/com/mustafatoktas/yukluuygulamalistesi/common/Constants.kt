package com.mustafatoktas.yukluuygulamalistesi.common

object Constants {
    const val yuklu_uygulama_listesi_yenilendi = "Installed application list has been refreshed"
    const val filtreleme_ozelligi_yeni_versiyonda_sunulacak = "Filtering feature will be available in later versions"
    const val evet = "Yes"
    const val hayir = "No"
}

enum class CihazDurumu {
    Rootlu,
    Emulator,
    Normal,
}