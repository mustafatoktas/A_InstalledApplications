package com.mustafatoktas.yukluuygulamalistesi.domain.model

import android.graphics.drawable.Drawable


data class Uygulama(
    val uygulumaAdi: String,
    val paketAdi: String,
    val versiyon: String,
    val installedOn : String,
    val sistemUygulamasiMi : Boolean,
    val icon : Drawable?,
)