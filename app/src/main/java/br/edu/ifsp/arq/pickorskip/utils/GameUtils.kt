package br.edu.ifsp.arq.pickorskip.utils

import android.content.Context
import androidx.compose.runtime.toMutableStateList
import br.edu.ifsp.arq.pickorskip.R
import br.edu.ifsp.arq.pickorskip.models.GameManager
import br.edu.ifsp.arq.pickorskip.models.Answer
import br.edu.ifsp.arq.pickorskip.models.PrizeStep
import br.edu.ifsp.arq.pickorskip.models.Question
import br.edu.ifsp.arq.pickorskip.models.enums.QuestionLevel
import org.xmlpull.v1.XmlPullParser

fun getManager(context: Context): GameManager {

    val prizeSteps: List<PrizeStep> = loadPrizeSteps(context)

    val questions: List<Question> = loadQuestions(context)
    val manager = GameManager(questions, prizeSteps)

    return manager
}

fun loadQuestions(context: Context): List<Question> {
    val questions = mutableListOf<Question>()
    val parser = context.resources.getXml(R.xml.questions)

    var eventType = parser.eventType
    var level: QuestionLevel? = null
    var text: String? = null
    var answers = mutableListOf<Answer>()

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> when (parser.name) {
                "question" -> {
                    level = QuestionLevel.valueOf(parser.getAttributeValue(null, "level"))
                    answers = mutableListOf()
                }

                "text" -> text = parser.nextText()
                "answer" -> {
                    val id = parser.getAttributeValue(null, "id")?.toInt() ?: 0
                    val isCorrect = parser.getAttributeValue(null, "isCorrect") == "true"
                    val answerText = parser.nextText()
                    answers.add(Answer(id, answerText, isCorrect))
                }
            }

            XmlPullParser.END_TAG -> if (parser.name == "question" && text != null && level != null) {
                questions.add(
                    Question(
                        title = text,
                        level = level,
                        answers = answers.shuffled()
                    )
                )
                text = null
                level = null
            }
        }
        eventType = parser.next()
    }

    return questions
}

fun loadPrizeSteps(context: Context): List<PrizeStep> {
    val prizeSteps = mutableListOf<PrizeStep>()
    val parser = context.resources.getXml(R.xml.prizes)

    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "prize") {
            val question = parser.getAttributeValue(null, "question")?.toInt() ?: 0
            val value = parser.getAttributeValue(null, "value")?.toInt() ?: 0
            val checkpoint = parser.getAttributeValue(null, "checkpoint") == "true"
            prizeSteps.add(PrizeStep(question, value, checkpoint))
        }
        eventType = parser.next()
    }

    return prizeSteps
}
