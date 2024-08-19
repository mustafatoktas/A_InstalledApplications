package com.mustafatoktas.yukluuygulamalistesi.data.repositoryImpl

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.provider.Settings
import com.mustafatoktas.yukluuygulamalistesi.common.Resource
import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama
import com.mustafatoktas.yukluuygulamalistesi.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val context: Context,
) : AppRepository {


    override suspend fun getUygulamaListesi(): Flow<Resource<List<Uygulama>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val pm = context.packageManager
                val apps = pm.getInstalledPackages(0).map { packageInfo ->
                    Uygulama(
                        uygulumaAdi = packageInfo.applicationInfo.loadLabel(pm).toString(),
                        paketAdi = packageInfo.packageName,
                        versiyon = packageInfo.versionName ?: "Bilinmiyor",
                        installedOn = Date(packageInfo.firstInstallTime).toString(),
                        sistemUygulamasiMi = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0,
                        icon = packageInfo.applicationInfo.loadIcon(pm)
                    )
                }.sortedBy { it.uygulumaAdi.lowercase() }
                emit(Resource.Success(apps))

            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Bir hata oluştu"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun ayarlardaAc(packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Yeni bir aktivite olarak açmak için
        }
        context.startActivity(intent)
    }
}