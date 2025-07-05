package br.edu.ufam.icomp.plaintextapp.activities // <-- SEU PACOTE

import android.content.Intent
import android.os.Bundle
import android.view.View // Importe View
import android.widget.EditText // Importe EditText (para os campos de texto)
import android.widget.TextView // Importe TextView (para o textTitle)
import androidx.appcompat.app.AppCompatActivity
import br.edu.ufam.icomp.plaintextapp.R // Importe R para acessar IDs
import br.edu.ufam.icomp.plaintextapp.dao.PasswordDAO // Importe PasswordDAO
import br.edu.ufam.icomp.plaintextapp.model.Password // Importe Password


class EditActivity : AppCompatActivity() {
    private lateinit var passwordDAO: PasswordDAO
    private var passwordId: Int = -1 // Inicializado como -1 para indicar nova senha
    private lateinit var editName: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var editNotes: EditText
    private lateinit var textTitle: TextView // Para o título da AppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Inicializar as Views
        textTitle = findViewById(R.id.textTitle)
        editName = findViewById(R.id.addName)
        editLogin = findViewById(R.id.addLogin)
        editPassword = findViewById(R.id.addPassword)
        editNotes = findViewById(R.id.addNotes)
        passwordDAO = PasswordDAO(this)

        // Obter o ID da senha da Intent
        val intent = intent
        passwordId = intent.getIntExtra("password_id", -1) // Use "password_id" como chave

        // Se um ID de senha válido foi passado, carregar os dados
        if (passwordId != -1) {
            val password = passwordDAO.get(passwordId)
            textTitle.text = "Editar Senha" // Mude o título para "Editar Senha"
            editName.setText(password.name)
            editLogin.setText(password.login)
            editPassword.setText(password.password)
            editNotes.setText(password.notes)
        } else {
            textTitle.text = "Nova Senha" // Título para nova senha
        }
    }

    // Método chamado quando o botão "Salvar" é clicado (definido em onClick no XML)
    fun salvarClicado(view: View) {
        val password = Password(
            id = passwordId, // O ID será -1 para nova senha, ou o ID existente
            name = editName.text.toString(),
            login = editLogin.text.toString(),
            password = editPassword.text.toString(),
            notes = editNotes.text.toString()
        )

        val result: Boolean
        if (passwordId == -1) {
            // Adicionar nova senha
            result = passwordDAO.add(password)
        } else {
            // Atualizar senha existente
            result = passwordDAO.update(password)
        }

        if (result) {
            finish() // Fecha a Activity se a operação for bem-sucedida
        }
    }
}