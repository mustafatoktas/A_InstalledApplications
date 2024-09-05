package com.mustafatoktas.yukluuygulamalistesi.ui.main.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama
import com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel.MainEvent
import com.mustafatoktas.yukluuygulamalistesi.ui.main.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UygulamalariAra(
    list : List<Uygulama>,
    viewModel: MainViewModel,
    onSearchClosed: () -> Unit,
) {

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    // search bar
    SearchBar(
        modifier = Modifier.fillMaxWidth().padding(horizontal = if (active) 0.dp else 16.dp),
        query = text,
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = { isActive ->
            active = isActive
            if (!isActive) {
                text = ""
                onSearchClosed.invoke()
            }
        },
        placeholder = { Text("Search apps...") },
    ) {
        val flat = list.filter { it.uygulumaAdi.contains(text, ignoreCase = true) }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 12.dp),
            content = {
                item { Text(text = "${flat.size} apps found") }

                items(items = flat) { uygulama ->
                    UygulamaBilgisiSection(uygulama = uygulama) {
                        viewModel.handleEvent(MainEvent.onUygulamaClick(uygulama.paketAdi))
                    }
                }
            }
        )
    }
}