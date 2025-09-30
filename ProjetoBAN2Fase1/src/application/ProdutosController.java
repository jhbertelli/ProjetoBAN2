package application;

import domain.Produto;
import infrastucture.CategoriasRepository;
import infrastucture.FornecedoresRepository;
import infrastucture.ProdutosRepository;
import infrastucture.Input;

import java.sql.SQLException;
import java.sql.Date;

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
            System.out.printf(produto.toString());
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
            System.out.printf(categoria.toString());
        }

        int idCategoria = Input.getInt("Insira o ID da categoria do produto:");

        for (var fornecedor : fornecedores) {
            System.out.printf(fornecedor.toString());
        }

        int idFornecedor = Input.getInt("Insira o ID do fornecedor do produto:");

        var produto = new Produto(nome, preco, tempoGarantia, dataRecebimento, quantidade);
        produto.setIdCategoria(idCategoria);
        produto.setIdFornecedor(idFornecedor);

        produtosRepository.createProduto(produto);
    }

    public void updateProduto() throws SQLException {
        System.out.println("---- Atualizando produtos ----");

        int id = Input.getInt("Insira o ID do produto a atualizar:");

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

        int id = Input.getInt("Insira o ID do produto a excluir:");

        produtosRepository.deleteProduto(id);
    }
}
