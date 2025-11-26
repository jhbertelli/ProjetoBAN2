package application2;

import domain2.Vendedor;
import infrastructure2.Input;
import infrastructure2.VendedoresRepository;

import java.util.ArrayList;

public class VendedoresController {
    private final VendedoresRepository vendedoresRepository;

    public VendedoresController(VendedoresRepository vendedoresRepository) {
        this.vendedoresRepository = vendedoresRepository;
    }

    public void getAllVendedores() {
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

    public void createVendedor() {
        System.out.println("---- Adicionando vendedor ----");

        String nome = Input.getString("Insira o nome do vendedor:");
        String endereco = Input.getString("Insira o endereco do vendedor:");
        String telefone = Input.getString("Insira o telefone do vendedor:");
        String email = Input.getString("Insira o email do vendedor:");

        int novoId = vendedoresRepository.getHighestId() + 1;

        var vendedor = new Vendedor(nome, endereco, telefone, email);
        vendedor.setId(novoId);

        vendedoresRepository.createVendedor(vendedor);
    }

    public void updateVendedor() {
        System.out.println("---- Atualizando vendedor ----");

        var vendedores = vendedoresRepository.getAllVendedores();

        if (vendedores.isEmpty()) {
            System.out.println("Erro: nenhum vendedor encontrado! Para atualizar um vendedor, crie um primeiro.");
            return;
        }

        int id = getIdVendedorDaLista(vendedores, "Insira o ID do vendedor a atualizar:");

        Vendedor original = vendedores.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);

        if (original == null) return;

        System.out.println("Pressione ENTER para manter o valor atual mostrado entre (parênteses).");

        String inputNome = Input.getString("Nome (" + original.getNome() + "):");
        String nomeFinal = inputNome.isEmpty() ? original.getNome() : inputNome;

        String inputEndereco = Input.getString("Endereço (" + original.getEndereco() + "):");
        String enderecoFinal = inputEndereco.isEmpty() ? original.getEndereco() : inputEndereco;

        String inputTelefone = Input.getString("Telefone (" + original.getTelefone() + "):");
        String telefoneFinal = inputTelefone.isEmpty() ? original.getTelefone() : inputTelefone;

        String inputEmail = Input.getString("Email (" + original.getEmail() + "):");
        String emailFinal = inputEmail.isEmpty() ? original.getEmail() : inputEmail;

        var vendedor = new Vendedor(id, nomeFinal, enderecoFinal, telefoneFinal, emailFinal);

        vendedoresRepository.updateVendedor(vendedor);
    }

    public void deleteVendedor() {
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
