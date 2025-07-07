package br.edu.ufam.icomp.plaintextapp.viewmodel // <-- SEU PACOTE

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ufam.icomp.plaintextapp.dao.PasswordDAO
import br.edu.ufam.icomp.plaintextapp.model.Password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Representa o estado da UI da tela de edição de senhas
data class PasswordEditUiState(
    val passwordId: Int = -1, // -1 para nova senha, ID real para edição
    val name: String = "",
    val login: String = "",
    val password: String = "",
    val notes: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null,
    val title: String = "Nova Senha" // Título da tela
)

class PasswordEditViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PasswordEditUiState())
    val uiState: StateFlow<PasswordEditUiState> = _uiState

    private val passwordDAO: PasswordDAO = PasswordDAO(application)

    // Função para inicializar a ViewModel com um ID de senha (se houver)
    fun initialize(passwordId: Int) {
        // Garante que a inicialização ocorra apenas uma vez ou quando o ID realmente muda
        if (_uiState.value.passwordId == passwordId && passwordId != -1) {
            return // Já inicializado com este ID
        }

        _uiState.value = _uiState.value.copy(passwordId = passwordId)

        if (passwordId != -1) {
            // Carregar senha para edição
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null) // Usando isSaving como indicador de carregamento
                try {
                    val loadedPassword = passwordDAO.get(passwordId)
                    if (loadedPassword != null) {
                        _uiState.value = _uiState.value.copy(
                            name = loadedPassword.name,
                            login = loadedPassword.login,
                            password = loadedPassword.password,
                            notes = loadedPassword.notes,
                            title = "Editar Senha",
                            isSaving = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Senha não encontrada!",
                            isSaving = false
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Erro ao carregar senha: ${e.message}",
                        isSaving = false
                    )
                }
            }
        } else {
            // Nova senha, apenas define o título
            _uiState.value = _uiState.value.copy(title = "Nova Senha")
        }
    }

    // Eventos de UI para atualizar os campos
    fun onNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName, errorMessage = null)
    }

    fun onLoginChanged(newLogin: String) {
        _uiState.value = _uiState.value.copy(login = newLogin, errorMessage = null)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    fun onNotesChanged(newNotes: String) {
        _uiState.value = _uiState.value.copy(notes = newNotes, errorMessage = null)
    }

    // Evento de UI: Salvar senha
    fun onSaveClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
            try {
                val currentPassword = Password(
                    id = _uiState.value.passwordId,
                    name = _uiState.value.name,
                    login = _uiState.value.login,
                    password = _uiState.value.password,
                    notes = _uiState.value.notes
                )

                val result = if (currentPassword.id == -1) {
                    passwordDAO.add(currentPassword)
                } else {
                    passwordDAO.update(currentPassword)
                }

                if (result) {
                    _uiState.value = _uiState.value.copy(saveSuccess = true, isSaving = false)
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Falha ao salvar/atualizar senha.",
                        isSaving = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao salvar/atualizar: ${e.message}",
                    isSaving = false
                )
            }
        }
    }

    // Resetar o estado de sucesso/erro após a Activity ter reagido
    fun resetSaveStatus() {
        _uiState.value = _uiState.value.copy(saveSuccess = false, errorMessage = null)
    }
}