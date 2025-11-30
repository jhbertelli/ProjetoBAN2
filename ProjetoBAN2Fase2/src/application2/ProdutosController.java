package application2;

import domain2.Categoria;
import domain2.Fornecedor;
import domain2.Produto;
import infrastructure2.CategoriasRepository;
import infrastructure2.FornecedoresRepository;
import infrastructure2.Input;
import infrastructure2.ProdutosRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProdutosController {
    private final ProdutosRepository produtosRepository;
    private final CategoriasRepository categoriasRepository;
    private final FornecedoresRepository fornecedoresRepository;

    public ProdutosController(ProdutosRepository produtosRepository, CategoriasRepository categoriasRepository, FornecedoresRepository fornecedoresRepository) {
        this.produtosRepository = produtosRepository;
        this.categoriasRepository = categoriasRepository;
        this.fornecedoresRepository = fornecedoresRepository;
    }

    public void getAllProdutos() {
        var produtos = produtosRepository.getAllProdutos();

        System.out.println("---- Listando produtos ----");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        for (var produto : produtos) {
            System.out.println(produto.toString());
        }
    }

    public void createProduto() {
        System.out.println("---- Adicionando produto ----");

        var categorias = categoriasRepository.getAllCategorias();
        if (categorias.isEmpty()) {
            System.out.println("Erro: nenhuma categoria encontrada! Para criar um produto, crie uma categoria primeiro.");
            return;
        }


        var fornecedores = fornecedoresRepository.getAllFornecedores();
        if (fornecedores.isEmpty()) {
            System.out.println("Erro: nenhum fornecedor encontrado! Para criar um produto, crie um fornecedor primeiro.");
            return;
        }

        String nome = Input.getString("Insira o nome do produto:");
        double preco = Input.getDouble("Insira o preço do produto:");
        int quantidade = Input.getInt("Insira a quantidade em estoque:");
        int tempoGarantia = Input.getInt("Insira o tempo de garantia (em meses):");

        Date dataNFormatada = Input.getDate("Insira a data de recebimento (formato YYYY-MM-DD):");
        SimpleDateFormat formatar = new SimpleDateFormat("yyyy-mm-dd");
        String dataFormatada = formatar.format(dataNFormatada);

        int novoId = produtosRepository.getHighestId() + 1;

        var produto = new Produto(novoId, nome, preco, tempoGarantia, dataFormatada, quantidade);

        System.out.println("--- Categorias Disponíveis ---");
        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }

        while (true) {
            int idCategoria = Input.getInt("Insira o ID da categoria do produto:");

            var categoria = categorias.stream()
                    .filter(x -> x.getId()==idCategoria)
                    .findFirst()
                    .orElse(null);

            if (categoria == null) {
                System.out.println("Categoria " + idCategoria + " não encontrada, tente outra.");
                continue;
            }
            produto.setIdCategoria(idCategoria);
            produto.setNomeCategoria(categoria.getNome());

            break;
        }

        System.out.println("--- Fornecedores Disponíveis ---");
        for (var fornecedor : fornecedores) {
            System.out.println(fornecedor.toString());
        }

        while (true) {
            int idFornecedor = Input.getInt("Insira o ID do fornecedor do produto:");

            var fornecedor = fornecedores.stream()
                    .filter(x -> x.getId()==idFornecedor)
                    .findFirst()
                    .orElse(null);

            if (fornecedor == null) {
                System.out.println("Fornecedor " + idFornecedor + " não encontrado, tente outro.");
                continue;
            }

            produto.setIdFornecedor(idFornecedor);
            produto.setNomeFornecedor(fornecedor.getNome());

            break;
        }

        produtosRepository.createProduto(produto);
    }

    public void updateProduto() {
        System.out.println("---- Atualizando produtos ----");

        var produtos = produtosRepository.getAllProdutos();
        var categorias = categoriasRepository.getAllCategorias();
        var fornecedores = fornecedoresRepository.getAllFornecedores();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para atualizar um produto, crie um primeiro.");
            return;
        }

        int id = getIdProdutoDaLista(produtos, "Insira o ID do produto a atualizar:");

        Produto original = produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if(original == null ) return;

        System.out.println("Pressione ENTER para manter o valor atual mostrado entre (parênteses).");

        String inputNome = Input.getString("Nome (" + original.getNome() + "):");
        String nomeFinal = inputNome.isEmpty() ? original.getNome() : inputNome;

        String inputPreco = Input.getString("Preco (" + original.getPreco() + "):");
        double precoFinal = inputPreco.isEmpty() ? original.getPreco() : Double.parseDouble(inputPreco);

        String inputQuantidade = Input.getString("Quantidade (" + original.getQuantidade() + "):");
        int quantidadeFinal = inputQuantidade.isEmpty() ? original.getQuantidade() : Integer.parseInt(inputQuantidade);

        String inputTempoGarantia = Input.getString("Tempo de garantia (em meses) (" + original.getTempoGarantia() + "):");
        int tempoGarantiaFinal = inputTempoGarantia.isEmpty() ? original.getTempoGarantia() : Integer.parseInt(inputTempoGarantia);

        String inputDataRecebimento = Input.getString("Insira a nova data de recebimento (formato YYYY-MM-DD):");
        String dataFinal = inputDataRecebimento.isEmpty() ? original.getDataRecebimento() : inputDataRecebimento;

        if (!inputDataRecebimento.isEmpty()) {
            try {
                dataFinal = inputDataRecebimento;
            } catch (Exception e) {
                System.out.println("Formato de data inválido, mantendo a original.");
            }
        }

        int idCatFinal = original.getIdCategoria();
        String nomeCatFinal = original.getNomeCategoria();

        System.out.println("--- Categorias Disponíveis ---");
        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }

        String idCatInput = Input.getString("ID Categoria (" + original.getIdCategoria() + " - " + original.getNomeCategoria() + "):");

        if (!idCatInput.isEmpty()) {
            int idDigitado = Integer.parseInt(idCatInput);
            var novaCat = categorias.stream()
                    .filter(c -> c.getId() == idDigitado)
                    .findFirst()
                    .orElse(null);

            if (novaCat != null) {
                idCatFinal = novaCat.getId();
                nomeCatFinal = novaCat.getNome();
            } else {
                System.out.println("Categoria nova não encontrada. Mantendo a antiga.");
            }
        }

        int idFornFinal = original.getIdFornecedor();
        String nomeFornFinal = original.getNomeFornecedor();

        System.out.println("--- Fornecedores Disponíveis ---");
        for (var fornecedor : fornecedores) {
            System.out.println(fornecedor.toString());
        }

        String idFornInput = Input.getString("ID Fornecedor (" + original.getIdFornecedor() + " - " + original.getNomeFornecedor() + "):");

        if (!idFornInput.isEmpty()) {
            int idDigitado = Integer.parseInt(idFornInput);
            // Busca o novo fornecedor na lista
            var novoForn = fornecedores.stream()
                    .filter(f -> f.getId() == idDigitado)
                    .findFirst()
                    .orElse(null);

            if (novoForn != null) {
                idFornFinal = novoForn.getId();
                nomeFornFinal = novoForn.getNome();
            } else {
                System.out.println("Fornecedor novo não encontrado. Mantendo o antigo.");
            }
        }

        Produto produtoAtualizado = new Produto(id, nomeFinal, precoFinal, tempoGarantiaFinal, dataFinal, quantidadeFinal);

        produtoAtualizado.setIdCategoria(idCatFinal);
        produtoAtualizado.setNomeCategoria(nomeCatFinal);

        produtoAtualizado.setIdFornecedor(idFornFinal);
        produtoAtualizado.setNomeFornecedor(nomeFornFinal);

        produtosRepository.updateProduto(produtoAtualizado);
    }

    public void deleteProduto() {
        System.out.println("---- Excluindo produto ----");

        var produtos = produtosRepository.getAllProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para excluir um produto, crie um primeiro.");
            return;
        }

        int id = getIdProdutoDaLista(produtos, "Insira o ID do produto a excluir:");

        try {
            produtosRepository.deleteProduto(id);
            System.out.println("Produto excluído com sucesso.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getIdProdutoDaLista(ArrayList<Produto> produtos, String mensagemInput) {
        System.out.println("Listando todos produtos para edição:");

        for (var produto : produtos) {
            System.out.println(produto.toString());
        }

        while (true) {
            int input = Input.getInt(mensagemInput);

            var produtoEncontrado = produtos
                    .stream()
                    .anyMatch(x -> x.getId() == input);

            if (produtoEncontrado)
                return input;

            System.out.println("Produto " + input + " não encontrado, tente outro.");
        }
    }

    public void getRelatorioProdutosFornecedor() {
        System.out.println("---- Relatório de Produtos por Fornecedor ----");

        var fornecedores = fornecedoresRepository.getAllFornecedores();
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado. Impossível gerar relatório.");
            return;
        }

        System.out.println("Fornecedores disponíveis:");
        for (var fornecedor : fornecedores) {
            System.out.println(fornecedor.toString());
        }

        int idFornecedor = Input.getInt("Insira o id do fornecedor a ser consultado:");
        Fornecedor fornecedorSelecionado = fornecedores.stream()
                .filter(x -> x.getId() == idFornecedor)
                .findFirst()
                .orElse(null);

        if (fornecedorSelecionado == null) {
            System.out.println("Erro: Fornecedor com ID " + idFornecedor + " não encontrado.");
            return;
        }

        var produtosDoFornecedor = produtosRepository.getRelatorioProdutosFornecedor(idFornecedor);

        System.out.println("---- Listando produtos de " + fornecedorSelecionado.getNome() + " ----");

        if (produtosDoFornecedor.isEmpty()) {
            System.out.println("Nenhum produto encontrado para este fornecedor.");
        } else {
            for (var produto : produtosDoFornecedor) {
                System.out.println(produto.toString());
            }
        }
        System.out.println("---- Fim do Relatório ----");

    }

    public void getRelatorioProdutosCategoria() {
        System.out.println("---- Relatório de Produtos por Categoria ----");

        var categorias = categoriasRepository.getAllCategorias();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada. Impossível gerar relatório.");
            return;
        }

        System.out.println("Categorais disponíveis:");
        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }

        int idCategoria = Input.getInt("Insira o id da categoria a ser consultada:");
        Categoria categoriaSelecionada = categorias.stream()
                .filter(x -> x.getId() == idCategoria)
                .findFirst()
                .orElse(null);

        if (categoriaSelecionada == null) {
            System.out.println("Erro: Categoria com ID " + idCategoria + " não encontrada.");
            return;
        }

        var produtosDoCategoria = produtosRepository.getRelatorioProdutosCategoria(idCategoria);

        System.out.println("---- Listando produtos de " + categoriaSelecionada.getNome() + " ----");

        if (produtosDoCategoria.isEmpty()) {
            System.out.println("Nenhum produto encontrado para esta categoria.");
        } else {
            for (var produto : produtosDoCategoria) {
                System.out.println(produto.toString());
            }
        }
        System.out.println("---- Fim do Relatório ----");
    }


}
