package com.mustafatoktas.yukluuygulamalistesi.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mustafatoktas.yukluuygulamalistesi.common.Constants
import com.mustafatoktas.yukluuygulamalistesi.common.toBitmap
import com.mustafatoktas.yukluuygulamalistesi.domain.model.Uygulama


@Composable
fun UygulamaBilgisiSection(
    modifier: Modifier = Modifier,
    uygulama: Uygulama,
    onItemClick: () -> Unit = {},
) {

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .shadow(3.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp)
            .clickable {
                onItemClick.invoke()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically // İkon ve metni ortalamak için
        ) {

            val bitmap = uygulama.icon?.toBitmap()

            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(48.dp) // İkonun boyutu
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = uygulama.uygulumaAdi,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        InfoRow(
            title = "Package Name:",
            text = uygulama.paketAdi,
            sistemUygulamasiMi = null,
        )

        InfoRow(
            title = "Version:",
            text = uygulama.versiyon,
            sistemUygulamasiMi = null,
        )

        InfoRow(
            title = "Installed On:",
            text = uygulama.installedOn,
            sistemUygulamasiMi = null,
        )

        InfoRow(
            title = "Is system app:",
            sistemUygulamasiMi = uygulama.sistemUygulamasiMi,
        )
    }
}

@Composable
fun InfoRow(
    title: String,
    text: String? = null,
    sistemUygulamasiMi: Boolean?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        // yatay kaydırılabilir metin
        Box(
            modifier = Modifier
                .weight(2f)
                .horizontalScroll(rememberScrollState()) // Yatay kaydırma
        ) {
            Text(
                text = text ?: when (sistemUygulamasiMi) {
                    true -> Constants.evet
                    false -> Constants.hayir
                    null -> ""
                },
                color = when (sistemUygulamasiMi) {
                    true -> MaterialTheme.colorScheme.error
                    false -> MaterialTheme.colorScheme.primary
                    null -> Color.Unspecified
                },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Taşan metinler 3 nokta ile gösterilir
            )
        }
    }
}


@Preview (showBackground = true)
@Composable
private fun UygulamaListesiSectionPreview() {
    UygulamaBilgisiSection(
        uygulama = Uygulama(
            uygulumaAdi = "Uygulama Adı",
            paketAdi = "Paket Adı sf",
            versiyon = "Versiyon",
            installedOn = "fdgzd",
            sistemUygulamasiMi = true,
            icon = null
        )
    )
}