package br.edu.ufam.icomp.plaintextapp.presentation.ui.navigation

// Objeto para centralizar as rotas da aplicação
object AppRoutes {
    const val LOGIN_SCREEN = "login_screen"
    // A rota da tela Hello terá um argumento de nome, indicado por {name}
    const val HELLO_SCREEN = "hello_screen/{name}"

    // Função auxiliar para construir a rota da tela Hello com o nome
    fun createHelloRoute(name: String): String {
        return "hello_screen/$name"
    }
}