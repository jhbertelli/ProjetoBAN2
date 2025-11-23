package application2;

import domain2.Fornecedor;
import infrastructure2.FornecedoresRepository;
import infrastructure2.Input;

import java.util.ArrayList;

public class FornecedoresController {
    private final FornecedoresRepository fornecedoresRepository;

    public FornecedoresController(FornecedoresRepository fornecedoresRepository) {
        this.fornecedoresRepository = fornecedoresRepository;
    }

    public void getAllFornecedores() {
        var fornecedores = fornecedoresRepository.getAllFornecedores();

        System.out.println(("---- Listando Fornecedores ----"));

        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor encontrado.");
            return;
        }

        for (var fornecedor : fornecedores) {
            System.out.println(fornecedor.toString());
        }
    }

    public void createFornecedor() {
        System.out.println("---- Adicionando fornecedor ----");

        String nome = Input.getString("Insira o nome (razão social) do fornecedor:");
        String nomeFantasia = Input.getString("Insira o nome fantasia do fornecedor:");
        String endereco = Input.getString("Insira o endereço do fornecedor:");
        String telefone = Input.getString("Insira o telefone do fornecedor:");
        String documento = Input.getString("Insira o CNPJ do fornecedor:");
        String email = Input.getString("Insira o e-mail do fornecedor:");

        var fornecedor = new Fornecedor(endereco, telefone, nome, nomeFantasia, documento, email);

        fornecedoresRepository.createFornecedor(fornecedor);
    }

    public void updateFornecedor() {
        System.out.println("---- Atualizando fornecedores ----");

        var fornecedores = fornecedoresRepository.getAllFornecedores();

        if (fornecedores.isEmpty()) {
            System.out.println("Erro: nenhum fornecedor encontrado! Para atualizar um fornecedor, crie um primeiro.");
            return;
        }

        int id = getIdFornecedorDaLista(fornecedores, "Insira o ID do fornecedor a atualizar:");

        String nome = Input.getString("Insira o novo nome do fornecedor:");
        String nomeFantasia = Input.getString("Insira o novo nome fantasia do fornecedor:");
        String endereco = Input.getString("Insira o novo endereço do fornecedor:");
        String telefone = Input.getString("Insira o novo telefone do fornecedor:");
        String documento = Input.getString("Insira o novo CNPJ do fornecedor:");
        String email = Input.getString("Insira o novo e-mail do fornecedor:");

        var fornecedor = new Fornecedor(id, endereco, telefone, nome, nomeFantasia, documento, email);

        fornecedoresRepository.updateFornecedor(fornecedor);
    }

    public void deleteFornecedor() {
        System.out.println("---- Excluindo fornecedor ----");

        var fornecedores = fornecedoresRepository.getAllFornecedores();

        if (fornecedores.isEmpty()) {
            System.out.println("Erro: nenhum fornecedor encontrado! Para excluir um fornecedor, crie um primeiro.");
            return;
        }

        int id = getIdFornecedorDaLista(fornecedores, "Insira o ID do fornecedor a excluir:");

        fornecedoresRepository.deleteFornecedor(id);
    }

    private static int getIdFornecedorDaLista(ArrayList<Fornecedor> fornecedores, String mensagemInput) {
        System.out.println("Listando todos fornecedores para edição:");

        for (var fornecedor : fornecedores) {
            System.out.println(fornecedor.toString());
        }

        while (true) {
            int input = Input.getInt(mensagemInput);

            var fornecedorEncontrado = fornecedores
                .stream()
                .anyMatch(x -> x.getId() == input);

            if (fornecedorEncontrado)
                return input;

            System.out.println("Fornecedor " + input + " não encontrado, tente outro.");
        }
    }
}