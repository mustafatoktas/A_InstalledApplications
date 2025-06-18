package com.mustafatoktas.yukluuygulamalistesi.domain.repository

import com.mustafatoktas.yukluuygulamalistesi.common.Resource
import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama
import kotlinx.coroutines.flow.Flow


interface AppRepository {
    suspend fun getUygulamaListesi() : Flow<Resource<List<Uygulama>>>
    suspend fun ayarlardaAc(packageName: String)
}