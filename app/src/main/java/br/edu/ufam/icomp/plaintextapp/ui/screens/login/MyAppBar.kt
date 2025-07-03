package br.edu.ufam.icomp.plaintextapp.ui.screens.login // <-- SEU PACOTE

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import br.edu.ufam.icomp.plaintextapp.activities.PreferencesActivity

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyAppBar() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current // Obtenha o Context aqui

    TopAppBar(
        title = { Text("PlainText") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Configurações") },
                    onClick = {
                        // Inicia a PreferencesActivity
                        val intent = Intent(context, PreferencesActivity::class.java)
                        context.startActivity(intent)
                        expanded = false // Fecha o menu após o clique
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sobre") },
                    onClick = {
                        // Lógica para Sobre (ex: mostrar um diálogo simples)
                        // Para um diálogo simples em Compose, não precisaríamos de uma Activity separada.
                        // Por enquanto, apenas fechar o menu.
                        expanded = false
                    }
                )
            }
        }
    )
}