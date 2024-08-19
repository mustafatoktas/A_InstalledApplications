package com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.mustafatoktas.yukluuygulamalistesi.common.Constants
import com.mustafatoktas.yukluuygulamalistesi.common.Resource
import com.mustafatoktas.yukluuygulamalistesi.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


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
        uygulamalariYenileJob?.cancel()
        uygulamalariYenileJob = viewModelScope.launch {
            uygulamaListesiniYenile()
        }
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnRefreshClick -> {
                uygulamaListesiniYenile()
            }
            is MainEvent.OnFilterClick -> {
               filtreTiklandi()
            }

            is MainEvent.onUygulamaClick -> {
                viewModelScope.launch {
                    uygulamayiAyarlardaAc(event.packageName)
                }
            }
        }
    }

    private fun uygulamaListesiniYenile() {
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

    private suspend fun uygulamayiAyarlardaAc(packageName: String) {
        appRepository.ayarlardaAc(packageName)
    }
}