package br.edu.ufam.icomp.plaintextapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController // Importe NavController
import androidx.navigation.compose.rememberNavController // Importe rememberNavController (para previews)

import br.edu.ufam.icomp.plaintextapp.ui.theme.PlainTextAppTheme // <-- SEU TEMA DE APP


// Esta função representa APENAS o conteúdo da tela, sem a Top Bar
// Agora ela recebe o navController, que será passado da MainActivity
@Composable
fun LoginScreenContent(navController: NavController) { // <-- AGORA RECEBE NAVCONTROLLER
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        GreenHeader()
        LoginForm(navController = navController) // <-- PASSA O NAVCONTROLLER PARA LoginForm
    }
}

// Preview da APENAS do conteúdo da tela
@Preview(showBackground = true, showSystemUi = false, name = "Login Screen Content Preview")
@Composable
fun PreviewLoginScreenContent() {
    PlainTextAppTheme {
        // Para o preview, criamos um navController de "mentira"
        LoginScreenContent(navController = rememberNavController())
    }
}

// Preview COMPLETO da tela de login, incluindo a Top Bar
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true, name = "Full Login Screen Preview")
@Composable
fun PreviewFullLoginScreen() {
    PlainTextAppTheme {
        // Para o preview, criamos um navController de "mentira"
        val navController = rememberNavController()
        Scaffold(
            topBar = { MyAppBar() }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                // O PreviewFullLoginScreen agora chama LoginScreenContent
                // e passa o navController para ele
                LoginScreenContent(navController = navController)
            }
        }
    }
}