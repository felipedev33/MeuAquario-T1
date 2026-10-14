package br.edu.up.meuaquario.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.up.meuaquario.dados.Repositorio
import br.edu.up.meuaquario.ui.theme.AquaClaraSuave
import br.edu.up.meuaquario.ui.theme.AquaEscura
import br.edu.up.meuaquario.ui.theme.GradienteFim
import br.edu.up.meuaquario.ui.theme.GradienteInicio
import br.edu.up.meuaquario.ui.theme.SuperficieApp
import br.edu.up.meuaquario.ui.theme.TextoPrincipal
import br.edu.up.meuaquario.ui.theme.TextoSecundario
import br.edu.up.meuaquario.ui.theme.VerdeOk
import br.edu.up.meuaquario.ui.theme.VerdeSuave
import br.edu.up.meuaquario.ui.theme.VermelhoAlerta
import br.edu.up.meuaquario.ui.theme.VermelhoSuave

/**
 * TELA 1 do Trabalho 1 — Início.
 * Responsável: Felipe Gabriel da Silva
 *
 * Os "cartões" desta tela são feitos com Box + background + clip, e não com o
 * componente Card: no Trabalho 1 o pedido era praticar Row, Column e Box.
 *
 * Esta tela tem 4 funções Composables próprias:
 *   TelaInicio, CabecalhoDegrade, QuadroDeNumero, LinhaDeSituacao
 */
@Composable
fun TelaInicio(aoVerAquarios: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CabecalhoDegrade()

        Column(modifier = Modifier.padding(16.dp)) {

            // ---------- Row com três números ----------
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuadroDeNumero(
                    icone = Icons.Filled.WaterDrop,
                    valor = "${Repositorio.aquarios.size}",
                    rotulo = "aquários",
                    cor = AquaEscura,
                    modifier = Modifier.weight(1f)
                )
                QuadroDeNumero(
                    icone = Icons.Filled.SetMeal,
                    valor = "${Repositorio.totalDePeixes()}",
                    rotulo = "peixes",
                    cor = AquaEscura,
                    modifier = Modifier.weight(1f)
                )
                QuadroDeNumero(
                    icone = Icons.Filled.WaterDrop,
                    valor = "${Repositorio.volumeTotal()} L",
                    rotulo = "no total",
                    cor = AquaEscura,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Como estão seus aquários",
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal
            )

            Spacer(Modifier.height(10.dp))

            LinhaDeSituacao()

            Spacer(Modifier.height(20.dp))

            // ---------- Box com as dicas do app ----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AquaClaraSuave)
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Por que este aplicativo existe",
                        style = MaterialTheme.typography.titleMedium,
                        color = AquaEscura
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Quem cria peixe costuma anotar as medidas e a quantidade de " +
                            "peixes em papel ou não anota. O MeuAquário faz a conta que mais " +
                            "importa: quantos litros o aquário tem de verdade e quantos peixes " +
                            "cabem ali sem superlotar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundario
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ---------- Botão que leva para a lista ----------
            Button(
                onClick = aoVerAquarios,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.WaterDrop,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Ver meus aquários")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/** Faixa do topo, feita com um Box de fundo degradê. */
@Composable
private fun CabecalhoDegrade() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(Brush.linearGradient(listOf(GradienteInicio, GradienteFim)))
            .padding(horizontal = 20.dp, vertical = 26.dp)
    ) {
        Column {
            Text(
                text = "MeuAquário",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "O controle dos seus aquários e dos peixes que vivem neles",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

/** Quadradinho com ícone, número e rótulo. Feito com Box. */
@Composable
private fun QuadroDeNumero(
    icone: ImageVector,
    valor: String,
    rotulo: String,
    cor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieApp)
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(cor.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = cor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.titleLarge,
                color = TextoPrincipal
            )
            Text(
                text = rotulo,
                style = MaterialTheme.typography.labelSmall,
                color = TextoSecundario
            )
        }
    }
}

/** Aviso verde quando está tudo certo, vermelho quando algum aquário superlotou. */
@Composable
private fun LinhaDeSituacao() {
    val superlotados = Repositorio.aquariosSuperlotados()
    val tudoCerto = superlotados.isEmpty()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (tudoCerto) VerdeSuave else VermelhoSuave)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (tudoCerto) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                contentDescription = null,
                tint = if (tudoCerto) VerdeOk else VermelhoAlerta
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = if (tudoCerto) "Tudo em ordem" else "${superlotados.size} aquário(s) superlotado(s)",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (tudoCerto) VerdeOk else VermelhoAlerta
                )
                Text(
                    text = if (tudoCerto) {
                        "Nenhum aquário passou do volume recomendado."
                    } else {
                        "Abra a lista e veja qual deles está cheio demais."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            }
        }
    }
}
