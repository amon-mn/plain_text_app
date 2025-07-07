package br.edu.ufam.icomp.plaintextapp.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels // Importe viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.edu.ufam.icomp.plaintextapp.R
import br.edu.ufam.icomp.plaintextapp.viewmodel.PasswordEditViewModel // Importe PasswordEditViewModel
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {

    private var passwordId: Int = -1 // O ID da senha (passado pela Intent)
    private lateinit var editName: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var editNotes: EditText
    private lateinit var textTitle: TextView

    // Obtém a ViewModel usando o delegate viewModels()
    private val viewModel: PasswordEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // Inicializar as Views
        textTitle = findViewById(R.id.textTitle)
        editName = findViewById(R.id.addName)
        editLogin = findViewById(R.id.addLogin)
        editPassword = findViewById(R.id.addPassword)
        editNotes = findViewById(R.id.addNotes)

        // Obter o ID da senha da Intent e inicializar a ViewModel
        passwordId = intent.getIntExtra("password_id", -1)
        viewModel.initialize(passwordId) // <-- Inicializa a ViewModel com o ID

        // --- Observar o estado da ViewModel ---
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // Atualiza os campos de texto com os dados do estado (apenas se não estiverem focados para evitar loops de digitação)
                    // Esta lógica é para garantir que a UI reflita o estado da ViewModel
                    if (editName.text.toString() != uiState.name) {
                        editName.setText(uiState.name)
                    }
                    if (editLogin.text.toString() != uiState.login) {
                        editLogin.setText(uiState.login)
                    }
                    if (editPassword.text.toString() != uiState.password) {
                        editPassword.setText(uiState.password)
                    }
                    if (editNotes.text.toString() != uiState.notes) {
                        editNotes.setText(uiState.notes)
                    }

                    // Atualiza o título da tela
                    textTitle.text = uiState.title

                    // Reage ao sucesso de salvar
                    if (uiState.saveSuccess) {
                        Toast.makeText(this@EditActivity, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show()
                        viewModel.resetSaveStatus() // Reseta o status na ViewModel
                        finish() // Fecha a Activity
                    }

                    // Reage a mensagens de erro
                    uiState.errorMessage?.let { message ->
                        Toast.makeText(this@EditActivity, message, Toast.LENGTH_LONG).show()
                        viewModel.resetSaveStatus() // Reseta o status do erro
                    }

                    // Opcional: Mostrar um ProgressBar se isSaving for true
                    // if (uiState.isSaving) { /* show progress */ } else { /* hide progress */ }
                }
            }
        }
        // --- Fim da Observação ---

        // --- Adicionar Listeners para os campos de texto para enviar eventos para a ViewModel ---
        editName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { // Atualiza a ViewModel apenas quando o foco sai do campo
                viewModel.onNameChanged(editName.text.toString())
            }
        }
        editLogin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onLoginChanged(editLogin.text.toString())
            }
        }
        editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onPasswordChanged(editPassword.text.toString())
            }
        }
        editNotes.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.onNotesChanged(editNotes.text.toString())
            }
        }
        // Para atualizações em tempo real (enquanto digita), use TextWatcher
        // Mas para este exemplo, onFocusChangeListener é mais simples e suficiente.
    }

    // Metodo chamado quando o botão "Salvar" é clicado (definido em onClick no XML)
    fun salvarClicado(view: View) {
        // Garante que a ViewModel tenha os valores mais recentes antes de salvar
        viewModel.onNameChanged(editName.text.toString())
        viewModel.onLoginChanged(editLogin.text.toString())
        viewModel.onPasswordChanged(editPassword.text.toString())
        viewModel.onNotesChanged(editNotes.text.toString())

        viewModel.onSaveClick() // Chama a ViewModel para salvar
    }
}