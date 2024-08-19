package com.mustafatoktas.yukluuygulamalistesi.presentation.main.components

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
    onItemClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
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
            label = "Package Name:",
            value = uygulama.paketAdi,
        )

        InfoRow(
            label = "Version:",
            value = uygulama.versiyon,
        )

        InfoRow(
            label = "Installed On:",
            value = uygulama.installedOn,
        )

        RenkliInfoRow(
            label = "Is system app?",
            sistemUygulamasiMi = uygulama.sistemUygulamasiMi,
        )
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
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
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Taşan metinler 3 nokta ile gösterilir
            )
        }
    }
}

@Composable
fun RenkliInfoRow(
    label: String,
    sistemUygulamasiMi: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .weight(2f)
                .horizontalScroll(rememberScrollState())
        ) {
            Text(
                text = if (sistemUygulamasiMi) Constants.evet else Constants.hayir,
                color = if (sistemUygulamasiMi) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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