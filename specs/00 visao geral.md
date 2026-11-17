# Visão geral

## Objetivo
Sistema de biblioteca em Java/JavaFX para o IF. Administrador gerencia livros e empréstimos; alunos e servidores pegam e devolvem livros e leem e-books.

## Quem usa
- **Administrador**: gerencia livros e empréstimos.
- **Aluno** e **Servidor** (professor ou técnico administrativo): pegam livros, devolvem e leem e-books.

## Dados
- Tudo fica em `Biblioteca.json`, na raiz do projeto.
- Toda alteração (livro, empréstimo, devolução) é gravada no JSON na hora.
- Datas no JSON no formato `2026-11-01`; na tela, `01/11/2026`.

## Telas
Login (sempre a primeira) -> Tela do administrador ou Tela do usuário.

## Fora do escopo (por enquanto)
- Cadastrar e excluir e-books pela tela.
- Suspender, reativar e cadastrar usuários pela tela.
- Aprovação de pedidos de empréstimo pelo admin (o empréstimo é imediato).
- Multa por atraso.