package br.edu.ufam.icomp.plaintextapp.domain.usecase // <-- SEU PACOTE

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password
import kotlinx.coroutines.flow.Flow

class GetPasswordsUseCase(private val repository: PasswordRepository) {
    operator fun invoke(): Flow<List<Password>> {
        return repository.getPasswords()
    }
}