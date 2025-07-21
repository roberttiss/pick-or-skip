package br.edu.ifsp.arq.pickorskip.models

import br.edu.ifsp.arq.pickorskip.models.enums.GameResult
import br.edu.ifsp.arq.pickorskip.models.enums.QuestionLevel

class GameManager(
    private val allQuestions: List<Question>,
    private val prizeSteps: List<PrizeStep>
) {
    var gameResult: GameResult = GameResult.NONE
        private set

    var canEliminate = true
        private set
    var canSkip = true
        private set
    var canAskAudience = true
        private set

    private var questions = setQuestions()

    val isCheckpoint: Boolean
        get() {
            val prizeStep = prizeSteps.getOrNull(currentQuestionIndex)

            return prizeStep?.checkpoint == true
        }

    val prize: Int
        get() {
            return when (gameResult) {
                GameResult.NONE -> getPrizeForCurrentQuestion()
                GameResult.WIN -> getPrizeForWin()
                GameResult.QUIT -> getPrizeForQuit()
                GameResult.GAME_OVER -> getPrizeForFail()
                else -> 0
            }
        }

    private var currentQuestionIndex: Int = 0
    private var correctAnswers = 0

    private fun setQuestions(): List<Question> {
        val easy = allQuestions.filter { it.level == QuestionLevel.EASY }.shuffled().take(5)
        val medium = allQuestions.filter { it.level == QuestionLevel.MEDIUM }.shuffled().take(5)
        val hard = allQuestions.filter { it.level == QuestionLevel.HARD }.shuffled().take(5)

        return (easy + medium + hard).sortedBy { it.level }
    }

    fun getCurrentQuestionStep() = currentQuestionIndex + 1

    fun getCurrentQuestion() = questions.getOrNull(currentQuestionIndex)

    fun answerQuestion(answerId: Int): Boolean {

        val question = getCurrentQuestion() ?: return false
        val answer = question.getAnswer(answerId) ?: return false

        if (answer.isCorrect) {
            correctAnswers++
            currentQuestionIndex++

            if (currentQuestionIndex >= questions.size)
                gameResult = GameResult.WIN

        } else {
            gameResult = GameResult.GAME_OVER
        }

        return answer.isCorrect
    }

    fun useEliminate(): Boolean {
        if (!canEliminate) return false

        val question = getCurrentQuestion() ?: return false

        val incorrectAnswers =
            question.answers.filter { !it.isCorrect && !it.isEliminated }.shuffled()
        incorrectAnswers.take(2).forEach { it.isEliminated = true }

        canEliminate = false
        return true
    }

    fun useSkip(): Boolean {
        if (!canSkip) return false

        currentQuestionIndex++
        correctAnswers++

        if (currentQuestionIndex >= questions.size)
            gameResult = GameResult.WIN

        canSkip = false
        return true
    }

    fun useAskAudience(): Boolean {
        if (!canAskAudience) return false

        val question = getCurrentQuestion() ?: return false
        val answers = question.answers

        val correct = answers.firstOrNull { it.isCorrect } ?: return false
        val incorrects = answers.filter { !it.isCorrect }

        val correctPercentage = (55..90).random()

        correct.audiencePercentage = correctPercentage
        var rest = 100 - correctPercentage

        for (i in incorrects.indices) {
            val remainingSlots = incorrects.size - i
            val value: Int

            if (i == incorrects.lastIndex) {
                value = rest
            } else {
                val max = rest - (remainingSlots - 1)
                value = if (max > 1) (1..max).random() else 1
            }

            incorrects[i].audiencePercentage = value
            rest -= value
        }

        canAskAudience = false
        return true
    }

    fun quitGame() {
        gameResult = GameResult.QUIT
    }

    fun resetGame() {
        currentQuestionIndex = 0
        correctAnswers = 0
        gameResult = GameResult.NONE
        canSkip = true
        canEliminate = true
        canAskAudience = true

        for (question in questions) {
            for (answer in question.answers) {
                answer.audiencePercentage = null
                answer.isEliminated = false
            }
        }

        questions = setQuestions();
    }

    fun getPrizeForCurrentQuestion(): Int {
        return prizeSteps.getOrNull(getCurrentQuestionStep() - 1)?.value ?: 0
    }

    fun getPrizeForQuit(): Int {
        return prizeSteps.getOrNull(correctAnswers - 1)?.value ?: 0
    }

    fun getPrizeForFail(): Int {
        val checkpoints = prizeSteps.filter { it.checkpoint }

        val lastCheckpoint = checkpoints.lastOrNull { it.question <= correctAnswers }

        return lastCheckpoint?.value ?: 0
    }

    private fun getPrizeForWin(): Int {
        return prizeSteps.getOrNull(correctAnswers - 1)?.value ?: 0
    }
}