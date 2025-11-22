package application2;

import domain.Vendedor;
import infrastucture.Input;
import infrastucture.VendedoresRepository;

import java.sql.SQLException;
import java.util.ArrayList;

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
            System.out.println(vendedor.toString());
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
        System.out.println("---- Atualizando vendedor ----");

        var vendedores = vendedoresRepository.getAllVendedores();

        if (vendedores.isEmpty()) {
            System.out.println("Erro: nenhum vendedor encontrado! Para atualizar um vendedor, crie um primeiro.");
            return;
        }

        int id = getIdVendedorDaLista(vendedores, "Insira o ID do vendedor a atualizar:");

        String nome = Input.getString("Insira o novo nome do vendedor:");

        String endereco = Input.getString("Insira o novo endereco do vendedor:");

        String telefone = Input.getString("Insira o novo telefone do vendedor:");

        String email = Input.getString("Insira o novo email do vendedor:");

        var vendedor = new Vendedor(id, nome, endereco, telefone, email);

        vendedoresRepository.updateVendedor(vendedor);
    }

    public void deleteVendedor() throws SQLException {
        System.out.println("---- Excluindo vendedor ----");

        var vendedores = vendedoresRepository.getAllVendedores();

        if (vendedores.isEmpty()) {
            System.out.println("Erro: nenhum vendedor encontrado! Para excluir um vendedor, crie um primeiro.");
            return;
        }

        int id = getIdVendedorDaLista(vendedores, "Insira o ID do vendedor a excluir:");

        vendedoresRepository.deleteVendedor(id);
    }

    private static int getIdVendedorDaLista(ArrayList<Vendedor> vendedores, String mensagemInput) {
        System.out.println("Listando todos vendedores para edição:");

        for (var vendedor : vendedores) {
            System.out.println(vendedor.toString());
        }

        while (true) {
            int input = Input.getInt(mensagemInput);

            var vendedorEncontrado = vendedores
                    .stream()
                    .anyMatch(x -> x.getId() == input);

            if (vendedorEncontrado)
                return input;

            System.out.println("Vendedor " + input + " não encontrado, tente outro.");
        }
    }
}
