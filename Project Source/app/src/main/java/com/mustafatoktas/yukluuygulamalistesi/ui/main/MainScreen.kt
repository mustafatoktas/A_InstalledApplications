package com.mustafatoktas.yukluuygulamalistesi.ui.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafatoktas.yukluuygulamalistesi.R
import com.mustafatoktas.yukluuygulamalistesi.common.CihazDurumu
import com.mustafatoktas.yukluuygulamalistesi.ui.main.components.MainContent
import com.mustafatoktas.yukluuygulamalistesi.ui.main.components.OtherContent
import com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    context: Context,
) {
    val state by viewModel.state.collectAsState()

    when (state.cihazDurumu) {
        CihazDurumu.Rootlu -> OtherContent(
            baslik = context.getString(R.string.root_tespit_edildi),
            icerik = context.getString(R.string.root_icerigi),
            resim = R.drawable.root,
            context = context,
        )
        CihazDurumu.Emulator -> OtherContent(
            baslik = context.getString(R.string.cihaz_bir_emulator_uzerinde_calisiyor),
            icerik = context.getString(R.string.emulator_icerigi),
            resim = R.drawable.emulator,
            context = context,
        )
        CihazDurumu.Normal -> MainContent(
            viewModel = viewModel,
            state = state,
        )
    }
}