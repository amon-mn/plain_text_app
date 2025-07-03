package br.edu.ufam.icomp.plaintextapp.ui.screens.hello



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ufam.icomp.plaintextapp.ui.theme.PlainTextAppTheme

@Composable
fun HelloScreen(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olá, $name!",
            fontSize = 32.sp,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Bem-vindo ao PlainText!",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHelloScreen() {
    PlainTextAppTheme {
        HelloScreen("Usuário Teste") // Exemplo para o Preview
    }
}