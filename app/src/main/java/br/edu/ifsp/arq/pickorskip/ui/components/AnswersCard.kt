package br.edu.ifsp.arq.pickorskip.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.ifsp.arq.pickorskip.models.Answer
import br.edu.ifsp.arq.pickorskip.ui.theme.answerActiveButtonColor
import br.edu.ifsp.arq.pickorskip.ui.theme.answerDisabledButtonColor

@Composable
fun AnswersCard(
    answers: List<Answer>,
    onAnswerSelected: (Int) -> Unit
) {

    Column(Modifier.fillMaxWidth(0.80f)) {

        answers.forEachIndexed { index, answer ->
            AnswerButton(
                index = index,
                answer = answer,
                onClick = { onAnswerSelected(answer.id) }
            )
        }
    }
}

@Composable
private fun AnswerButton(
    index: Int,
    answer: Answer,
    onClick: () -> Unit
) {

    val isDisabled = answer.isEliminated
    val buttonColor = if (isDisabled) answerDisabledButtonColor else answerActiveButtonColor

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        enabled = !isDisabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ('A' + index).toString(),
                        color = answerActiveButtonColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = answer.text,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            answer.audiencePercentage?.let { percent ->
                Text(
                    text = "$percent%",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}