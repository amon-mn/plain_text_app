package br.edu.ufam.icomp.plaintextapp.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import br.edu.ufam.icomp.plaintextapp.R // <-- SEU R.drawable

@Composable
fun GreenHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFF8BC34A)), // Cor verde
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.digital_key), // Usando o logo que você adicionou
                contentDescription = "Android Logo",
                modifier = Modifier
                    .size(130.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(text = "\"The most", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "secure", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "password", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "manager\"", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Bob and Alice", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}