# E-books

## Objetivo
O usuário abre e-books pelo programa.

## Regras
- Todo usuário (inclusive suspenso) vê a lista de e-books: ID, título e categorias.
- E-book não precisa de empréstimo.
- O campo `arquivo` do JSON é o caminho a partir da raiz do projeto (ex.: `books/clean_code.pdf`).
- O arquivo abre no programa padrão do computador (leitor de PDF etc.).
- Se o arquivo não existir, aparece aviso com o caminho completo procurado.

## Critérios de aceite
- Dado um e-book selecionado cujo arquivo existe, quando clica em Abrir para ler, então o arquivo abre no programa padrão do computador.
- Dado o arquivo inexistente, então aparece "Arquivo não encontrado" com o caminho.
- Dado nenhum e-book selecionado, então aparece aviso para selecionar.