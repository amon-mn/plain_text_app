package br.edu.ufam.icomp.plaintextapp.ui.screens.login

import android.content.Intent // Importe Intent
import android.content.SharedPreferences
import android.widget.Toast
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
import androidx.preference.PreferenceManager
import androidx.compose.ui.platform.LocalContext

import br.edu.ufam.icomp.plaintextapp.ui.navigation.AppRoutes
import br.edu.ufam.icomp.plaintextapp.activities.ListActivity // Importe ListActivity


@Composable
fun LoginForm(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var saveLoginInfo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val prefLogin = sharedPreferences.getString("login", "") ?: ""
        val prefPassword = sharedPreferences.getString("password", "") ?: ""
        val rememberLoginPref = sharedPreferences.getBoolean("remember_login", false)

        if (rememberLoginPref) {
            loginText = prefLogin
            passwordText = prefPassword
            saveLoginInfo = true
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
                val savedLogin = sharedPreferences.getString("login", "") ?: ""
                val savedPassword = sharedPreferences.getString("password", "") ?: ""

                if (loginText == savedLogin && passwordText == savedPassword) {
                    if (saveLoginInfo) {
                        sharedPreferences.edit()
                            .putString("login", loginText)
                            .putString("password", passwordText)
                            .putBoolean("remember_login", true)
                            .apply()
                    } else {
                        sharedPreferences.edit()
                            .remove("login")
                            .remove("password")
                            .putBoolean("remember_login", false)
                            .apply()
                    }

                    // --- MUDANÇA AQUI: Inicia ListActivity com Intent ---
                    val intent = Intent(context, ListActivity::class.java)
                    // Opcional: Se quiser passar o login para a ListActivity
                    intent.putExtra("login", loginText)
                    context.startActivity(intent)
                    // --- FIM DA MUDANÇA ---

                } else {
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