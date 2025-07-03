package br.edu.ufam.icomp.plaintextapp.ui.screens.login

import android.widget.Toast // Importe Toast para mensagens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.preference.PreferenceManager // Importe PreferenceManager
import androidx.compose.ui.platform.LocalContext // Importe LocalContext

import br.edu.ufam.icomp.plaintextapp.ui.navigation.AppRoutes

@Composable
fun LoginForm(navController: NavController) {
    val context = LocalContext.current // Obtenha o Context para acessar SharedPreferences e mostrar Toast

    // Obtenha as SharedPreferences
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Variáveis de estado para os campos de texto
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var saveLoginInfo by remember { mutableStateOf(false) } // Estado do checkbox

    // Carregar preferências ao iniciar o Composable
    // O LaunchedEffect é usado para executar efeitos colaterais (como carregar dados)
    // quando o Composable entra na composição ou uma de suas chaves muda.
    // Unit como chave significa que ele será executado apenas uma vez.
    LaunchedEffect(Unit) {
        val prefLogin = sharedPreferences.getString("login", "") ?: ""
        val prefPassword = sharedPreferences.getString("password", "") ?: ""
        val rememberLoginPref = sharedPreferences.getBoolean("remember_login", false)

        if (rememberLoginPref) {
            loginText = prefLogin
            passwordText = prefPassword
            saveLoginInfo = true // Marcar o checkbox se "remember_login" for true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Digite suas credenciais para continuar",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login:", modifier = Modifier.width(70.dp))
            OutlinedTextField(
                value = loginText,
                onValueChange = { loginText = it },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Senha:", modifier = Modifier.width(70.dp))
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox "Salvar as informações de login"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = saveLoginInfo,
                onCheckedChange = { saveLoginInfo = it }
            )
            Text(text = "Salvar as informações de login")
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Botão "Enviar"
        Button(
            onClick = {
                // Obtenha os valores de login e senha salvos nas preferências
                val savedLogin = sharedPreferences.getString("login", "") ?: ""
                val savedPassword = sharedPreferences.getString("password", "") ?: ""

                // Lógica de validação do login
                if (loginText == savedLogin && passwordText == savedPassword) {
                    // Login bem-sucedido: Salvar/Atualizar preferências e navegar
                    if (saveLoginInfo) {
                        // Salva as credenciais atuais nas preferências
                        sharedPreferences.edit()
                            .putString("login", loginText)
                            .putString("password", passwordText)
                            .putBoolean("remember_login", true) // Marca para lembrar
                            .apply() // Aplica as mudanças assincronamente
                    } else {
                        // Se o checkbox não estiver marcado, remove as credenciais salvas
                        sharedPreferences.edit()
                            .remove("login")
                            .remove("password")
                            .putBoolean("remember_login", false)
                            .apply()
                    }

                    val nameToPass = loginText.ifEmpty { "Usuário" }
                    navController.navigate(AppRoutes.createHelloRoute(nameToPass))
                } else {
                    // Login/senha inválidos: Mostra uma mensagem Toast
                    Toast.makeText(context, "Login/senha inválidos!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A689B))
        ) {
            Text(text = "Enviar", color = Color.White, fontSize = 18.sp)
        }
    }
}