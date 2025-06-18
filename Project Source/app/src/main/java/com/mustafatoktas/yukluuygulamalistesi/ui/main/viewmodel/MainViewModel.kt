package com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.mustafatoktas.yukluuygulamalistesi.common.CihazDurumu
import com.mustafatoktas.yukluuygulamalistesi.common.Constants
import com.mustafatoktas.yukluuygulamalistesi.common.Resource
import com.mustafatoktas.yukluuygulamalistesi.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    private val _eventUiFlow = MutableSharedFlow<MainEventUi>()
    val eventUiFlow = _eventUiFlow.asSharedFlow()

    private var uygulamalariYenileJob: Job? = null

    init {
        uygulamalariYenileJob = viewModelScope.launch {
            uygulamaListesiniYenile(true)
        }
        cihazDurumu()

    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnRefreshClick -> uygulamaListesiniYenile(event.ilkAcilisMi)
            is MainEvent.OnFilterClick -> filtreTiklandi()
            is MainEvent.onUygulamaClick -> uygulamayiAyarlardaAc(event.packageName)
        }
    }

    private fun uygulamaListesiniYenile(ilkAcilisMi : Boolean = false) {
        uygulamalariYenileJob?.cancel()
        uygulamalariYenileJob = viewModelScope.launch {
            appRepository.getUygulamaListesi().collect { result ->
                when (result) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                    is Resource.Success -> {
                        result.data?.let { uygulamalar ->
                            _state.update {
                                it.copy(
                                    uygulamaListesi = uygulamalar,
                                    toplamUygulamaSayisi = uygulamalar.size
                                )
                            }
                        }
                        if (!ilkAcilisMi)
                        _eventUiFlow.emit(
                            MainEventUi.SnackBarGoster(
                                mesaj = Constants.yuklu_uygulama_listesi_yenilendi
                            )
                        )
                    }
                }
            }
        }
    }

    private fun filtreTiklandi() {
        viewModelScope.launch {
            _eventUiFlow.emit(
                MainEventUi.SnackBarGoster(
                    mesaj = Constants.filtreleme_ozelligi_yeni_versiyonda_sunulacak
                )
            )
        }
    }

    private fun uygulamayiAyarlardaAc(packageName: String) {
        viewModelScope.launch {
            appRepository.ayarlardaAc(packageName)
        }
    }

    private fun cihazDurumu() {
        viewModelScope.launch {
            val isRootedDeferred = async { isRooted() }
            val isEmulatorDeferred = async { isEmulator() }

            val isRooted = isRootedDeferred.await()
            val isEmulator = isEmulatorDeferred.await()

            _state.value = _state.value.copy(
                cihazDurumu = when {
                    isRooted -> CihazDurumu.Rootlu
                    isEmulator -> CihazDurumu.Emulator
                    else -> CihazDurumu.Normal
                }
            )
        }
    }

    private suspend fun isRooted(): Boolean {
        return isRootedMethod1() || isRootedMethod2()
    }

    private suspend fun isRootedMethod1(): Boolean {
        return withContext(Dispatchers.IO) {
            val superuserApk = File("/system/app/Superuser.apk")
            val suBinary = File("/system/bin/su")
            superuserApk.exists() || suBinary.exists()
        }
    }

    private suspend fun isRootedMethod2(): Boolean {
        return withContext(Dispatchers.IO) {
            var process: Process? = null
            return@withContext try {
                process = Runtime.getRuntime().exec("su")
                true // Eğer "su" komutu başarılı olursa cihaz rootlu
            } catch (e: Exception) {
                false // Eğer bir exception fırlarsa cihaz rootlu değil
            } finally {
                process?.destroy() // Process'i kapatmayı garantiye alıyoruz
            }
        }
    }

    private suspend fun isEmulator(): Boolean {
        return withContext(Dispatchers.IO) {
            (Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                    && Build.FINGERPRINT.endsWith(":user/release-keys")
                    && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone") && Build.BRAND == "google"
                    && Build.MODEL.startsWith("sdk_gphone"))
                    || Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.HARDWARE.contains("goldfish")
                    || Build.HARDWARE.contains("ranchu")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || Build.HOST == "Build2" //MSI App Player
                    || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                    || Build.PRODUCT.contains("sdk_google")
                    || Build.PRODUCT == "google_sdk"
                    || Build.PRODUCT.contains("sdk")
                    || Build.PRODUCT.contains("sdk_x86")
                    || Build.PRODUCT.contains("vbox86p")
                    || Build.PRODUCT.contains("emulator")
                    || Build.PRODUCT.contains("simulator")
        }
    }
}