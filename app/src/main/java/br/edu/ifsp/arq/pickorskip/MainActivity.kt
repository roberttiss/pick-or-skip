package br.edu.ifsp.arq.pickorskip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.arq.pickorskip.ui.components.GameScreen
import br.edu.ifsp.arq.pickorskip.ui.theme.PickOrSkipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PickOrSkipTheme {
                GameScreen()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GameScreenPreview() {
        PickOrSkipTheme {
            GameScreen()
        }
    }
}