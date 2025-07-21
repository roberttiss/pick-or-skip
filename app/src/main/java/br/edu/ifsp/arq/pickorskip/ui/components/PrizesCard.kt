package br.edu.ifsp.arq.pickorskip.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.ifsp.arq.pickorskip.ui.theme.consequenceButtonColor
import br.edu.ifsp.arq.pickorskip.ui.theme.consequenceButtonLabelColor

@Composable
fun ConsequencesCard(
    wrongAnswerPrize: Int,
    giveUpPrize: Int,
    answeredCorrectlyPrize: Int
) {
    val wrongAnswerPrizeText = formatPrize(wrongAnswerPrize)
    val giveUpPrizeText = formatPrize(giveUpPrize)
    val answeredCorrectlyPrizeText = formatPrize(answeredCorrectlyPrize)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrizeColumn(value = wrongAnswerPrizeText, label = "ERRAR")
        PrizeColumn(value = giveUpPrizeText, label = "PARAR")
        PrizeColumn(value = answeredCorrectlyPrizeText, label = "ACERTAR")
    }
}

@Composable
fun PrizeColumn(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(consequenceButtonColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = value,
                color = consequenceButtonLabelColor,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

fun formatPrize(value: Int): String {
    return when {
        value == 1_000_000 -> "1 MILHÃO"
        value % 1_000 == 0 -> "${value / 1_000} MIL"
        else -> value.toString()
    }
}