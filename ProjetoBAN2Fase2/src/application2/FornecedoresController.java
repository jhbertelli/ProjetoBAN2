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

        int novoId = fornecedoresRepository.getHighestId() + 1;

        fornecedoresRepository.createFornecedor(new Fornecedor(
                novoId,
                endereco,
                telefone,
                nome,
                nomeFantasia,
                documento,
                email
        ));
    }

    public void updateFornecedor() {
        System.out.println("---- Atualizando fornecedores ----");

        var fornecedores = fornecedoresRepository.getAllFornecedores();

        if (fornecedores.isEmpty()) {
            System.out.println("Erro: nenhum fornecedor encontrado!");
            return;
        }

        int id = getIdFornecedorDaLista(fornecedores, "Insira o ID do fornecedor a atualizar:");

        Fornecedor original = fornecedores.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);

        if (original == null) return;

        System.out.println("Pressione ENTER para manter o valor atual mostrado entre (parênteses).");

        String inputNome = Input.getString("Nome (" + original.getNome() + "):");
        String nomeFinal = inputNome.isEmpty() ? original.getNome() : inputNome;

        String inputFantasia = Input.getString("Nome Fantasia (" + original.getNomeFantasia() + "):");
        String fantasiaFinal = inputFantasia.isEmpty() ? original.getNomeFantasia() : inputFantasia;

        String inputEndereco = Input.getString("Endereço (" + original.getEndereco() + "):");
        String enderecoFinal = inputEndereco.isEmpty() ? original.getEndereco() : inputEndereco;

        String inputTelefone = Input.getString("Telefone (" + original.getTelefone() + "):");
        String telefoneFinal = inputTelefone.isEmpty() ? original.getTelefone() : inputTelefone;

        String inputDoc = Input.getString("CNPJ (" + original.getDocumento() + "):");
        String docFinal = inputDoc.isEmpty() ? original.getDocumento() : inputDoc;

        String inputEmail = Input.getString("Email (" + original.getEmail() + "):");
        String emailFinal = inputEmail.isEmpty() ? original.getEmail() : inputEmail;

        var fornecedorAtualizado = new Fornecedor(
                id,
                enderecoFinal,
                telefoneFinal,
                nomeFinal,
                fantasiaFinal,
                docFinal,
                emailFinal
        );

        fornecedoresRepository.updateFornecedor(fornecedorAtualizado);
    }

    public void deleteFornecedor() {
        System.out.println("---- Excluindo fornecedor ----");

        var fornecedores = fornecedoresRepository.getAllFornecedores();

        if (fornecedores.isEmpty()) {
            System.out.println("Erro: nenhum fornecedor encontrado! Para excluir um fornecedor, crie um primeiro.");
            return;
        }

        int id = getIdFornecedorDaLista(fornecedores, "Insira o ID do fornecedor a excluir:");

        try {
            fornecedoresRepository.deleteFornecedor(id);
            System.out.println("Fonecedor deletado com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
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