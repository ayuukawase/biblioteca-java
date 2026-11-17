# Login

## Objetivo
Identificar quem está usando o sistema antes de mostrar qualquer outra tela.

## Regras
- O login é a primeira tela ao abrir o programa.
- O usuário escolhe o perfil: **Administrador** ou **Aluno ou servidor**.
- O admin e um aluno podem ter o mesmo id, por isso o perfil é escolhido antes.
- Entrada: id (somente número) e senha.
- Admin entra na tela do administrador; aluno ou servidor entra na tela do usuário.
- Usuário suspenso consegue entrar, mas a tela avisa que a conta está suspensa.
- Sair volta para o login.

## Critérios de aceite
- Dado perfil Administrador, id 1 e senha correta, quando clica em Entrar, então abre a tela do administrador.
- Dado perfil Aluno ou servidor, id 1 e senha correta, quando clica em Entrar, então abre a tela do usuário com o nome da pessoa.
- Dado id ou senha errados, então aparece "ID ou senha incorretos." e a tela não muda.
- Dado um id com letras, então aparece "O ID deve ser um número.".
- Dado o perfil Aluno ou servidor com o id e a senha de um admin, então o login falha.