package br.edu.ufam.icomp.plaintextapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ufam.icomp.plaintextapp.data.datasource.local.dao.MyPasswordDAO

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository
import br.edu.ufam.icomp.plaintextapp.data.repository.PasswordRepositoryImpl
import br.edu.ufam.icomp.plaintextapp.domain.usecase.AddPasswordUseCase // Importe AddPasswordUseCase
import br.edu.ufam.icomp.plaintextapp.domain.usecase.UpdatePasswordUseCase // Importe UpdatePasswordUseCase
import br.edu.ufam.icomp.plaintextapp.domain.usecase.GetPasswordByIdUseCase // Importe GetPasswordByIdUseCase
import br.edu.ufam.icomp.plaintextapp.model.Password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PasswordEditUiState(
    val passwordId: Int = -1,
    val name: String = "",
    val login: String = "",
    val password: String = "",
    val notes: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null,
    val title: String = "Nova Senha"
)

class PasswordEditViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PasswordEditUiState())
    val uiState: StateFlow<PasswordEditUiState> = _uiState

    // Instancia o Repositório e os Use Cases aqui
    // Em um projeto real, isso seria feito com Injeção de Dependência (Hilt)
    private val passwordRepository: PasswordRepository = PasswordRepositoryImpl(MyPasswordDAO(application))
    private val addPasswordUseCase = AddPasswordUseCase(passwordRepository)
    private val updatePasswordUseCase = UpdatePasswordUseCase(passwordRepository)
    private val getPasswordByIdUseCase = GetPasswordByIdUseCase(passwordRepository)

    fun initialize(passwordId: Int) {
        if (_uiState.value.passwordId == passwordId && passwordId != -1 && _uiState.value.name.isNotEmpty()) {
            return // Já inicializado com este ID e dados carregados
        }

        _uiState.value = _uiState.value.copy(passwordId = passwordId)

        if (passwordId != -1) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
                try {
                    val loadedPassword = getPasswordByIdUseCase(passwordId) // Chama o Use Case
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
            _uiState.value = _uiState.value.copy(title = "Nova Senha")
        }
    }

    fun onNameChanged(newName: String) { _uiState.value = _uiState.value.copy(name = newName, errorMessage = null) }
    fun onLoginChanged(newLogin: String) { _uiState.value = _uiState.value.copy(login = newLogin, errorMessage = null) }
    fun onPasswordChanged(newPassword: String) { _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null) }
    fun onNotesChanged(newNotes: String) { _uiState.value = _uiState.value.copy(notes = newNotes, errorMessage = null) }

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
                    addPasswordUseCase(currentPassword) // Chama o Use Case
                } else {
                    updatePasswordUseCase(currentPassword) // Chama o Use Case
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

    fun resetSaveStatus() {
        _uiState.value = _uiState.value.copy(saveSuccess = false, errorMessage = null)
    }
}