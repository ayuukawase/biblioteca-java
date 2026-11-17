# Empréstimo de livros

## Objetivo
Aluno ou servidor pega um livro disponível. O administrador também pode registrar o empréstimo para um usuário e acompanhar todos os empréstimos.

## Regras
- Prazo de devolução: 14 dias a partir da data do empréstimo.
- Usuário suspenso não pode pegar livros.
- Livro emprestado fica indisponível até ser devolvido.
- O empréstimo é imediato (não há aprovação) e é gravado no JSON na hora.
- Na tela do usuário, só aparecem livros disponíveis.
- Na tela do admin, ele escolhe o usuário e um livro disponível.
- O admin vê todos os empréstimos, do mais recente para o mais antigo, com: usuário, livro, emprestado em, devolver até, devolvido em e situação.
- Situação: **Em andamento**, **Atrasado** (não devolvido e prazo vencido) ou **Devolvido**.

## Critérios de aceite
- Dado um usuário ativo e um livro disponível, quando pede o empréstimo, então é criado com devolução em 14 dias, o livro fica Emprestado e o JSON é atualizado.
- Dado um usuário suspenso, quando pede um livro, então nenhum empréstimo é criado e aparece aviso.
- Dado um livro indisponível, então ele não aparece para o usuário nem na escolha do admin.
- Dado o admin sem escolher usuário ou livro, quando clica em Registrar empréstimo, então aparece aviso.
- Dado um empréstimo não devolvido com prazo vencido, então a situação mostra Atrasado.