package br.edu.up.meuaquario.dados

import androidx.compose.runtime.mutableStateListOf

/**
 * Status de ocupação de um aquário, calculado a partir dos peixes que vivem nele.
 */
enum class StatusOcupacao(val rotulo: String, val descricao: String) {
    LIVRE("Tranquilo", "Muita folga de espaço. Ainda cabe bastante peixe."),
    ADEQUADO("Bem povoado", "Ocupação saudável para o volume deste aquário."),
    ATENCAO("Quase cheio", "Perto do limite. Pense duas vezes antes de acrescentar."),
    SUPERLOTADO("Superlotado", "Passou do volume recomendado. Risco para os peixes.")
}

/**
 * De onde as telas do Trabalho 1 tiram os dados que exibem.
 *
 * Neste trabalho as três telas são de exibição: elas mostram a lista de
 * exemplo cadastrada aqui e fazem as contas. Não existe formulário nem
 * remoção — isso é conteúdo do Trabalho 2, quando a lista vira interativa.
 *
 * Os dados vivem só na memória, exatamente como o enunciado permite
 * ("dados podem viver apenas na variável de estado, sem se preocupar em salvar
 * nada permanentemente"). Não há banco de dados.
 *
 * mutableStateListOf é a lista reativa: quando ela muda, toda tela que a leu
 * se redesenha sozinha.
 */
object Repositorio {

    val aquarios = mutableStateListOf<Aquario>()
    val especies = mutableStateListOf<Especie>()

    init {
        dadosDeExemplo()
    }

    // ------------------------------------------------------------------
    // CONSULTAS
    // ------------------------------------------------------------------

    fun aquarioPorId(id: Int): Aquario? = aquarios.firstOrNull { it.id == id }

    /** Todas as espécies que apontam para este aquário. É a junção das duas listas. */
    fun especiesDoAquario(aquarioId: Int): List<Especie> =
        especies.filter { it.aquarioId == aquarioId }

    // ------------------------------------------------------------------
    // CÁLCULOS
    // ------------------------------------------------------------------

    /** Volume em litros = comprimento × largura × altura (em cm) ÷ 1000. */
    fun volumeLitros(aquario: Aquario): Int =
        (aquario.comprimentoCm * aquario.larguraCm * aquario.alturaCm) / 1000

    /**
     * Regra usada por aquaristas: 1 litro de água para cada centímetro de
     * peixe adulto. Serve para estimar a carga que a espécie representa.
     */
    fun litrosNecessarios(especie: Especie): Int =
        especie.tamanhoAdultoCm * especie.quantidade

    fun litrosOcupados(aquarioId: Int): Int =
        especiesDoAquario(aquarioId).sumOf { litrosNecessarios(it) }

    fun volumeTotal(): Int = aquarios.sumOf { volumeLitros(it) }

    fun totalDePeixes(): Int = especies.sumOf { it.quantidade }

    /** Percentual inteiro de ocupação. Pode passar de 100 quando superlotou. */
    fun percentualOcupacao(aquario: Aquario): Int {
        val volume = volumeLitros(aquario)
        if (volume <= 0) return 0
        return (litrosOcupados(aquario.id) * 100) / volume
    }

    fun statusOcupacao(aquario: Aquario): StatusOcupacao {
        val p = percentualOcupacao(aquario)
        return when {
            p > 100 -> StatusOcupacao.SUPERLOTADO
            p >= 90 -> StatusOcupacao.ATENCAO
            p >= 70 -> StatusOcupacao.ADEQUADO
            else -> StatusOcupacao.LIVRE
        }
    }

    fun aquariosSuperlotados(): List<Aquario> =
        aquarios.filter { statusOcupacao(it) == StatusOcupacao.SUPERLOTADO }

    // ------------------------------------------------------------------
    // DADOS DE EXEMPLO — é com isto que as telas do trabalho aparecem cheias
    // ------------------------------------------------------------------

    private fun dadosDeExemplo() {

        aquarios.addAll(
            listOf(
                // 100 × 40 × 50 cm = 200 L
                Aquario(1, "Comunitário da sala", 100, 40, 50, TipoAquario.DOCE),
                // 60 × 30 × 36 cm = 64 L
                Aquario(2, "Plantado 60P", 60, 30, 36, TipoAquario.PLANTADO),
                // 45 × 30 × 30 cm = 40 L
                Aquario(3, "Nano marinho", 45, 30, 30, TipoAquario.MARINHO),
                // 40 × 25 × 30 cm = 30 L  -> este fica superlotado de propósito
                Aquario(4, "Aquário do Huan", 40, 25, 30, TipoAquario.DOCE)
            )
        )

        especies.addAll(
            listOf(
                // 3 cm × 12 = 36 L no Comunitário (que tem 200 L)  -> 30 %
                Especie(1, "Neon Cardinal", "Paracheirodon innesi", 1, 12, 3, 6.5),
                // 6 cm × 4 = 24 L  ->  soma 60 L no Comunitário
                Especie(2, "Coridora Paleatus", "Corydoras paleatus", 1, 4, 6, 7.0),

                // 4 cm × 8 = 32 L no Plantado (que tem 64 L)
                Especie(3, "Tetra Serpae", "Hyphessobrycon eques", 2, 8, 4, 6.8),
                // 6 cm × 2 = 12 L  ->  soma 44 L no Plantado = 68 %
                Especie(4, "Betta", "Betta splendens", 2, 2, 6, 7.0),

                // 8 cm × 2 = 16 L no Nano marinho (que tem 40 L)  -> 40 %
                Especie(5, "Palhaço Ocelaris", "Amphiprion ocellaris", 3, 2, 8, 8.2),

                // 5 cm × 10 = 50 L no aquário de 30 L  -> 166 % SUPERLOTADO
                Especie(6, "Guppy", "Poecilia reticulata", 4, 10, 5, 7.2)
            )
        )
    }
}
