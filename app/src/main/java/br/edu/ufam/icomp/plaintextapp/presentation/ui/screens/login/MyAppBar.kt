package br.edu.ufam.icomp.plaintextapp.presentation.ui.screens.login

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
import androidx.compose.ui.platform.LocalContext

import br.edu.ufam.icomp.plaintextapp.presentation.activities.PreferencesActivity
import androidx.compose.material3.MaterialTheme // <-- IMPORTANTE: Importe MaterialTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyAppBar() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                "PlainText",
                color = MaterialTheme.colorScheme.onSurface // <-- Cor do texto do tema
            )
        },
        // ESTA CHAMADA ESTÁ CORRETA PARA MATERIAL 3
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface // <-- Cor de fundo da AppBar do tema
        ),
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface // <-- Cor do ícone do menu do tema
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Configurações", color = MaterialTheme.colorScheme.onSurface) }, // Cor do texto do item
                    onClick = {
                        val intent = Intent(context, PreferencesActivity::class.java)
                        context.startActivity(intent)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sobre", color = MaterialTheme.colorScheme.onSurface) }, // Cor do texto do item
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    )
}