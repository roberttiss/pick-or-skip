package br.edu.ifsp.arq.pickorskip.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.arq.pickorskip.R
import br.edu.ifsp.arq.pickorskip.models.enums.Lifeline
import br.edu.ifsp.arq.pickorskip.ui.theme.lifelineActiveButtonColor
import br.edu.ifsp.arq.pickorskip.ui.theme.lifelineDisabledButtonColor

@Composable
fun LifelineCard(
    canEliminate: Boolean,
    canSkip: Boolean,
    canAskAudience: Boolean,
    onLifelineClicked: (Lifeline) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        LifelineButton(
            text = "ELIMINAR",
            iconRes = R.drawable.ic_eliminate,
            enabled = canEliminate,
            onClick = { onLifelineClicked(Lifeline.ELIMINATE) }
        )

        LifelineButton(
            text = "PULAR",
            iconRes = R.drawable.ic_skip,
            enabled = canSkip,
            onClick = { onLifelineClicked(Lifeline.SKIP) }
        )

        LifelineButton(
            text = "PLATEIA",
            iconRes = R.drawable.ic_audience,
            enabled = canAskAudience,
            onClick = { onLifelineClicked(Lifeline.AUDIENCE) }
        )

        LifelineButton(
            text = "PARAR",
            iconRes = R.drawable.ic_give_up,
            enabled = true,
            onClick = { onLifelineClicked(Lifeline.GIVE_UP) }
        )
    }
}

@Composable
fun LifelineButton(
    text: String,
    iconRes: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {

    val buttonColor = if (enabled) lifelineActiveButtonColor else lifelineDisabledButtonColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(buttonColor, RoundedCornerShape(22.dp))
            .border(
                width = 3.dp,
                color = Color(0xFF2352FF),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(8.dp)
            .size(width = 80.dp, height = 90.dp)
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}