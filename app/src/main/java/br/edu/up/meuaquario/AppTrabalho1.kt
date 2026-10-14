package br.edu.up.meuaquario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.edu.up.meuaquario.dados.Repositorio
import br.edu.up.meuaquario.ui.telas.AquarioNaoEncontrado
import br.edu.up.meuaquario.ui.telas.TelaDetalheAquario
import br.edu.up.meuaquario.ui.telas.TelaInicio
import br.edu.up.meuaquario.ui.telas.TelaListaAquarios

/** As três telas programadas no Trabalho 1. */
enum class TelaDoApp {
    INICIO,
    AQUARIOS,
    DETALHE
}

/**
 * Raiz do app do Trabalho 1.
 *
 * IMPORTANTE — o que muda em relação ao Trabalho 2:
 * aqui NÃO existe NavHost nem navController. A troca de tela é feita do jeito
 * que aprendemos até agora: um estado (`remember` + `mutableStateOf`) que
 * guarda qual tela está na frente, e um `when` que decide o que desenhar.
 *
 * O botão "Voltar" da tela de detalhe também não usa popBackStack: ele só
 * devolve o estado para AQUARIOS. É por isso que, no Trabalho 2, trocar isso
 * por rotas de verdade (com objeto Rotas e navController) aparece como uma das
 * decisões de organização do código.
 *
 * O id do aquário escolhido fica guardado aqui e é passado para a tela de
 * detalhe — que é como o detalhe mostra "o item certo" mesmo sem navegação.
 */
@Composable
fun AppTrabalho1() {

    var telaAtual by remember { mutableStateOf(TelaDoApp.INICIO) }
    var aquarioSelecionadoId by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (telaAtual) {

            TelaDoApp.INICIO -> TelaInicio(
                aoVerAquarios = { telaAtual = TelaDoApp.AQUARIOS }
            )

            TelaDoApp.AQUARIOS -> TelaListaAquarios(
                aoAbrirDetalhe = { id ->
                    aquarioSelecionadoId = id
                    telaAtual = TelaDoApp.DETALHE
                }
            )

            TelaDoApp.DETALHE -> {
                val aquario = Repositorio.aquarioPorId(aquarioSelecionadoId)
                if (aquario == null) {
                    AquarioNaoEncontrado(aoVoltar = { telaAtual = TelaDoApp.AQUARIOS })
                } else {
                    TelaDetalheAquario(
                        aquario = aquario,
                        aoVoltar = { telaAtual = TelaDoApp.AQUARIOS }
                    )
                }
            }
        }
    }
}
