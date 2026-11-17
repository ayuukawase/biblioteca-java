# Devolução de livros

## Objetivo
O usuário devolve os livros que pegou emprestados.

## Regras
- A aba Meus empréstimos mostra só os empréstimos do usuário logado.
- Devolver registra a data de hoje em "devolvido em".
- O livro volta a ficar disponível.
- Um empréstimo já devolvido não pode ser devolvido de novo.
- Usuário suspenso pode devolver livros.
- A devolução é gravada no JSON na hora.

## Critérios de aceite
- Dado um empréstimo em andamento selecionado, quando clica em Devolver, então "Devolvido em" mostra a data de hoje, a situação vira Devolvido e o livro fica Disponível.
- Dado um empréstimo já devolvido selecionado, quando clica em Devolver, então aparece aviso de que já foi devolvido.
- Dado nenhum empréstimo selecionado, então aparece aviso para selecionar.
- Dado que o programa foi fechado e aberto de novo, então a data de devolução continua aparecendo (veio do JSON).