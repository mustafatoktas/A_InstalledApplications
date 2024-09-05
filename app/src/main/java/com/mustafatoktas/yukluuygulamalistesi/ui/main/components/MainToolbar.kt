package com.mustafatoktas.yukluuygulamalistesi.ui.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.mustafatoktas.yukluuygulamalistesi.ui.theme.YukluUygulamaListesiTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainToolbar(
    onSearchClick: () -> Unit = {},
    oncFilterClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {}
) {
    var dropDownGoster by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = "Installed Applications")
        },
        actions = {
            IconButton(
                onClick = onSearchClick,
            ) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
            }

            IconButton(
                onClick = {
                    dropDownGoster = !dropDownGoster
                }
            ) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "MoreVert")
            }

            DropdownMenu(
                expanded = dropDownGoster,
                onDismissRequest = { dropDownGoster = false },
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Filter") },
                    onClick = {
                        dropDownGoster = false
                        oncFilterClick.invoke()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.FilterAlt, contentDescription = "Filter")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Refresh") },
                    onClick = {
                        dropDownGoster = false
                        onRefreshClick.invoke()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                )
            }
        }
    )
}

@Preview (showBackground = true)
@Composable
private fun MainToolbarPreview() {
    YukluUygulamaListesiTheme {
        MainToolbar()
    }
}