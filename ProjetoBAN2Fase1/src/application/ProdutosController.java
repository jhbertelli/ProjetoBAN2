package application;

import domain.Produto;
import infrastucture.ProdutosRepository;
import infrastucture.Input;

import java.sql.SQLException;
import java.sql.Date;

public class ProdutosController {
    private final ProdutosRepository produtosRepository;

    public ProdutosController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    public void getAllProdutos() throws SQLException {
        var produtos = produtosRepository.getAllProdutos();

        System.out.println("---- Listando produtos ----");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        for (var produto : produtos) {
            System.out.printf(
                "ID: %d | Nome: %s | Preço: R$%.2f | Quantidade: %d | Tempo de Garantia: %d meses | Data de Recebimento: %s\n",
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidade(),
                produto.getTempoGarantia(),
                produto.getDataRecebimento()
            );
        }
    }

    public void createProduto() throws SQLException {
        System.out.println("---- Adicionando produto ----");

        String nome = Input.getString("Insira o nome do produto:");
        double preco = Input.getDouble("Insira o preço do produto:");
        int quantidade = Input.getInt("Insira a quantidade em estoque:");
        int tempoGarantia = Input.getInt("Insira o tempo de garantia (em meses):");
        Date dataRecebimento = Input.getDate("Insira a data de recebimento (formato YYYY-MM-DD):");

        // TODO: verificar se categoria/fornecedor existe, ou se há categorias ou fornecedores
        // a aplicação não está caindo, mas o registro não é salvo se continuar com categoria/fornecedor que não existe
        int idCategoria = Input.getInt("Insira o ID da Categoria:");
        int idFornecedor = Input.getInt("Insira o ID do Fornecedor:");

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

        // TODO: verificar se categoria/fornecedor existe, ou se há categorias ou fornecedores
        // a aplicação não está caindo, mas o registro não é salvo se continuar com categoria/fornecedor que não existe
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
