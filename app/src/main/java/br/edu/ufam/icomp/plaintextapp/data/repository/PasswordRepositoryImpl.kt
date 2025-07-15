package br.edu.ufam.icomp.plaintextapp.data.repository // <-- SEU PACOTE


import br.edu.ufam.icomp.plaintextapp.data.datasource.local.dao.MyPasswordDAO
import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository // Importe a interface
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password
import kotlinx.coroutines.Dispatchers // Importe Dispatchers
import kotlinx.coroutines.flow.Flow // Importe Flow
import kotlinx.coroutines.flow.flow // Importe flow builder
import kotlinx.coroutines.flow.flowOn // Importe flowOn

// Implementação do repositório que usa o DAO
class PasswordRepositoryImpl(private val passwordDAO: MyPasswordDAO) : PasswordRepository {

    // Retorna um Flow para observar mudanças na lista de senhas
    override fun getPasswords(): Flow<List<Password>> = flow {
        // Emitir a lista atual de senhas do DAO
        emit(passwordDAO.getList())
        // Em um app real com DB reativo (Room), o DAO já retornaria um Flow.
        // Como SQLiteOpenHelper não é reativo por padrão, estamos simulando um Flow aqui.
        // Para um DB reativo, a linha acima seria substituída por:
        // passwordDAO.getAllPasswordsFlow()
    }.flowOn(Dispatchers.IO) // Garante que a operação de DB ocorra em uma thread de I/O

    override suspend fun addPassword(password: Password): Boolean {
        return passwordDAO.add(password)
    }

    override suspend fun updatePassword(password: Password): Boolean {
        return passwordDAO.update(password)
    }

    override suspend fun getPasswordById(id: Int): Password? {
        return passwordDAO.get(id)
    }

    override suspend fun deletePassword(id: Int): Boolean {
        return passwordDAO.delete(id) // Chamará o novo metodo delete no DAO
    }
}