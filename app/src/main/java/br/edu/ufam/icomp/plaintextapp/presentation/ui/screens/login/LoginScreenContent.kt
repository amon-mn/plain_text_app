package br.edu.ufam.icomp.plaintextapp.presentation.ui.screens.login

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import br.edu.ufam.icomp.plaintextapp.presentation.ui.theme.PlainTextAppTheme
import br.edu.ufam.icomp.plaintextapp.viewmodel.LoginViewModel

import androidx.compose.material3.MaterialTheme // <-- IMPORTANTE: Importe MaterialTheme


@Composable
fun LoginScreenContent(navController: NavController, loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // MUITO IMPORTANTE: Use a cor de fundo da superfície do tema
            .background(MaterialTheme.colorScheme.surface)
    ) {
        GreenHeader() // O GreenHeader manterá suas cores específicas (branding)
        LoginForm(navController = navController, loginViewModel = loginViewModel)
    }
}

// Preview da APENAS do conteúdo da tela
@Preview(showBackground = true, showSystemUi = false, name = "Login Screen Content Preview")
@Composable
fun PreviewLoginScreenContent() {
    PlainTextAppTheme {
        // Para o preview, criamos um navController de "mentira" e uma ViewModel
        LoginScreenContent(navController = rememberNavController(), loginViewModel = viewModel())
    }
}

// Preview COMPLETO da tela de login, incluindo a Top Bar
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true, name = "Full Login Screen Preview")
@Composable
fun PreviewFullLoginScreen() {
    PlainTextAppTheme {
        // Para o preview, criamos um navController de "mentira" e uma ViewModel
        val navController = rememberNavController()
        val loginViewModel: LoginViewModel = viewModel() // Cria/obtém a ViewModel para o preview
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
                // e passa o navController e a ViewModel para ele
                LoginScreenContent(navController = navController, loginViewModel = loginViewModel)
            }
        }
    }
}