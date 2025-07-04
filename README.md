PlainText App

🚀 Visão Geral
Este é PlainText App, um aplicativo móvel desenvolvido em Android Studio que simula um gerenciador de senhas simples e foca na interface de usuário, navegação entre telas e persistência de dados de login através de preferências. Ele foi criado para demonstrar conceitos fundamentais de desenvolvimento Android moderno com Jetpack Compose, incluindo navegação e gerenciamento de configurações de usuário.

✨ Funcionalidades

Interface de usuário intuitiva e responsiva construída com Jetpack Compose.

Navegação entre a tela de Login e uma tela de Boas-vindas (HelloScreen).

Passagem de dados (nome de usuário) entre telas durante a navegação.

Gerenciamento de credenciais de login (usuário e senha) via Preferências do Usuário (SharedPreferences).

Tela de Configurações dedicada para definir e gerenciar as credenciais de login.

Opção de preenchimento automático dos campos de login na inicialização, baseada nas preferências salvas.

Validação de login e senha utilizando as credenciais configuradas nas preferências.

🛠️ Tecnologias Utilizadas

Linguagem de Programação: Kotlin

IDE: Android Studio

Frameworks/Bibliotecas Principais:

Android Jetpack Compose (para construção da UI declarativa)

Android Jetpack Compose Navigation (para gerenciamento do fluxo entre telas)

AndroidX Preference (para a tela de configurações e SharedPreferences)

Material Design 3 (para componentes de UI modernos)

Ferramentas de Build: Gradle

⚙️ Instalação e Execução
Para configurar e executar o projeto localmente, siga estes passos:

Clone o repositório:

git clone https://github.com/SeuUsuario/NomeDoSeuRepositorio.git


(Substitua SeuUsuario pelo seu nome de usuário do GitHub e NomeDoSeuRepositorio pelo nome que você deu ao seu repositório.)

Abra no Android Studio:

No Android Studio, clique em File > Open.

Navegue até a pasta NomeDoSeuRepositorio que você acabou de clonar e selecione-a.

Aguarde o Android Studio sincronizar o projeto com o Gradle.

Execute o aplicativo:

Selecione um emulador Android ou conecte um dispositivo físico.

Clique no botão Run (o ícone de 'Play' verde) na barra de ferramentas do Android Studio.

📚 Como Usar
Após a instalação, o aplicativo abrirá na tela de login.

Configurar Credenciais: Para definir o login e a senha, clique no ícone de três pontos verticais (menu) na AppBar superior e selecione "Configurações".

Na tela de Configurações, utilize os campos "Setar Login" e "Setar Senha" para definir suas credenciais.

Volte para a tela de login.

Realizar Login: Digite o login e a senha que você configurou. Opcionalmente, marque a caixa "Salvar as informações de login" para que o aplicativo preencha esses campos automaticamente na próxima vez que for aberto.

Clique no botão "ENVIAR". Se as credenciais estiverem corretas, você será direcionado para a tela de boas-vindas com o nome de usuário.

🤝 Contribuição
Contribuições são bem-vindas! Se você quiser contribuir com este projeto, por favor, siga estas diretrizes:

Faça um fork do repositório.

Crie uma nova branch para sua funcionalidade ou correção de bug (git checkout -b feature/sua-feature ou bugfix/sua-correcao).

Faça suas alterações e commit-as com mensagens claras.

Envie suas alterações para o seu fork (git push origin feature/sua-feature).

Abra um Pull Request detalhando suas mudanças.

🐛 Issues e Suporte
Se você encontrar algum problema ou tiver sugestões, por favor, abra uma Issue neste repositório.

📝 Licença
Este projeto está licenciado sob a licença MIT License - veja o arquivo LICENSE.md para mais detalhes.

🧑‍💻 Autor
Amon Menezes Negreiros - https://github.com/amon-mn
