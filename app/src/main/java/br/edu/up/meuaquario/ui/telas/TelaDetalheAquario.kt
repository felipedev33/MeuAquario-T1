package br.edu.up.meuaquario.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
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
 * TELA 3 do Trabalho 1 — Detalhe do Aquário.
 * Responsável: Felipe Gabriel da Silva
 *
 * Recebe o aquário já escolhido por parâmetro (o id fica guardado na raiz do
 * app) e mostra:
 *  - o volume CALCULADO a partir das três medidas (não é campo guardado);
 *  - a ocupação: quantos litros os peixes já pedem daquele aquário;
 *  - a lista de peixes que vivem ali, juntando as DUAS listas do app.
 *
 * Funções Composables próprias: TelaDetalheAquario, CabecalhoDoDetalhe,
 * CartaoDeVolume, CartaoDeOcupacao, CartaoDeEspecies, LinhaDeEspecie,
 * BarraDeOcupacao e AquarioNaoEncontrado.
 */
@Composable
fun TelaDetalheAquario(aquario: Aquario, aoVoltar: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CabecalhoDoDetalhe(aquario = aquario, aoVoltar = aoVoltar)

        Column(modifier = Modifier.padding(16.dp)) {

            CartaoDeVolume(aquario)
            Spacer(Modifier.height(14.dp))
            CartaoDeOcupacao(aquario)
            Spacer(Modifier.height(14.dp))
            CartaoDeEspecies(aquario)

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = aoVoltar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Voltar para a lista")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

/** Cabeçalho com degradê, nome do aquário e botão de voltar. */
@Composable
private fun CabecalhoDoDetalhe(aquario: Aquario, aoVoltar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(Brush.linearGradient(listOf(AquaPrimaria, AquaSecundaria)))
            .padding(horizontal = 12.dp, vertical = 18.dp)
    ) {
        Column {
            IconButton(onClick = aoVoltar) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = aquario.nome,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${aquario.tipo.rotulo} · ${aquario.comprimentoCm} × " +
                            "${aquario.larguraCm} × ${aquario.alturaCm} cm",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

/** Volume calculado: mostra a conta inteira, para não parecer número mágico. */
@Composable
private fun CartaoDeVolume(aquario: Aquario) {
    val volume = Repositorio.volumeLitros(aquario)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieApp)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Capacidade calculada",
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${aquario.comprimentoCm} × ${aquario.larguraCm} × " +
                    "${aquario.alturaCm} ÷ 1000",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$volume",
                    style = MaterialTheme.typography.headlineMedium,
                    color = AquaPrimaria,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "litros",
                    style = MaterialTheme.typography.titleMedium,
                    color = AquaPrimaria,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

/** Ocupação do aquário pelos peixes cadastrados. */
@Composable
private fun CartaoDeOcupacao(aquario: Aquario) {
    val volume = Repositorio.volumeLitros(aquario)
    val ocupados = Repositorio.litrosOcupados(aquario.id)
    val percentual = Repositorio.percentualOcupacao(aquario)
    val status = Repositorio.statusOcupacao(aquario)
    val cor = corDoStatus(aquario)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieApp)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Ocupação",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextoPrincipal,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(cor.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = status.rotulo,
                        style = MaterialTheme.typography.labelSmall,
                        color = cor
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            BarraDeOcupacao(
                fracao = (percentual / 100f).coerceIn(0f, 1f),
                cor = cor
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "$ocupados L de $volume L · $percentual%",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoPrincipal,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = status.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSecundario
            )

            if (status == StatusOcupacao.SUPERLOTADO) {
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = VermelhoAlerta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Tire alguns peixes ou troque por um aquário maior.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VermelhoAlerta
                    )
                }
            }
        }
    }
}

/** Junta as duas listas: as espécies que escolheram este aquário. */
@Composable
private fun CartaoDeEspecies(aquario: Aquario) {
    val especies = Repositorio.especiesDoAquario(aquario.id)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SuperficieApp)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Peixes que vivem aqui (${especies.size})",
                style = MaterialTheme.typography.titleMedium,
                color = TextoPrincipal
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Espécies cadastradas na outra lista que apontam para este aquário.",
                style = MaterialTheme.typography.labelSmall,
                color = TextoSecundario
            )
            Spacer(Modifier.height(10.dp))

            if (especies.isEmpty()) {
                Text(
                    text = "Nenhuma espécie vive aqui ainda.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            } else {
                especies.forEach { especie ->
                    LinhaDeEspecie(
                        nome = especie.nome,
                        nomeCientifico = especie.nomeCientifico,
                        detalhe = "${especie.tamanhoAdultoCm} cm × ${especie.quantidade}" +
                            "  ·  ${Repositorio.litrosNecessarios(especie)} L"
                    )
                }
            }
        }
    }
}

/** Uma linha da lista de peixes, montada com Row dentro de Box. */
@Composable
private fun LinhaDeEspecie(nome: String, nomeCientifico: String, detalhe: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AquaClaraSuave)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.SetMeal,
                contentDescription = null,
                tint = AquaEscura,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextoPrincipal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = nomeCientifico,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextoSecundario,
                    fontStyle = FontStyle.Italic
                )
            }
            Text(
                text = detalhe,
                style = MaterialTheme.typography.labelSmall,
                color = AquaEscura,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/** Barra de progresso feita com dois Box sobrepostos. */
@Composable
private fun BarraDeOcupacao(fracao: Float, cor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
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

/** Chamada quando o aquário escolhido não existe mais. */
@Composable
fun AquarioNaoEncontrado(aoVoltar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            tint = VermelhoAlerta,
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Aquário não encontrado",
            style = MaterialTheme.typography.titleMedium,
            color = TextoPrincipal
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = aoVoltar) {
            Text("Voltar para a lista")
        }
    }
}

private fun corDoStatus(aquario: Aquario): Color =
    when (Repositorio.statusOcupacao(aquario)) {
        StatusOcupacao.LIVRE -> VerdeOk
        StatusOcupacao.ADEQUADO -> AquaSecundaria
        StatusOcupacao.ATENCAO -> AmareloAtencao
        StatusOcupacao.SUPERLOTADO -> VermelhoAlerta
    }
