package br.edu.ufam.icomp.plaintextapp.model // <-- SEU PACOTE

data class Password(
    var id: Int, // id: identificador (inteiro) da senha
    var name: String, // name: um nome para a senha (e.g., nome do site)
    var login: String, // login: login de acesso da senha
    var password: String, // password: a senha a ser cadastrada
    var notes: String // notes: observações gerais
)