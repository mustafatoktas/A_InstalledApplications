package com.mustafatoktas.yukluuygulamalistesi.ui.main.components

import android.app.Activity
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mustafatoktas.yukluuygulamalistesi.R

@Composable
fun OtherContent(
    baslik : String,
    icerik : String,
    @DrawableRes resim : Int? = null,
    context: Context,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = baslik,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(resim ?: R.drawable.emulator)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.width(300.dp).padding(bottom = 24.dp),
        )
        Text(
            text = icerik,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = {
                (context as Activity).finish()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = context.getString(R.string.uygulamayi_kapat))
        }
    }
}