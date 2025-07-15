package br.edu.ufam.icomp.plaintextapp.domain.repository // <-- SEU PACOTE

import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password
import kotlinx.coroutines.flow.Flow // Importe Flow

// Interface que define as operações de dados para senhas
interface PasswordRepository {
    fun getPasswords(): Flow<List<Password>> // Retorna um Flow para reatividade
    suspend fun addPassword(password: Password): Boolean
    suspend fun updatePassword(password: Password): Boolean
    suspend fun getPasswordById(id: Int): Password?
    suspend fun deletePassword(id: Int): Boolean // Adicionando delete para completar o CRUD
}