package application;

import domain.Vendedor;
import infrastucture.VendedoresRepository;
import infrastucture.Input;

import java.sql.SQLException;

public class VendedoresController {
    private final VendedoresRepository vendedoresRepository;

    public VendedoresController(VendedoresRepository vendedoresRepository) {
        this.vendedoresRepository = vendedoresRepository;
    }

    public void getAllVendedores() throws SQLException {
        var vendedores = vendedoresRepository.getAllVendedores();

        System.out.println("---- Listando vendedores ----");

        if (vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor encontrado.");
            return;
        }

        for (var vendedor : vendedores) {
            System.out.printf(
                "ID: %d | Nome: %s | Endereço: %s | Telefone: %s | E-mail: %s\n",
                vendedor.getId(),
                vendedor.getNome(),
                vendedor.getEndereco(),
                vendedor.getTelefone(),
                vendedor.getEmail()
            );
        }
    }

    public void createVendedor() throws SQLException {
        System.out.println("---- Adicionando vendedor ----");

        String nome = Input.getString("Insira o nome do vendedor:");

        String endereco = Input.getString("Insira o endereco do vendedor:");

        String telefone = Input.getString("Insira o telefone do vendedor:");

        String email = Input.getString("Insira o email do vendedor:");

        var vendedor = new Vendedor(nome, endereco, telefone, email);

        vendedoresRepository.createVendedor(vendedor);
    }

    public void updateVendedor() throws SQLException {
        System.out.println("---- Atualizando vendedores ----");

        int id = Input.getInt("Insira o ID do vendedor a atualizar:");

        String nome = Input.getString("Insira o novo nome do vendedor:");

        String endereco = Input.getString("Insira o novo endereco do vendedor:");

        String telefone = Input.getString("Insira o novo telefone do vendedor:");

        String email = Input.getString("Insira o novo email do vendedor:");

        var vendedor = new Vendedor(id, nome, endereco, telefone, email);

        vendedoresRepository.updateVendedor(vendedor);
    }

    public void deleteVendedor() throws SQLException {
        System.out.println("---- Excluindo vendedor ----");

        int id = Input.getInt("Insira o ID do vendedor a excluir:");

        vendedoresRepository.deleteVendedor(id);
    }
}
