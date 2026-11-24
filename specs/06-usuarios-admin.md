# Usuários (administrador)

## Objetivo
O admin suspende e reativa usuários.

## Regras
- A tela mostra todos os alunos e servidores com a situação (Ativo ou Suspenso).
- Usuário suspenso não pode pegar livros, mas pode devolver e ler e-books.
- A mudança é gravada no JSON na hora.

## Critérios de aceite
- Dado um usuário ativo selecionado, quando o admin clica em Suspender, então ele aparece como Suspenso e o JSON é atualizado.
- Dado um usuário suspenso selecionado, quando clica em Reativar, então ele volta a Ativo.
- Dado nenhum usuário selecionado, então aparece aviso.