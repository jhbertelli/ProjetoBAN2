package application;

import domain.Venda;
import infrastucture.Input;
import infrastucture.VendasRepository;

import java.sql.SQLException;

public class VendasController {
    private final VendasRepository vendasRepository;

    public VendasController(VendasRepository vendasRepository) {
        this.vendasRepository = vendasRepository;
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
        System.out.println("---- Adicionando venda ----");

        Integer idVendedor = Input.getInt("Insira o ID do vendedor que atendeu a venda, ou 0 caso não haja:");
        if (idVendedor == 0) idVendedor = null;

        int ultimoIdVenda = vendasRepository.getUltimoIdVenda();

        var venda = new Venda(ultimoIdVenda);
        venda.setIdVendedor(idVendedor);

        int qtdProdutos = Input.getInt("Quantos registros de produto você deseja adicionar para esta venda?");

        for (int i = 0; i < qtdProdutos; i++) {
            int idProduto = Input.getInt("Insira o ID do produto:");
            int quantidade = Input.getInt("Insira a quantidade vendida:");

            venda.adicionarProduto(idProduto, quantidade);
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
