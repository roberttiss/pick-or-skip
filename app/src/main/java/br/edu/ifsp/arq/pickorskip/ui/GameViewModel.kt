package br.edu.ifsp.arq.pickorskip.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.edu.ifsp.arq.pickorskip.models.GameManager
import br.edu.ifsp.arq.pickorskip.utils.getManager

class GameViewModel(
    context: Context,
    private val manager: GameManager = getManager(context)
) : ViewModel() {

    var gameResult by mutableStateOf(manager.gameResult)
        private set

    var currentQuestion by mutableStateOf(manager.getCurrentQuestion())
        private set

    var currentQuestionStep by mutableIntStateOf(manager.getCurrentQuestionStep())
        private set

    var isCheckpoint by mutableStateOf(manager.isCheckpoint)
        private set

    var prize by mutableIntStateOf(manager.prize)
        private set

    var canEliminate by mutableStateOf(manager.canEliminate)
        private set

    var canSkip by mutableStateOf(manager.canSkip)

        private set
    var canAskAudience by mutableStateOf(manager.canAskAudience)
        private set

    fun useEliminate() {
        if (manager.useEliminate()) {
            canEliminate = manager.canEliminate
        }
    }

    fun useSkip() {
        if (manager.useSkip()) {
            updateStateFromManager()
            canSkip = manager.canSkip
        }
    }

    fun useAskAudience() {
        if (manager.useAskAudience()) {
            canAskAudience = manager.canAskAudience
        }
    }

    fun answerQuestion(answerId: Int) {
        manager.answerQuestion(answerId)
        updateStateFromManager()
    }

    fun resetGame() {
        manager.resetGame()
        updateStateFromManager()
    }

    fun quitGame() {
        manager.quitGame()
        updateStateFromManager()
    }

    fun prizeForFail() = manager.getPrizeForFail()

    fun prizeForQuit() = manager.getPrizeForQuit()

    fun prizeForCurrentQuestion() = manager.getPrizeForCurrentQuestion()

    private fun updateStateFromManager() {
        gameResult = manager.gameResult
        currentQuestion = manager.getCurrentQuestion()
        currentQuestionStep = manager.getCurrentQuestionStep()
        isCheckpoint = manager.isCheckpoint
        prize = manager.prize
        canEliminate = manager.canEliminate
        canSkip = manager.canSkip
        canAskAudience = manager.canAskAudience
    }
}