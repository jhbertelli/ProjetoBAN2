package application;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.Categoria;
import infrastucture.CategoriasRepository;
import infrastucture.Input;

public class CategoriasController {
    private final CategoriasRepository categoriasRepository;

    public CategoriasController(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    public void getAllCategorias() throws SQLException {
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

    public void createCategoria() throws SQLException {
        System.out.println("---- Adicionando categoria ----");

        String nome = Input.getString("Insira o nome da categoria:");

        var categoria = new Categoria(nome);

        categoriasRepository.createCategoria(categoria);
    }

    public void updateCategoria() throws SQLException {
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

    public void deleteCategoria() throws SQLException {
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
