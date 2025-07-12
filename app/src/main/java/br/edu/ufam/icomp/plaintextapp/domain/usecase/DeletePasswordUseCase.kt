package br.edu.ufam.icomp.plaintextapp.domain.usecase

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository

class DeletePasswordUseCase(private val repository: PasswordRepository) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.deletePassword(id)
    }
}