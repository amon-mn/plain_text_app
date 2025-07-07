package br.edu.ufam.icomp.plaintextapp.viewmodel // <-- SEU PACOTE

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ufam.icomp.plaintextapp.dao.PasswordDAO
import br.edu.ufam.icomp.plaintextapp.model.Password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Representa o estado da UI da tela de listagem de senhas
data class PasswordListUiState(
    val passwords: List<Password> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToEdit: Int? = null // ID da senha para navegar para EditActivity, ou null para nova
)

class PasswordListViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PasswordListUiState())
    val uiState: StateFlow<PasswordListUiState> = _uiState

    private val passwordDAO: PasswordDAO = PasswordDAO(application)

    init {
        loadPasswords() // Carrega as senhas ao inicializar a ViewModel
    }

    fun loadPasswords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val loadedPasswords = passwordDAO.getList()
                _uiState.value = _uiState.value.copy(
                    passwords = loadedPasswords,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Erro ao carregar senhas: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    // Evento de UI: quando um item da lista é clicado
    fun onPasswordItemClick(passwordId: Int) {
        _uiState.value = _uiState.value.copy(navigateToEdit = passwordId)
    }

    // Evento de UI: quando o FAB de adicionar é clicado
    fun onAddPasswordClick() {
        _uiState.value = _uiState.value.copy(navigateToEdit = -1) // -1 para indicar nova senha
    }

    // Resetar o estado de navegação após a Activity ter reagido
    fun resetNavigation() {
        _uiState.value = _uiState.value.copy(navigateToEdit = null)
    }
}