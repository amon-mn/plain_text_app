package br.edu.ufam.icomp.plaintextapp.domain.usecase

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository
import br.edu.ufam.icomp.plaintextapp.domain.entities.model.Password

class GetPasswordByIdUseCase(private val repository: PasswordRepository) {
    suspend operator fun invoke(id: Int): Password? {
        return repository.getPasswordById(id)
    }
}