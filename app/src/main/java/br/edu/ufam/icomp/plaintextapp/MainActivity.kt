package br.edu.ufam.icomp.plaintextapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel // Importe viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import br.edu.ufam.icomp.plaintextapp.ui.screens.login.MyAppBar
import br.edu.ufam.icomp.plaintextapp.ui.screens.login.LoginScreenContent // Importe LoginScreenContent
import br.edu.ufam.icomp.plaintextapp.ui.theme.PlainTextAppTheme

import br.edu.ufam.icomp.plaintextapp.ui.navigation.AppRoutes
import br.edu.ufam.icomp.plaintextapp.ui.screens.hello.HelloScreen
import br.edu.ufam.icomp.plaintextapp.viewmodel.LoginViewModel // Importe LoginViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlainTextAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        topBar = {
                            MyAppBar()
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = AppRoutes.LOGIN_SCREEN,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                // MUITO IMPORTANTE: Use a cor de fundo do tema, não hardcoded!
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            composable(AppRoutes.LOGIN_SCREEN) {
                                val loginViewModel: LoginViewModel = viewModel() // Cria/obtém a ViewModel aqui
                                // CHAMA AGORA LoginScreenContent e passa o navController e a ViewModel
                                LoginScreenContent(navController = navController, loginViewModel = loginViewModel)
                            }

                            composable(
                                route = AppRoutes.HELLO_SCREEN,
                                arguments = listOf(navArgument("name") {
                                    type = NavType.StringType
                                    nullable = false
                                })
                            ) { backStackEntry ->
                                val name = backStackEntry.arguments?.getString("name") ?: "Visitante"
                                HelloScreen(name = name)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Modo Retrato", widthDp = 320)
@Preview(name = "Modo Paisagem", widthDp = 640)
@Composable
fun PreviewUI() {
    PlainTextAppTheme {
        // O PreviewFullLoginScreen já usa viewModel() internamente
        br.edu.ufam.icomp.plaintextapp.ui.screens.login.PreviewFullLoginScreen()
    }
}