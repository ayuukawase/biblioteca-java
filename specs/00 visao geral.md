# Visão geral

## Objetivo
Sistema de biblioteca em Java/JavaFX para o IF. Administrador gerencia livros, e-books, empréstimos e usuários; alunos e servidores pegam e devolvem livros e leem e-books.

## Quem usa
- **Administrador**: gerencia livros, e-books, empréstimos e a situação dos usuários (suspender e reativar).
- **Aluno** e **Servidor** (professor ou técnico administrativo): pegam livros, devolvem e leem e-books.

## Dados
- Tudo fica em `Biblioteca.json`, na raiz do projeto.
- Toda alteração (livro, empréstimo, devolução, situação do usuário) é gravada no JSON na hora.
- Datas no JSON no formato `2026-11-01`; na tela, `01/11/2026`.

## Telas
Login (sempre a primeira) -> Tela do administrador (abas Livros, Fazer empréstimo, Empréstimos, Usuários e E-books) ou Tela do usuário (abas Pedir empréstimo, Meus empréstimos e E-books).