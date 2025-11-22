package application2;

import domain.Categoria;
import domain.Fornecedor;
import domain.Produto;
import infrastucture.CategoriasRepository;
import infrastucture.FornecedoresRepository;
import infrastucture.Input;
import infrastucture.ProdutosRepository;

import java.sql.Date;
import java.sql.SQLException;
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

    public void getAllProdutos() throws SQLException {
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

    public void createProduto() throws SQLException {
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
        Date dataRecebimento = Input.getDate("Insira a data de recebimento (formato YYYY-MM-DD):");

        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }

        var produto = new Produto(nome, preco, tempoGarantia, dataRecebimento, quantidade);

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

            break;
        }

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

            break;
        }

        produtosRepository.createProduto(produto);
    }

    public void updateProduto() throws SQLException {
        System.out.println("---- Atualizando produtos ----");

        var produtos = produtosRepository.getAllProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para atualizar um produto, crie um primeiro.");
            return;
        }

        int id = getIdProdutoDaLista(produtos, "Insira o ID do produto a atualizar:");

        String nome = Input.getString("Insira o novo nome do produto:");
        double preco = Input.getDouble("Insira o novo preço do produto:");
        int quantidade = Input.getInt("Insira a nova quantidade em estoque:");
        int tempoGarantia = Input.getInt("Insira o novo tempo de garantia (em meses):");
        Date dataRecebimento = Input.getDate("Insira a nova data de recebimento (formato YYYY-MM-DD):");

        // aqui não precisa verificar se há categoria ou fornecedor porque
        // nenhuma categoria ou fornecedor pode ser excluído/a se houver um produto atrelado
        int idCategoria = Input.getInt("Insira o novo ID da Categoria:");
        int idFornecedor = Input.getInt("Insira o novo ID do Fornecedor:");

        var produto = new Produto(id, nome, preco, tempoGarantia, dataRecebimento, quantidade);
        produto.setIdCategoria(idCategoria);
        produto.setIdFornecedor(idFornecedor);

        produtosRepository.updateProduto(produto);
    }

    public void deleteProduto() throws SQLException {
        System.out.println("---- Excluindo produto ----");

        var produtos = produtosRepository.getAllProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para excluir um produto, crie um primeiro.");
            return;
        }

        int id = getIdProdutoDaLista(produtos, "Insira o ID do produto a excluir:");

        produtosRepository.deleteProduto(id);
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

    public void getRelatorioProdutosFornecedor() throws SQLException {
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

    public void getRelatorioProdutosCategoria() throws SQLException {
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
