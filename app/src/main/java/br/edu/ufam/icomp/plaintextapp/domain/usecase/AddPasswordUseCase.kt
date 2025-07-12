package br.edu.ufam.icomp.plaintextapp.domain.usecase

import br.edu.ufam.icomp.plaintextapp.domain.repository.PasswordRepository
import br.edu.ufam.icomp.plaintextapp.model.Password

class AddPasswordUseCase(private val repository: PasswordRepository) {
    suspend operator fun invoke(password: Password): Boolean {
        // Aqui poderia haver lógica de validação de negócio antes de adicionar
        return repository.addPassword(password)
    }
}