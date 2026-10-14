package br.edu.up.meuaquario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.up.meuaquario.ui.theme.MeuAquarioTheme

/**
 * Único Activity do app (Trabalho 1).
 * Aqui ainda não existe NavHost: a troca de telas é feita por estado,
 * dentro do composable AppTrabalho1().
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MeuAquarioTheme {
                AppTrabalho1()
            }
        }
    }
}
