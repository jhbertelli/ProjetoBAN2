package application;

import domain.Venda;
import domain.Vendedor;
import infrastucture.Input;
import infrastucture.ProdutosRepository;
import infrastucture.VendasRepository;
import infrastucture.VendedoresRepository;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class VendasController {
    private final VendasRepository vendasRepository;
    private final ProdutosRepository produtosRepository;
    private final VendedoresRepository vendedoresRepository;

    public VendasController(VendasRepository vendasRepository, ProdutosRepository produtosRepository, VendedoresRepository vendedoresRepository) {
        this.vendasRepository = vendasRepository;
        this.produtosRepository = produtosRepository;
        this.vendedoresRepository = vendedoresRepository;
    }

//    public void getAllVendas() throws SQLException {
//        var vendas = vendasRepository.getAllVendas();
//
//        System.out.println("---- Listando vendas ----");
//
//        if (vendas.isEmpty()) {
//            System.out.println("Nenhuma venda encontrada.");
//            return;
//        }
//
//        for (var venda : vendas) {
//           System.out.printf(categoria.toString());
//        }
//
//        for (var venda : vendas) {
//            System.out.printf(
//                "ID: %d | Nome: %s | Endereço: %s | Telefone: %s | E-mail: %s\n",
//                venda.getId(),
//                venda.getNome(),
//                venda.getEndereco(),
//                venda.getTelefone(),
//                venda.getEmail()
//            );
//        }
//    }

    public void createVenda() throws SQLException {
        var produtos = produtosRepository.getAllProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para criar uma venda, crie um produto primeiro.");
            return;
        }

        System.out.println("---- Adicionando venda ----");

        var vendedores = vendedoresRepository.getAllVendedores();

        int idVendedor = 0;

        if (!vendedores.isEmpty()) {
            for (var vendedor : vendedores) {
                System.out.println(vendedor.toString());
            }

            idVendedor = Input.getInt("Insira o ID do vendedor que atendeu a venda, ou 0 caso não haja:");

            while (idVendedor > 0) {
                int finalIdVendedor = idVendedor;

                var vendedorEncontrado = vendedores
                    .stream()
                    .anyMatch(x -> x.getId() == finalIdVendedor);

                if (!vendedorEncontrado) {
                    idVendedor = Input.getInt("Vendedor não encontrado. Insira o ID do vendedor que atendeu a venda, ou 0 caso não haja:");
                } else {
                    break;
                }
            }
        } else {
            System.out.println("Nenhum vendedor encontrado, prosseguindo para adição de produtos na venda");
        }

        Vendedor vendedor = idVendedor > 0 ? new Vendedor(idVendedor) : null;

        int ultimoIdVenda = vendasRepository.getUltimoIdVenda();

        var venda = new Venda(ultimoIdVenda);
        venda.setVendedor(vendedor);

        for (var produto : produtos) {
            System.out.println(produto.toString());
        }

        while (true) {
            int idProduto = Input.getInt("Insira o ID do produto a ser adicionado ou 0 para finalizar a adição de produtos:");

            if (idProduto == 0) {
                if (venda.getVendaProdutos().isEmpty()) {
                    System.out.println("Ao menos um produto deve ser atrelado a uma venda. Tente novamente.");
                    continue;
                }

                break;
            }

            var produto = produtos.stream()
                .filter(x -> x.getId() == idProduto)
                .findFirst()
                .orElse(null);

            if (produto == null) {
                System.out.println("O produto com o ID " + idProduto + " não foi encontrado, tente outro.");
                continue;
            }

            var produtosAdicionados = venda.getVendaProdutos()
                .stream()
                .filter(x -> x.getIdProduto() == idProduto)
                .toList();

            int produtoQuantidadeTotalVendidos = 0;

            for (var vendaProduto : produtosAdicionados)
                produtoQuantidadeTotalVendidos += vendaProduto.getQuantidadeVendida();

            int produtoQuantidadeTotalDisponivel = produto.getQuantidade() - produtoQuantidadeTotalVendidos;

            int quantidade = Input.getInt("Insira a quantidade vendida, ou 0 para não adicionar este produto:");

            while (quantidade > produtoQuantidadeTotalDisponivel) {
                quantidade = Input.getInt("A quantidade inserida é maior que a quantidade existente no estoque. Insira uma quantidade menor, ou 0 para não adicionar este produto:");
            }

            if (quantidade == 0) continue;

            produtosRepository.decrementarQuantidade(idProduto, quantidade);
            venda.adicionarProduto(idProduto, quantidade);
            System.out.println("Produto " + idProduto + " adicionado com a quantidade: " + quantidade);
        }

        vendasRepository.createVenda(venda);
    }

//    public void updateVenda() throws SQLException {
//        System.out.println("---- Atualizando vendas ----");
//
//        int id = Input.getInt("Insira o ID do venda a atualizar:");
//
//        String nome = Input.getString("Insira o novo nome do venda:");
//
//        String endereco = Input.getString("Insira o novo endereco do venda:");
//
//        String telefone = Input.getString("Insira o novo telefone do venda:");
//
//        String email = Input.getString("Insira o novo email do venda:");
//
//        var venda = new Venda(id, nome, endereco, telefone, email);
//
//        vendasRepository.updateVenda(venda);
//    }
//
//    public void deleteVenda() throws SQLException {
//        System.out.println("---- Excluindo venda ----");
//
//        int id = Input.getInt("Insira o ID do venda a excluir:");
//
//        vendasRepository.deleteVenda(id);
//    }
}
