package application;

import java.sql.SQLException;

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
            System.out.printf(categoria.toString());
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

        int id = Input.getInt("Insira o ID da categoria a atualizar:");

        String nome = Input.getString("Insira o novo nome da categoria:");

        var categoria = new Categoria(id, nome);

        categoriasRepository.updateCategoria(categoria);
    }

    public void deleteCategoria() throws SQLException {
        System.out.println("---- Excluindo categoria ----");

        int id = Input.getInt("Insira o ID da categoria a excluir:");

        categoriasRepository.deleteCategoria(id);
    }
}
