# E-books

## Objetivo
O usuário lê e-books dentro do próprio programa.

## Regras
- Todo usuário (inclusive suspenso) vê a lista de e-books: ID, título e categorias.
- E-book não precisa de empréstimo.
- O campo `arquivo` do JSON é o caminho a partir da raiz do projeto (ex.: `books/clean_code.pdf`).
- PDFs abrem numa janela do programa, com Anterior, Próxima, Zoom + e Zoom −.
- Outros formatos abrem no programa padrão do computador.
- Se o arquivo não existir, aparece aviso com o caminho completo procurado.

## Critérios de aceite
- Dado um e-book selecionado cujo PDF existe, quando clica em Abrir para ler, então abre a janela do leitor na página 1 de N.
- Dado a primeira página, então Anterior fica desabilitado; na última, Próxima fica desabilitado.
- Dado o arquivo inexistente, então aparece "Arquivo não encontrado" com o caminho.
- Dado nenhum e-book selecionado, então aparece aviso para selecionar.