package br.edu.ifsp.arq.pickorskip.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Answer(
    val id: Int,
    val text: String,
    val isCorrect: Boolean
){
    var isEliminated by mutableStateOf(false)
    var audiencePercentage by mutableStateOf<Int?>(null)
}