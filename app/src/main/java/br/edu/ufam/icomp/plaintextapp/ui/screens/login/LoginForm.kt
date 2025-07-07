package br.edu.ufam.icomp.plaintextapp.ui.screens.login

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Importe TUDO do material3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import br.edu.ufam.icomp.plaintextapp.activities.ListActivity
import br.edu.ufam.icomp.plaintextapp.viewmodel.LoginViewModel
import br.edu.ufam.icomp.plaintextapp.viewmodel.LoginUiState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults // Importe OutlinedTextFieldDefaults


@Composable
fun LoginForm(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginSuccessful, uiState.showLoginError) {
        if (uiState.loginSuccessful) {
            val nameToPass = uiState.loginText.ifEmpty { "Usuário" }
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra("login", nameToPass)
            context.startActivity(intent)
            loginViewModel.resetLoginStatus()
        } else if (uiState.showLoginError) {
            Toast.makeText(context, "Login/senha inválidos!", Toast.LENGTH_SHORT).show()
            loginViewModel.resetLoginStatus()
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
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Campo de Login
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login:", modifier = Modifier.width(70.dp), color = MaterialTheme.colorScheme.onSurface)
            OutlinedTextField(
                value = uiState.loginText,
                onValueChange = { loginViewModel.onLoginTextChanged(it) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    // REMOVIDO: textColor = MaterialTheme.colorScheme.onSurface, <-- REMOVA ESTA LINHA SE AINDA ESTIVER LÁ
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,   // <-- Corrigido
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // <-- Corrigido
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Senha
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Senha:", modifier = Modifier.width(70.dp), color = MaterialTheme.colorScheme.onSurface)
            OutlinedTextField(
                value = uiState.passwordText,
                onValueChange = { loginViewModel.onPasswordTextChanged(it) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    // REMOVIDO: textColor = MaterialTheme.colorScheme.onSurface, <-- REMOVA ESTA LINHA SE AINDA ESTIVER LÁ
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox "Salvar as informações de login"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.saveLoginInfo,
                onCheckedChange = { loginViewModel.onSaveLoginInfoChanged(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(text = "Salvar as informações de login", color = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Botão "Enviar"
        Button(
            onClick = { loginViewModel.onLoginClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(text = "Enviar", color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 18.sp)
        }
    }
}