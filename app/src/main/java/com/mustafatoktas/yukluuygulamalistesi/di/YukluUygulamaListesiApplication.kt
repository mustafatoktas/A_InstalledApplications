package com.mustafatoktas.yukluuygulamalistesi.di

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class YukluUygulamaListesiApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)    // telefon RAM'inin en fazla %10 boyutu kullanılacak
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)   //önbelleğe alınan resimler telefon hafızasının en fazla %3 boyutu kullanılacak
                    .directory(cacheDir)
                    .build()
            }
            //.logger(DebugLogger())   //coil'in yaptığı işleri loglar
            .build()
    }
}