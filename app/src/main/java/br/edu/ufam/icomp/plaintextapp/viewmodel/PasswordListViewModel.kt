package br.edu.ufam.icomp.plaintextapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ufam.icomp.plaintextapp.data.datasource.local.dao.MyPasswordDAO
import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository // Importe PasswordRepository
import br.edu.ufam.icomp.plaintextapp.data.repository.PasswordRepositoryImpl // Importe PasswordRepositoryImpl
import br.edu.ufam.icomp.plaintextapp.domain.usecase.GetPasswordsUseCase // Importe GetPasswordsUseCase
import br.edu.ufam.icomp.plaintextapp.domain.usecase.DeletePasswordUseCase // Importe DeletePasswordUseCase
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch // Importe catch
import kotlinx.coroutines.launch

data class PasswordListUiState(
    val passwords: List<Password> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToEdit: Int? = null,
    val deleteSuccess: Boolean = false, // Novo estado para sucesso na exclusão
    val showDeleteConfirmation: Int? = null // ID da senha para confirmar exclusão
)

class PasswordListViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PasswordListUiState())
    val uiState: StateFlow<PasswordListUiState> = _uiState

    // Instancia o Repositório e os Use Cases aqui
    // Em um projeto real, isso seria feito com Injeção de Dependência (Hilt)
    private val passwordRepository: PasswordRepository = PasswordRepositoryImpl(MyPasswordDAO(application))
    private val getPasswordsUseCase = GetPasswordsUseCase(passwordRepository)
    private val deletePasswordUseCase = DeletePasswordUseCase(passwordRepository)

    init {
        collectPasswords() // Inicia a coleta de senhas do Flow
    }

    // Novo método para coletar o Flow de senhas
    private fun collectPasswords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            getPasswordsUseCase() // Chama o Use Case que retorna o Flow
                .catch { e -> // Captura erros do Flow
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Erro ao carregar senhas: ${e.message}",
                        isLoading = false
                    )
                }
                .collect { passwords -> // Coleta os itens do Flow
                    _uiState.value = _uiState.value.copy(
                        passwords = passwords,
                        isLoading = false
                    )
                }
        }
    }

    // Chamado quando a Activity volta para o foreground
    fun refreshPasswords() {
        // Como getPasswordsUseCase retorna um Flow, ele já é reativo.
        // Para forçar uma "atualização" imediata, podemos re-coletar ou ter um Use Case específico.
        // Por enquanto, o collectPasswords() já está ativo.
        // Se o DAO não fosse reativo, teríamos que chamar getPasswordsUseCase() novamente aqui.
        // Para o nosso mock de Flow, não é estritamente necessário, mas é boa prática.
        collectPasswords()
    }

    fun onPasswordItemClick(passwordId: Int) {
        _uiState.value = _uiState.value.copy(navigateToEdit = passwordId)
    }

    fun onAddPasswordClick() {
        _uiState.value = _uiState.value.copy(navigateToEdit = -1)
    }

    fun resetNavigation() {
        _uiState.value = _uiState.value.copy(navigateToEdit = null)
    }

    // Novo: Evento para confirmar exclusão
    fun onDeletePasswordClick(passwordId: Int) {
        _uiState.value = _uiState.value.copy(showDeleteConfirmation = passwordId)
    }

    // Novo: Confirmar a exclusão e executar o Use Case
    fun confirmDeletePassword() {
        _uiState.value.showDeleteConfirmation?.let { idToDelete ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                try {
                    val success = deletePasswordUseCase(idToDelete)
                    if (success) {
                        _uiState.value = _uiState.value.copy(deleteSuccess = true, isLoading = false)
                        // A lista será atualizada automaticamente via Flow
                    } else {
                        _uiState.value = _uiState.value.copy(errorMessage = "Falha ao excluir senha.", isLoading = false)
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(errorMessage = "Erro ao excluir: ${e.message}", isLoading = false)
                } finally {
                    _uiState.value = _uiState.value.copy(showDeleteConfirmation = null) // Limpa o estado de confirmação
                }
            }
        }
    }

    // Novo: Cancelar a confirmação de exclusão
    fun cancelDeletePassword() {
        _uiState.value = _uiState.value.copy(showDeleteConfirmation = null)
    }

    // Novo: Resetar o status de exclusão
    fun resetDeleteStatus() {
        _uiState.value = _uiState.value.copy(deleteSuccess = false, errorMessage = null)
    }
}