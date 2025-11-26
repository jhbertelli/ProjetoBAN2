package application2;

import domain2.Categoria;
import infrastructure2.CategoriasRepository;
import infrastructure2.Input;

import java.util.ArrayList;

public class CategoriasController {
    private final CategoriasRepository categoriasRepository;

    public CategoriasController(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    public void getAllCategorias() {
        var categorias = categoriasRepository.getAllCategorias();

        System.out.println("---- Listando categorias ----");

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
            return;
        }

        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }
    }

    public void createCategoria() {
        System.out.println("---- Adicionando categoria ----");

        String nome = Input.getString("Insira o nome da categoria:");

        int novoId = categoriasRepository.getHighestId() + 1;

        categoriasRepository.createCategoria(new Categoria(novoId, nome));
    }

    public void updateCategoria() {
        System.out.println("---- Atualizando categorias ----");

        var categorias = categoriasRepository.getAllCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Erro: nenhuma categoria encontrada! Para atualizar uma categoria, crie uma primeiro.");
            return;
        }

        int id = getIdCategoriaDaLista(categorias, "Insira o ID da categoria a atualizar:");

        String nome = Input.getString("Insira o novo nome da categoria:");

        var categoria = new Categoria(id, nome);

        categoriasRepository.updateCategoria(categoria);
    }

    public void deleteCategoria() {
        System.out.println("---- Excluindo categoria ----");

        var categorias = categoriasRepository.getAllCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Erro: nenhuma categoria encontrada! Para excluir uma categoria, crie uma primeiro.");
            return;
        }

        int id = getIdCategoriaDaLista(categorias, "Insira o ID da categoria a excluir:");

        categoriasRepository.deleteCategoria(id);
    }

    private static int getIdCategoriaDaLista(ArrayList<Categoria> categorias, String mensagemInput) {
        System.out.println("Listando todas categorias para edição:");

        for (var categoria : categorias) {
            System.out.println(categoria.toString());
        }

        while (true) {
            int input = Input.getInt(mensagemInput);

            var categoriaEncontrada = categorias
                .stream()
                .anyMatch(x -> x.getId() == input);

            if (categoriaEncontrada)
                return input;

            System.out.println("Categoria " + input + " não encontrada, tente outra.");
        }
    }
}
