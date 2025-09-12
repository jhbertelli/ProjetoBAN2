package application;

import domain.Fornecedor;
import infrastucture.FornecedoresRepository;
import infrastucture.Input;
import infrastucture.VendedoresRepository;

import java.sql.SQLException;

public class FornecedoresController {
    private final FornecedoresRepository fornecedoresRepository;

    public FornecedoresController(FornecedoresRepository fornecedoresRepository) {
        this.fornecedoresRepository = fornecedoresRepository;
    }

    public void getAllFornecedores() throws SQLException {
        var fornecedores = fornecedoresRepository.getAllFornecedores();

        System.out.println(("--- Listando Fornecedores ---"));

        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor encontrado.");
            return;
        }

        for (var fornecedor : fornecedores) {
            System.out.printf(
                "ID: %d - Nome: %s - Nome Fantasia: %s - Endereço: %s - Telefone %s - Documento: %s - Email: %s\n",
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getNomeFantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getDocumento(),
                fornecedor.getEmail()
            );
        }
    }

    public void createFornecedor() throws SQLException {
        System.out.println("--- Adicionando fornecedor ---");

        String nome = Input.getString("Insira o nome do fornecedor:");
        String nomeFantasia = Input.getString("Insira o nome fantasia do fornecedor:");
        String endereco = Input.getString("Insira o endereço do fornecedor:");
        String telefone = Input.getString("Insira o telefone do fornecedor:");
        String documento = Input.getString("Insira o CNPJ do fornecedor:");
        String email = Input.getString("Insira o e-mail do fornecedor:");

        var fornecedor = new Fornecedor(endereco, telefone, nome, nomeFantasia, documento, email);

        fornecedoresRepository.createFornecedor(fornecedor);
    }

    public void updateFornecedor() throws SQLException {
        System.out.println("--- Atualizando fornecedores ---");

        int id = Input.getInt("Insira o ID do fornecedor a atualizar:");

        String nome = Input.getString("Insira o novo nome do fornecedor:");
        String nomeFantasia = Input.getString("Insira o novo nome fantasia do fornecedor:");
        String endereco = Input.getString("Insira o novo endereço do fornecedor:");
        String telefone = Input.getString("Insira o novo telefone do fornecedor:");
        String documento = Input.getString("Insira o novo CNPJ do fornecedor:");
        String email = Input.getString("Insira o novo e-mail do fornecedor:");

        var fornecedor = new Fornecedor(endereco, telefone, nome, nomeFantasia, documento, email);

        fornecedoresRepository.updateFornecedor(fornecedor);
    }

    public void deleteFornecedor() throws SQLException {
        System.out.println("--- Excluindo fornecedor ---");

        int id = Input.getInt("Insira o ID do fornecedor a excluir:");

        fornecedoresRepository.deleteFornecedor(id);
    }


}