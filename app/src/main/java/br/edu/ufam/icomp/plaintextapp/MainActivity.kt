package br.edu.ufam.icomp.plaintextapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import br.edu.ufam.icomp.plaintextapp.ui.screens.login.MyAppBar
import br.edu.ufam.icomp.plaintextapp.ui.theme.PlainTextAppTheme

import br.edu.ufam.icomp.plaintextapp.ui.navigation.AppRoutes
import br.edu.ufam.icomp.plaintextapp.ui.screens.hello.HelloScreen
import br.edu.ufam.icomp.plaintextapp.ui.screens.login.LoginScreenContent


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
                    val navController = rememberNavController() // NavController aqui

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
                                .background(Color.White)
                        ) {
                            composable(AppRoutes.LOGIN_SCREEN) {
                                // CHAMA AGORA LoginScreenContent e passa o navController
                                LoginScreenContent(navController = navController)
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
        // Chame o preview completo da sua tela de login para ver o resultado aqui
        // (este Preview já contém o Scaffold e a MyAppBar para simular a Main Activity)
        br.edu.ufam.icomp.plaintextapp.ui.screens.login.PreviewFullLoginScreen()
    }
}