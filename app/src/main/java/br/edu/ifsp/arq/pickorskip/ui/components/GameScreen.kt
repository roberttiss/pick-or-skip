package br.edu.ifsp.arq.pickorskip.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.edu.ifsp.arq.pickorskip.R
import br.edu.ifsp.arq.pickorskip.models.enums.GameResult
import br.edu.ifsp.arq.pickorskip.models.enums.Lifeline
import br.edu.ifsp.arq.pickorskip.ui.theme.mainBackgroundColor
import br.edu.ifsp.arq.pickorskip.ui.GameViewModel

@Composable
fun GameScreen() {
    val context = LocalContext.current
    val viewModel = remember { GameViewModel(context) }

    GameResultDialog(viewModel.gameResult, viewModel, viewModel.prize)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoHeader()

        viewModel.currentQuestion?.let { question ->
            QuestionCard(question, viewModel.currentQuestionStep)

            Spacer(Modifier.height(18.dp))

            AnswersCard(
                answers = question.answers,
                onAnswerSelected = { answerId -> viewModel.answerQuestion(answerId) }
            )
        }

        Spacer(Modifier.height(60.dp))

        ConsequencesCard(
            wrongAnswerPrize = viewModel.prizeForFail(),
            giveUpPrize = viewModel.prizeForQuit(),
            answeredCorrectlyPrize = viewModel.prizeForCurrentQuestion()
        )

        LifelineCard(
            canEliminate = viewModel.canEliminate,
            canSkip = viewModel.canSkip,
            canAskAudience = viewModel.canAskAudience,
            onLifelineClicked = { lifeline ->
                when (lifeline) {
                    Lifeline.ELIMINATE -> viewModel.useEliminate()
                    Lifeline.SKIP -> viewModel.useSkip()
                    Lifeline.AUDIENCE -> viewModel.useAskAudience()
                    Lifeline.GIVE_UP -> viewModel.quitGame()
                }
            }
        )
    }
}

@Composable
fun LogoHeader() {
    Spacer(Modifier.height(12.dp))
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier.height(100.dp)
    )
    Spacer(Modifier.height(12.dp))
}

@Composable
fun GameResultDialog(
    gameResult: GameResult,
    viewModel: GameViewModel,
    prize: Int
) {
    val formatedPrize = formatPrize(prize)

    when (gameResult) {

        GameResult.GAME_OVER -> PrizeDialog(
            title = "Você perdeu!",
            message = "Você ganhou R$ $formatedPrize",
            viewModel = viewModel
        )

        GameResult.WIN -> PrizeDialog(
            title = "Parabéns!",
            message = "Você ganhou o prêmio máximo de R$ $formatedPrize!",
            viewModel = viewModel
        )

        GameResult.QUIT -> PrizeDialog(
            title = "Você desistiu!",
            message = "Você saiu do jogo com R$ $formatedPrize",
            viewModel = viewModel
        )

        GameResult.NONE -> {}
    }
}

@Composable
fun PrizeDialog(
    title: String,
    message: String,
    viewModel: GameViewModel,
) {
    AlertDialog(
        onDismissRequest = { viewModel.resetGame() },
        confirmButton = {
            Button(onClick = { viewModel.resetGame() }) {
                Text("OK")
            }
        },
        title = { Text(title) },
        text = { Text(message) }
    )
}