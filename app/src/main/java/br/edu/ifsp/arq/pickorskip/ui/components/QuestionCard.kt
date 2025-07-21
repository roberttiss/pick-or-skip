package br.edu.ifsp.arq.pickorskip.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.arq.pickorskip.R
import br.edu.ifsp.arq.pickorskip.models.Question


@Composable
fun QuestionCard(
    question: Question,
    questionQuantity: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        QuestionCardBackground()
        QuestionContent(question = question, questionQuantity = questionQuantity)
    }
}

@Composable
fun QuestionCardBackground() {
    Image(
        painter = painterResource(id = R.drawable.question_card),
        contentDescription = "Painel da pergunta",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun QuestionContent(
    question: Question,
    questionQuantity: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 18.dp,
                start = 45.dp,
                end = 115.dp,
                bottom = 0.dp
            ),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "PERGUNTA $questionQuantity/15",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                question.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}