package br.edu.ufam.icomp.plaintextapp.presentation.ui.theme

import androidx.compose.ui.graphics.Color

// Cores para o Tema Claro
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Cores personalizadas do seu app (verde e azul)
val AppGreen = Color(0xFF8BC34A) // Seu verde original
val AppBlue = Color(0xFF4A689B)  // Seu azul original

// --- NOVAS CORES PARA O TEMA ESCURO ---
val DarkBackground = Color(0xFF121212) // Fundo escuro quase preto
val DarkSurface = Color(0xFF1E1E1E)    // Superfícies um pouco mais claras que o fundo
val DarkPrimary = Color(0xFFBB86FC)    // Cor primária para texto/elementos em fundo escuro (ex: um roxo claro)
val DarkOnPrimary = Color(0xFF000000)  // Cor para conteúdo sobre a primária (preto)
val DarkSecondary = Color(0xFF03DAC6)  // Cor secundária (ex: um ciano)
val DarkOnSecondary = Color(0xFF000000)
val DarkTertiary = Color(0xFF3700B3)   // Cor terciária
val DarkOnTertiary = Color(0xFFFFFFFF) // Branco para conteúdo sobre terciária
val DarkOnBackground = Color(0xFFFFFFFF) // Cor de texto principal sobre o fundo escuro (branco)
val DarkOnSurface = Color(0xFFFFFFFF)    // Cor de texto sobre a superfície escura (branco)
val DarkError = Color(0xFFCF6679)      // Cor de erro
val DarkOnError = Color(0xFF000000)    // Cor de texto sobre o erro

// Você pode ajustar AppGreen e AppBlue para como eles se comportam no dark mode,
// ou criar variantes DarkAppGreen, DarkAppBlue se quiser cores diferentes.
// Por enquanto, vamos reutilizá-las e ver como ficam.