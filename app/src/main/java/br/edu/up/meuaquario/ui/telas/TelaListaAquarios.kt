package br.edu.up.meuaquario.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.edu.up.meuaquario.dados.Aquario
import br.edu.up.meuaquario.dados.Repositorio
import br.edu.up.meuaquario.dados.StatusOcupacao
import br.edu.up.meuaquario.ui.theme.AmareloAtencao
import br.edu.up.meuaquario.ui.theme.AquaClaraSuave
import br.edu.up.meuaquario.ui.theme.AquaEscura
import br.edu.up.meuaquario.ui.theme.AquaPrimaria
import br.edu.up.meuaquario.ui.theme.AquaSecundaria
import br.edu.up.meuaquario.ui.theme.SuperficieApp
import br.edu.up.meuaquario.ui.theme.TextoPrincipal
import br.edu.up.meuaquario.ui.theme.TextoSecundario
import br.edu.up.meuaquario.ui.theme.VerdeOk
import br.edu.up.meuaquario.ui.theme.VermelhoAlerta

/**
 * TELA 2 do Trabalho 1 — Lista de aquários.
 *
 * Como este trabalho é de telas (a navegação de verdade só vem no Trabalho 2),
 * esta tela apenas exibe a lista de exemplo e faz as contas de volume e
 * ocupação. Cada cartão tem um botão que leva ao Detalhe.
 *
 * Decisões desta tela:
 *  - a lista é um `Column` com `verticalScroll` e um `forEach`, e não um
 *    LazyColumn: LazyColumn ainda não tinha sido visto em aula, e para poucos
 *    itens o Column resolve (no Trabalho 2 isso foi trocado por LazyColumn);
 *  - cada cartão é um Box (com Row e Column dentro) em vez de Card, porque o
 *    pedido do trabalho era praticar Row, Column e Box;
 *  - a barra de ocupação também é feita na mão, com dois Box sobrepostos.
 *
 * Funções Composables próprias: TelaListaAquarios, CabecalhoDaLista,
 * ItemDeAquario e BarraDeOcupacao.
 */
@Composable
fun TelaListaAquarios(aoAbrirDetalhe: (Int) -> Unit) {

    val aquarios = Repositorio.aquarios

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CabecalhoDaLista()

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Aquários cadastrados (${aquarios.size})",
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal
            )

            Spacer(Modifier.height(10.dp))

            aquarios.forEach { aquario ->
                ItemDeAquario(
                    aquario = aquario,
                    aoAbrir = { aoAbrirDetalhe(aquario.id) }
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/** Cabeçalho da lista, em Box com fundo cheio. */
@Composable
private fun CabecalhoDaLista() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AquaPrimaria)
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {
        Column {
            Text(
                text = "Meus aquários",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Veja as medidas, o volume calculado e a ocupação de cada um",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

/** Um aquário da lista: Box com Row e Column dentro, e o botão que abre o detalhe. */
@Composable
private fun ItemDeAquario(
    aquario: Aquario,
    aoAbrir: () -> Unit
) {
    val volume = Repositorio.volumeLitros(aquario)
    val ocupados = Repositorio.litrosOcupados(aquario.id)
    val percentual = Repositorio.percentualOcupacao(aquario)
    val status = Repositorio.statusOcupacao(aquario)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieApp)
            .padding(14.dp)
    ) {
        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AquaClaraSuave),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.WaterDrop,
                        contentDescription = null,
                        tint = AquaEscura
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = aquario.nome,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextoPrincipal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${aquario.comprimentoCm} × ${aquario.larguraCm} × " +
                            "${aquario.alturaCm} cm  ·  $volume L",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundario
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            BarraDeOcupacao(
                fracao = (percentual / 100f).coerceIn(0f, 1f),
                cor = corDoStatus(status)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "$ocupados L ocupados de $volume L · $percentual% · ${status.rotulo}",
                style = MaterialTheme.typography.labelSmall,
                color = TextoSecundario
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = aoAbrir,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver detalhes", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/** Barra de progresso feita na mão, com dois Box sobrepostos. */
@Composable
private fun BarraDeOcupacao(fracao: Float, cor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(50))
            .background(cor.copy(alpha = 0.18f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fracao)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(cor)
        )
    }
}

/** Cor de cada status de ocupação. */
private fun corDoStatus(status: StatusOcupacao): Color = when (status) {
    StatusOcupacao.LIVRE -> VerdeOk
    StatusOcupacao.ADEQUADO -> AquaSecundaria
    StatusOcupacao.ATENCAO -> AmareloAtencao
    StatusOcupacao.SUPERLOTADO -> VermelhoAlerta
}
