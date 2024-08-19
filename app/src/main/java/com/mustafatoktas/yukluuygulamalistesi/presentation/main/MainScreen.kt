package com.mustafatoktas.yukluuygulamalistesi.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.components.MainToolbar
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.components.UygulamaBilgisiSection
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.components.UygulamalariAra
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel.MainEvent
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel.MainEventUi
import com.mustafatoktas.yukluuygulamalistesi.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val toaster = rememberToasterState()
    var aramaAktifMi by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventUiFlow.collectLatest { event ->
            when(event) {
                is MainEventUi.SnackBarGoster -> {
                    toaster.show(event.mesaj)
                }
            }
        }
    }

    Toaster(state = toaster, alignment = Alignment.Center )

    Scaffold(
        topBar = {
            if (!aramaAktifMi)
            MainToolbar(
                oncFilterClick = {
                    viewModel.handleEvent(MainEvent.OnFilterClick)
                },
                onRefreshClick = {
                    viewModel.handleEvent(MainEvent.OnRefreshClick)
                },
                onSearchClick = {
                    aramaAktifMi = true
                }
            )

            else UygulamalariAra(
                list = state.uygulamaListesi,
                viewModel = viewModel
            ) {
                aramaAktifMi = false
            }
        },
    ) { padding ->

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    text = "${state.toplamUygulamaSayisi} apps installed",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                )

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize(),
                ) {
                    items(state.uygulamaListesi) { uygulama ->
                        UygulamaBilgisiSection(
                            uygulama = uygulama,
                            onItemClick = {
                               viewModel.handleEvent(MainEvent.onUygulamaClick(uygulama.paketAdi))
                            }
                        )
                    }
                }
            }

        }
    }
}