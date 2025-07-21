package br.edu.ifsp.arq.pickorskip.models

import br.edu.ifsp.arq.pickorskip.models.enums.QuestionLevel

data class Question(
    val title: String,
    val level: QuestionLevel,
    val answers: List<Answer>
){
    fun getAnswer(id: Int): Answer? = answers.find {it.id == id }
}