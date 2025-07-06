package br.edu.ufam.icomp.plaintextapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.dao.PasswordDAO
import br.edu.ufam.icomp.plaintextapp.model.Password


class EditActivity : AppCompatActivity() {
    private lateinit var passwordDAO: PasswordDAO
    private var passwordId: Int = -1
    private lateinit var editName: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var editNotes: EditText
    private lateinit var textTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        textTitle = findViewById(R.id.textTitle)
        editName = findViewById(R.id.addName)
        editLogin = findViewById(R.id.addLogin)
        editPassword = findViewById(R.id.addPassword)
        editNotes = findViewById(R.id.addNotes)
        passwordDAO = PasswordDAO(this)

        val intent = intent
        passwordId = intent.getIntExtra("password_id", -1)

        if (passwordId != -1) {
            val password: Password? = passwordDAO.get(passwordId) // O tipo agora é Password?

            // CORREÇÃO AQUI: Verifique se 'password' não é nulo antes de usar
            if (password != null) {
                textTitle.text = "Editar Senha"
                editName.setText(password.name)
                editLogin.setText(password.login)
                editPassword.setText(password.password)
                editNotes.setText(password.notes)
            } else {
                // Caso a senha não seja encontrada (o que não deveria acontecer com um ID válido)
                // ou se passwordDAO.get() retornar null por algum motivo.
                // Você pode adicionar um Toast ou log aqui.
                Toast.makeText(this, "Senha não encontrada!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a Activity se a senha não for encontrada
            }
        } else {
            textTitle.text = "Nova Senha"
        }
    }

    fun salvarClicado(view: View) {
        val password = Password(
            id = passwordId,
            name = editName.text.toString(),
            login = editLogin.text.toString(),
            password = editPassword.text.toString(),
            notes = editNotes.text.toString()
        )

        val result: Boolean
        if (passwordId == -1) {
            result = passwordDAO.add(password)
        } else {
            result = passwordDAO.update(password)
        }

        if (result) {
            finish()
        }
    }
}