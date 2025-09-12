import application.CategoriasController;
import application.FornecedoresController;
import application.VendedoresController;
import infrastucture.*;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final int QUIT_OPTION = 20;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Connection connection = new PostgresService().getConnection();

        int opcao = 0;

        var categoriasRepository = new CategoriasRepository(connection);
        var categoriasController = new CategoriasController(categoriasRepository);

        var vendedoresRepository = new VendedoresRepository(connection);
        var vendedoresController = new VendedoresController(vendedoresRepository);

        var fornecedoresRepository = new FornecedoresRepository(connection);
        var fornecedoresController = new FornecedoresController(fornecedoresRepository);

        while (opcao != QUIT_OPTION) {
            exibirMenu();

            opcao = Input.getInt();

            try {
                switch (opcao) {
                    case 1:
                        categoriasController.getAllCategorias();
                        break;
                    case 2:
                        categoriasController.createCategoria();
                        break;
                    case 3:
                        categoriasController.updateCategoria();
                        break;
                    case 4:
                        categoriasController.deleteCategoria();
                        break;
                    case 5:
                        vendedoresController.getAllVendedores();
                        break;
                    case 6:
                        vendedoresController.createVendedor();
                        break;
                    case 7:
                        vendedoresController.updateVendedor();
                        break;
                    case 8:
                        vendedoresController.deleteVendedor();
                        break;
                    case 9:
                        vendedoresController.getAllVendedores();
                        break;
                    case 10:
                        fornecedoresController.createFornecedor();
                        break;
                    case 11:
                        fornecedoresController.updateFornecedor();
                        break;
                    case 12:
                        fornecedoresController.deleteFornecedor();
                        break;
                    case QUIT_OPTION:
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("1 - Listar categorias");
        System.out.println("2 - Adicionar categoria");
        System.out.println("3 - Atualizar categoria");
        System.out.println("4 - Excluir categoria");
        System.out.println("5 - Listar vendedores");
        System.out.println("6 - Adicionar vendedor");
        System.out.println("7 - Atualizar vendedor");
        System.out.println("8 - Excluir vendedor");
        System.out.println("9 - Listar fornecedores");
        System.out.println("10 - Adicionar fornecedor");
        System.out.println("11 - Atualizar fornecedor");
        System.out.println("12 - Excluir fornecedor");

        System.out.printf("%d - Sair\n", QUIT_OPTION);
        System.out.print("Escolha uma opção: ");
    }
}