package br.edu.up.meuaquario.dados

/**
 * Tipo de aquário.
 *
 * É um enum e não um texto solto de propósito: assim o compilador impede
 * gravar "agua doce" com erro de digitação e todo Chip da tela já tem
 * um rótulo bonito pronto para exibir.
 */
enum class TipoAquario(val rotulo: String) {
    DOCE("Água doce"),
    PLANTADO("Plantado"),
    MARINHO("Marinho")
}

/**
 * DATA CLASS 1 do trabalho — o aquário.
 *
 * Detalhe importante: a capacidade em litros NÃO é um campo guardado aqui.
 * Ela é sempre calculada a partir das três medidas, em Repositorio.volumeLitros().
 * Guardar o litro "na mão" deixaria a conta desatualizada se alguém corrigisse
 * uma medida depois.
 */
data class Aquario(
    val id: Int,
    val nome: String,
    val comprimentoCm: Int,
    val larguraCm: Int,
    val alturaCm: Int,
    val tipo: TipoAquario
)

/**
 * DATA CLASS 2 do trabalho — a espécie de peixe.
 *
 * O campo [aquarioId] é o que costura as duas listas do app: é por ele que o
 * Detalhe do Aquário descobre quais peixes vivem ali, e é por ele que o
 * Detalhe da Espécie mostra em qual aquário ela está.
 */
data class Especie(
    val id: Int,
    val nome: String,
    val nomeCientifico: String,
    val aquarioId: Int,
    val quantidade: Int,
    val tamanhoAdultoCm: Int,
    val phIdeal: Double
)
