package br.edu.ufam.icomp.plaintextapp.dao // <-- SEU PACOTE

import android.content.Context
import android.widget.Toast
import br.edu.ufam.icomp.plaintextapp.model.Password // Importe a classe Password

class PasswordDAO(private val context: Context) {

    // Usamos um companion object para simular o 'static' do Java
    companion object {
        private val passwordsList = ArrayList<Password>()
    }

    // Metodo getList
    fun getList(): ArrayList<Password> {
        // Inicializa a lista com dados de exemplo se estiver vazia
        if (passwordsList.size == 0) {
            passwordsList.add(Password(0, "Facebook", "dovahkiin@gmail.com", "FusRoDah123", ""))
            passwordsList.add(Password(1, "GMail", "dovahkiin", "Teste123", ""))
            passwordsList.add(Password(2, "IComp", "dfrd@icomp.ufam.edu.br", "Java4242", "Mudar a senha!"))
            passwordsList.add(Password(3, "Steam", "dovahkiin", "FusRoDah123", "Conta do Brasil"))
        }
        return passwordsList
    }

    // Metodo add
    fun add(password: Password): Boolean {
        // Define o ID da nova senha com base no tamanho atual da lista
        password.id = passwordsList.size
        passwordsList.add(password)
        Toast.makeText(context, "Senha salva!", Toast.LENGTH_SHORT).show()
        return true
    }

    // Metodo update
    fun update(password: Password): Boolean {
        // Atualiza a senha na posição do ID dela
        passwordsList[password.id] = password
        Toast.makeText(context, "Senha atualizada!", Toast.LENGTH_SHORT).show()
        return true
    }

    // Metodo get
    fun get(id: Int): Password {
        return passwordsList[id]
    }
}