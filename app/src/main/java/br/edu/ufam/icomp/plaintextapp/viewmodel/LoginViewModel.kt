package br.edu.ufam.icomp.plaintextapp.viewmodel // <-- SEU PACOTE

import android.app.Application // Importe Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel // Importe AndroidViewModel
import androidx.lifecycle.viewModelScope // Importe viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow // Importe MutableStateFlow
import kotlinx.coroutines.flow.StateFlow // Importe StateFlow
import kotlinx.coroutines.launch // Importe launch

// Representa o estado da UI da tela de login
data class LoginUiState(
    val loginText: String = "",
    val passwordText: String = "",
    val saveLoginInfo: Boolean = false,
    val loginSuccessful: Boolean = false,
    val showLoginError: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Use MutableStateFlow para o estado da UI, que é observável
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application)

    init {
        // Carrega as preferências ao inicializar a ViewModel
        loadLoginPreferences()
    }

    private fun loadLoginPreferences() {
        viewModelScope.launch {
            val prefLogin = sharedPreferences.getString("login", "") ?: ""
            val prefPassword = sharedPreferences.getString("password", "") ?: ""
            val rememberLoginPref = sharedPreferences.getBoolean("remember_login", false)

            if (rememberLoginPref) {
                _uiState.value = _uiState.value.copy(
                    loginText = prefLogin,
                    passwordText = prefPassword,
                    saveLoginInfo = true
                )
            }
        }
    }

    // Eventos de UI (funções que a View chamará)
    fun onLoginTextChanged(newText: String) {
        _uiState.value = _uiState.value.copy(loginText = newText, showLoginError = false)
    }

    fun onPasswordTextChanged(newText: String) {
        _uiState.value = _uiState.value.copy(passwordText = newText, showLoginError = false)
    }

    fun onSaveLoginInfoChanged(isChecked: Boolean) {
        _uiState.value = _uiState.value.copy(saveLoginInfo = isChecked)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val savedLogin = sharedPreferences.getString("login", "") ?: ""
            val savedPassword = sharedPreferences.getString("password", "") ?: ""

            if (_uiState.value.loginText == savedLogin && _uiState.value.passwordText == savedPassword) {
                // Login bem-sucedido
                if (_uiState.value.saveLoginInfo) {
                    sharedPreferences.edit()
                        .putString("login", _uiState.value.loginText)
                        .putString("password", _uiState.value.passwordText)
                        .putBoolean("remember_login", true)
                        .apply()
                } else {
                    sharedPreferences.edit()
                        .remove("login")
                        .remove("password")
                        .putBoolean("remember_login", false)
                        .apply()
                }
                _uiState.value = _uiState.value.copy(loginSuccessful = true, showLoginError = false)
            } else {
                // Login falhou
                _uiState.value = _uiState.value.copy(loginSuccessful = false, showLoginError = true)
            }
        }
    }

    // Resetar o estado de sucesso/erro após a navegação/exibição da mensagem
    fun resetLoginStatus() {
        _uiState.value = _uiState.value.copy(loginSuccessful = false, showLoginError = false)
    }
}