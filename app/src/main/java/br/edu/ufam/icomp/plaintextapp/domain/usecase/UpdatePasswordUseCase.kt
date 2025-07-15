package br.edu.ufam.icomp.plaintextapp.domain.usecase

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password

class UpdatePasswordUseCase(private val repository: PasswordRepository) {
    suspend operator fun invoke(password: Password): Boolean {
        // Lógica de validação de negócio antes de atualizar
        return repository.updatePassword(password)
    }
}