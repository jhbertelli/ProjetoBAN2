package application;

import domain.Categoria;
import domain.Produto;
import domain.Venda;
import domain.Vendedor;
import infrastucture.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class VendasController {
    private final VendasRepository vendasRepository;
    private final ProdutosRepository produtosRepository;
    private final VendedoresRepository vendedoresRepository;
    private final CategoriasRepository categoriasRepository;

    public VendasController(VendasRepository vendasRepository, ProdutosRepository produtosRepository, VendedoresRepository vendedoresRepository, CategoriasRepository categoriasRepository) {
        this.vendasRepository = vendasRepository;
        this.produtosRepository = produtosRepository;
        this.vendedoresRepository = vendedoresRepository;
        this.categoriasRepository = categoriasRepository;
    }

    public void getAllVendas() throws SQLException {
        var vendas = vendasRepository.getAllVendas();

        System.out.println("---- Listando vendas ----");

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada.");
            return;
        }

        for (var venda : vendas) {
            System.out.println(venda.toString());
        }
    }

    public void createVenda() throws SQLException {
        var produtos = produtosRepository.getAllProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Erro: nenhum produto encontrado! Para criar uma venda, crie um produto primeiro.");
            return;
        }

        System.out.println("---- Adicionando venda ----");

        var vendedores = vendedoresRepository.getAllVendedores();

        Vendedor vendedor = findVendedorFromLista(vendedores);

        int proximoIdVenda = vendasRepository.getProximoIdVenda();

        var venda = criarVenda(proximoIdVenda, vendedor, produtos);

        for (var vendaProduto : venda.getVendaProdutos())
            produtosRepository.decrementarQuantidade(
                vendaProduto.getProduto().getId(),
                vendaProduto.getQuantidadeVendida()
            );

        vendasRepository.createVenda(venda);
    }

    private static Venda criarVenda(int idVenda, Vendedor vendedor, ArrayList<Produto> produtos) {
        var venda = new Venda(idVenda);
        venda.setVendedor(vendedor);

        System.out.println("Listando produtos:");
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

            var produtoJaAdicionado = venda
                .getVendaProdutos()
                .stream()
                .anyMatch(x -> x.getProduto() == produto);

            if (produtoJaAdicionado) {
                int quantidade = Input.getInt("Você já inseriu este produto. Informe a quantidade nova, ou 0 para removê-lo:");

                if (quantidade == 0)
                    venda.removerProduto(produto);
                else {
                    venda.atualizarProduto(produto, quantidade);
                    System.out.println("Produto " + idProduto + " atualizado com a quantidade: " + quantidade);
                }
                continue;
            }

            int quantidade = Input.getInt("Insira a quantidade vendida, ou 0 para não adicionar este produto:");

            while (quantidade > produto.getQuantidade()) {
                quantidade = Input.getInt("A quantidade inserida é maior que a quantidade existente no estoque. Insira uma quantidade menor, ou 0 para não adicionar este produto:");
            }

            if (quantidade == 0) continue;

            venda.adicionarProduto(produto, quantidade);
            System.out.println("Produto " + idProduto + " adicionado com a quantidade: " + quantidade);
        }

        return venda;
    }

    private static Vendedor findVendedorFromLista(ArrayList<Vendedor> vendedores) {
        int idVendedor = 0;

        if (!vendedores.isEmpty()) {
            System.out.println("Listando vendedores:");

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

        return idVendedor > 0 ? new Vendedor(idVendedor) : null;
    }

    public void updateVenda() throws SQLException {
        System.out.println("---- Atualizando venda ----");

        var vendas = vendasRepository.getAllVendas();

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para atualizar.");
            return;
        }

        System.out.println("---- Listando vendas existentes ----");
        for (var venda : vendas) {
            System.out.println(venda.toString());
        }

        int idVenda = Input.getInt("Insira o ID da venda a ser atualizada:");

        Venda vendaOriginal = vendas.stream()
            .filter(v -> v.getId() == idVenda)
            .findFirst()
            .orElse(null);

        if (vendaOriginal == null) {
            System.out.println("Venda com ID " + idVenda + " não encontrada.");
            return;
        }

        for (var vendaProduto : vendaOriginal.getVendaProdutos()) {
            produtosRepository.incrementarQuantidade(
                vendaProduto.getProduto().getId(),
                vendaProduto.getQuantidadeVendida()
            );
        }

        System.out.println("---- Insira os novos dados para a venda ----");

        var vendedores = vendedoresRepository.getAllVendedores();
        Vendedor vendedor = findVendedorFromLista(vendedores);

        var produtos = produtosRepository.getAllProdutos();

        var vendaAtualizada = criarVenda(idVenda, vendedor, produtos);

        for (var vendaProduto : vendaAtualizada.getVendaProdutos())
            produtosRepository.decrementarQuantidade(
                vendaProduto.getProduto().getId(),
                vendaProduto.getQuantidadeVendida()
            );

        vendasRepository.updateVenda(vendaAtualizada);
    }

    public void getRelatorioVendas() throws SQLException {
        System.out.println("---- Relatório de vendas por vendedor ----");

        var vendedores = vendedoresRepository.getAllVendedores();
        if (vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado. Impossível gerar relatório.");
            return;
        }

        System.out.println("Vendedores disponíveis:");
        for (var vendedor : vendedores) {
            System.out.println(vendedor.toString());
        }

        int idVendedor = Input.getInt("Insira o id do vendedor a ser consultado:");
        Vendedor vendedorSelecionado = vendedores.stream()
                .filter(v -> v.getId() == idVendedor)
                .findFirst()
                .orElse(null);

        if (vendedorSelecionado == null) {
            System.out.println("Erro: Vendedor com ID " + idVendedor + " não encontrado.");
            return;
        }


        var vendasDoVendedor = vendasRepository.getRelatorioVendas(idVendedor);

        System.out.println("---- Listando vendas de " + vendedorSelecionado.getNome() + " ----");

        if (vendasDoVendedor.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para este vendedor.");
        } else {
            for (var venda : vendasDoVendedor) {
                System.out.println(venda.toString());
            }
        }
        System.out.println("---- Fim do Relatório ----");

    }

    public void getRelatorioVendasCategoria() throws SQLException {
        System.out.println("---- Relatório de vendas por categoria ----");

        var categorias = categoriasRepository.getAllCategorias();
        if (categorias.isEmpty()){
            System.out.println("Nenhuma categoria cadastrada. Impossível gerar relatório.");
            return;
        }

        System.out.println("Categorias disponíveis:");
        for (var categoria : categorias){
            System.out.println(categoria.toString());
        }

        int idCategoria = Input.getInt("Insira o id da categoria a ser consultada:");
        Categoria categoriaSelecionada = categorias.stream()
                .filter(v -> v.getId() == idCategoria)
                .findFirst()
                .orElse(null);

        if (categoriaSelecionada == null) {
            System.out.println("Erro: Categoria com ID " + idCategoria + " não encontrada.");
            return;
        }

        var vendasDaCategoria = vendasRepository.getRelatorioVendasCategoria(idCategoria);

        System.out.println("---- Listando vendas de " + categoriaSelecionada.getNome() + " ----");

        if (vendasDaCategoria.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para esta categoria.");
        } else {
            for (var venda : vendasDaCategoria) {
                System.out.println(venda.toString());
            }
        }
        System.out.println("---- Fim do Relatório ----");

    }

//
//    public void deleteVenda() throws SQLException {
//        System.out.println("---- Excluindo venda ----");
//
//        int id = Input.getInt("Insira o ID do venda a excluir:");
//
//        vendasRepository.deleteVenda(id);
//    }
}
