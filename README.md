# MeuAquário — Trabalho 1

Aplicativo Android em Jetpack Compose para quem cria peixe de aquário: guarda as medidas de
cada aquário, calcula o volume em litros e mostra quantos peixes cabem ali sem superlotar.

**Disciplina:** Desenvolvimento de Aplicativos Móveis
**Entrega:** Trabalho 1 — Planejamento de produto + primeiras telas em Jetpack Compose
**Integrantes:** Felipe Gabriel da Silva · Huan Machado de Oliveira

---

## Sobre o tema

Aquarismo. A escolha foi feita pensando em algo que obrigasse a pensar em cálculo de verdade:
as telas do trabalho pedem Row, Column e Box convivendo com número, texto e estado — e aqui os
números não são decorativos, eles são o produto.

O vocabulário do app:

| Termo | O que é |
|---|---|
| **Aquário** | Um tanque, com nome, três medidas em centímetros e um tipo de água |
| **Espécie** | Um peixe, com nome popular, nome científico, quantidade, tamanho adulto e pH ideal |
| **Volume** | `comprimento × largura × altura ÷ 1000` — **calculado**, nunca guardado |
| **Carga** | `tamanho adulto × quantidade` — os litros que aquela espécie ocupa |
| **Ocupação** | carga dos peixes ÷ volume do aquário. A regra usada por aquaristas é 1 litro por centímetro de peixe |

A mesma conta é o que decide a cor da barra em cada cartão: verde abaixo de 70 %, ciano de 70 a
89 %, laranja de 90 a 100 % e vermelho acima de 100 % (superlotado).

---

## As três telas programadas neste trabalho

| Tela | Arquivo | Responsável |
|---|---|---|
| Início | `app/src/main/java/br/edu/up/meuaquario/ui/telas/TelaInicio.kt` | Felipe Gabriel da Silva |
| Lista de Aquários | `app/src/main/java/br/edu/up/meuaquario/ui/telas/TelaListaAquarios.kt` | Huan Machado de Oliveira |
| Detalhe do Aquário | `app/src/main/java/br/edu/up/meuaquario/ui/telas/TelaDetalheAquario.kt` | Felipe Gabriel da Silva |

Composables próprios por tela: a Início tem 4, a Lista tem 4 e o Detalhe tem 8 — bem acima do
mínimo de 2 por tela. Todas usam Row, Column e Box, todas têm pelo menos um Button e todas foram
estilizadas com a paleta definida em `ui/theme/Color.kt`.

### O que este trabalho faz e o que ele ainda não faz — de propósito

O Trabalho 1 vai até onde a aula tinha chegado. As três telas são **de exibição**: elas mostram
a lista de exemplo e fazem as contas, e cada uma tem o seu botão.

- **Não existe NavHost nem navController.** A troca de tela é feita por estado, em
  `AppTrabalho1.kt`: um `remember { mutableStateOf(...) }` guarda qual das três telas está na
  frente e um `when` decide o que desenhar. Foi exatamente o que aprendemos até aqui, e é o
  suficiente para apresentar as três telas em sala.
- **A lista é um `Column` com `verticalScroll` e um `forEach`**, e não um `LazyColumn` — LazyColumn
  ainda não tinha sido visto.
- **Os "cartões" são feitos com Box** + `background` + `clip`, porque o pedido era praticar Box.
- **Não há formulário nem remoção de item**: as telas exibem os dados de exemplo cadastrados em
  `dados/Repositorio.kt`. Fazer a lista mudar pela interface é conteúdo do Trabalho 2.
- **Os dados vivem só na memória.** Não há banco de dados: fechar o app perde o que foi alterado.

Isso é o ponto de partida documentado para o **Trabalho 2**, onde cada uma dessas decisões foi
trocada pela versão de verdade (rotas nomeadas, LazyColumn, Card, formulários próprios,
adicionar e remover).

---

## Documentação da idealização

Está em [`documentacao/mockups-e-canvas.html`](documentacao/mockups-e-canvas.html) — abra no
navegador. O documento tem quatro partes:

1. **A — as 10 telas idealizadas no papel.** Cada molde mostra onde cada elemento fica. Serve de
   guia para o esboço à mão.
2. **B — as 5 telas refeitas em ferramenta digital**, com a especificação de cada uma: quais
   elementos existem, onde estão posicionados e o que cada botão faz.
3. **C — o mapa de navegação**, com cada tela como uma caixa e cada caminho como uma seta.
4. **D — o Canvas**, respondendo por que este aplicativo existe.

Para gerar o PDF da entrega: abra o arquivo no navegador e use **Ctrl+P → Salvar como PDF**
(o layout já está preparado para impressão, com uma seção por página).

---

## Como rodar o projeto

### No Android Studio

1. Abra o Android Studio e escolha **Open**.
2. Aponte para a pasta deste repositório (a que contém o `settings.gradle.kts`).
3. Espere o *Gradle sync* terminar — na primeira vez ele baixa as dependências do Compose.
4. Escolha um emulador ou um aparelho conectado e clique em **Run ▶**.

### Pela linha de comando

Precisa de JDK 17 e do SDK do Android configurado em `local.properties` (`sdk.dir=...`).

```bash
# Linux / macOS
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

O APK sai em `app/build/outputs/apk/debug/app-debug.apk`.
Para instalar em um aparelho conectado por USB com depuração ativada:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

> **Este app tem um `applicationId` diferente do Trabalho 2** (`br.edu.up.meuaquario.t1`) e
> aparece no aparelho como **"MeuAquário T1"**. É de propósito: são dois aplicativos, e se os
> dois usassem o mesmo identificador um instalaria por cima do outro — o segundo apagaria o
> primeiro do celular. Assim os dois podem ficar instalados ao mesmo tempo.

Versões usadas: AGP 8.2.0 · Kotlin 1.9.22 · Compose BOM 2024.02.01 · `compileSdk` 34 · `minSdk` 24.

---

## Estrutura do projeto

```
app/src/main/java/br/edu/up/meuaquario/
├── MainActivity.kt              # abre o app e chama AppTrabalho1()
├── AppTrabalho1.kt              # o estado que decide qual das 3 telas está na frente
├── dados/
│   ├── Modelos.kt               # data class Aquario e data class Especie
│   └── Repositorio.kt           # os dados de exemplo e todos os cálculos
└── ui/
    ├── theme/
    │   ├── Color.kt             # a paleta do app
    │   └── Theme.kt             # o tema do Material 3
    └── telas/
        ├── TelaInicio.kt
        ├── TelaListaAquarios.kt
        └── TelaDetalheAquario.kt
```

Todas as contas do aplicativo ficam em `dados/Repositorio.kt`:

```kotlin
fun volumeLitros(aquario: Aquario): Int =
    (aquario.comprimentoCm * aquario.larguraCm * aquario.alturaCm) / 1000

fun litrosNecessarios(especie: Especie): Int =
    especie.tamanhoAdultoCm * especie.quantidade
```

Nenhum litro é guardado como campo: o volume é recalculado a cada leitura. Guardar o valor "na mão"
deixaria a conta desatualizada se alguém corrigisse uma medida depois.

---

## Checklist do trabalho

- [x] 3 telas, uma por integrante do grupo
- [x] Cada tela com pelo menos 2 funções Composables próprias
- [x] Row, Column e Box usados para organizar os elementos
- [x] Pelo menos 1 Button em cada tela, posicionado
- [x] Estilização: cores de fundo, cores e tamanhos de texto, espaçamentos e alinhamentos
- [x] 10 telas idealizadas no papel (documentação, seção A)
- [x] 5 telas refeitas em ferramenta digital (documentação, seção B)
- [x] Diagrama de fluxo de navegação (documentação, seção C)
- [x] Canvas respondido (documentação, seção D)
