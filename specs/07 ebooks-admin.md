# E-books (administrador)

## Objetivo
O admin adiciona, atualiza e exclui e-books.

## Regras
- A aba E-books mostra todos os e-books com ID, título, categorias e arquivo.
- Para adicionar, o título e o arquivo são obrigatórios; as categorias são opcionais e separadas por vírgula.
- O arquivo é o caminho a partir da raiz do projeto (ex.: `books/clean_code.pdf`). O programa não confere se o arquivo existe ao cadastrar; o aviso aparece quando o usuário tenta abrir.
- O e-book novo recebe o maior id existente + 1.
- Ao clicar num e-book da tabela, os campos são preenchidos com os dados dele.
- Salvar alterações troca título, categorias e arquivo do e-book selecionado; o id não muda.
- Excluir pede confirmação (Sim ou Não).
- Toda alteração é gravada no JSON na hora.

## Critérios de aceite
- Dado título, categorias e arquivo preenchidos, quando clica em Adicionar e-book, então ele aparece na tabela e o JSON é atualizado.
- Dado título ou arquivo vazio, quando clica em Adicionar e-book ou Salvar alterações, então aparece "Digite o título e o caminho do arquivo." e nada muda.
- Dado um e-book selecionado e os campos editados, quando clica em Salvar alterações, então a tabela e o JSON mostram os dados novos com o mesmo id.
- Dado nenhum e-book selecionado, quando clica em Salvar alterações ou Excluir, então aparece aviso para selecionar.
- Dado um e-book selecionado, quando clica em Excluir e confirma com Sim, então ele some da tabela, do JSON e da lista do usuário.
- Dado um e-book selecionado, quando clica em Excluir e responde Não, então nada muda.