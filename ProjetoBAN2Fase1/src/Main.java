import application.*;
import domain.Produto;
import domain.Venda;
import infrastucture.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final int CATEGORIAS_OPTION = 1;
    private static final int FORNECEDORES_OPTION = 2;
    private static final int VENDEDORES_OPTION = 3;
    private static final int PRODUTOS_OPTION = 4;
    private static final int VENDAS_OPTION = 5;
    private static final int QUIT_OPTION = 6;

    public static void main(String[] args) throws SQLException {
        Connection connection = new PostgresService().getConnection();

        int opcao = 0;

        var categoriasRepository = new CategoriasRepository(connection);
        var categoriasController = new CategoriasController(categoriasRepository);

        var vendedoresRepository = new VendedoresRepository(connection);
        var vendedoresController = new VendedoresController(vendedoresRepository);

        var fornecedoresRepository = new FornecedoresRepository(connection);
        var fornecedoresController = new FornecedoresController(fornecedoresRepository);

        var produtosRepository = new ProdutosRepository(connection);
        var produtosController = new ProdutosController(produtosRepository, categoriasRepository, fornecedoresRepository);

        var vendasRepository = new VendasRepository(connection);
        var vendasController = new VendasController(vendasRepository, produtosRepository, vendedoresRepository, categoriasRepository);

        while (opcao != QUIT_OPTION) {
            exibirMenuPrincipal();

            opcao = Input.getInt();

            try {
                switch (opcao) {
                    case CATEGORIAS_OPTION:
                        gerenciarCategorias(categoriasController);
                        break;
                    case FORNECEDORES_OPTION:
                        gerenciarFornecedores(fornecedoresController);
                        break;
                    case VENDEDORES_OPTION:
                        gerenciarVendedores(vendedoresController);
                        break;
                    case PRODUTOS_OPTION:
                        gerenciarProdutos(produtosController);
                        break;
                    case VENDAS_OPTION:
                        gerenciarVendas(vendasController);
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

    private static void gerenciarCategorias(CategoriasController categoriasController) throws SQLException {
        int opcao = 0;
        final int opcao_voltar = 5;

        while (opcao != opcao_voltar) {
            exibirMenuCrud("categoria", "categorias");
            opcao = Input.getInt();

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
                case opcao_voltar:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void gerenciarFornecedores(FornecedoresController fornecedoresController) throws SQLException {
        int opcao = 0;
        final int opcao_voltar = 5;

        while (opcao != opcao_voltar) {
            exibirMenuCrud("fornecedor", "fornecedores");
            opcao = Input.getInt();

            switch (opcao) {
                case 1:
                    fornecedoresController.getAllFornecedores();
                    break;
                case 2:
                    fornecedoresController.createFornecedor();
                    break;
                case 3:
                    fornecedoresController.updateFornecedor();
                    break;
                case 4:
                    fornecedoresController.deleteFornecedor();
                    break;
                case opcao_voltar:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void gerenciarVendedores(VendedoresController vendedoresController) throws SQLException {
        int opcao = 0;
        final int opcao_voltar = 5;

        while (opcao != opcao_voltar) {
            exibirMenuCrud("vendedor", "vendedores");
            opcao = Input.getInt();

            switch (opcao) {
                case 1:
                    vendedoresController.getAllVendedores();
                    break;
                case 2:
                    vendedoresController.createVendedor();
                    break;
                case 3:
                    vendedoresController.updateVendedor();
                    break;
                case 4:
                    vendedoresController.deleteVendedor();
                    break;
                case opcao_voltar:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void gerenciarProdutos(ProdutosController produtosController) throws SQLException {
        int opcao = 0;
        final int opcao_voltar = 7;

        while (opcao != opcao_voltar) {
            exibirMenuCrudProdutos("produto", "produtos");
            opcao = Input.getInt();

            switch (opcao) {
                case 1:
                    produtosController.getAllProdutos();
                    break;
                case 2:
                    produtosController.createProduto();
                    break;
                case 3:
                    produtosController.updateProduto();
                    break;
                case 4:
                    produtosController.deleteProduto();
                    break;
                case 5:
                    produtosController.getRelatorioProdutosFornecedor();
                    break;
                case 6:
                    produtosController.getRelatorioProdutosCategoria();
                case opcao_voltar:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void gerenciarVendas(VendasController vendasController) throws SQLException {
        int opcao = 0;
        final int opcao_voltar = 7;

        while (opcao != opcao_voltar) {
            exibirMenuCrudVendas("venda", "vendas");
            opcao = Input.getInt();

            switch (opcao) {
                case 1:
                    vendasController.getAllVendas();
                    break;
                case 2:
                    vendasController.createVenda();
                    break;
                case 3:
                    vendasController.updateVenda();
                    break;
                case 4:
                    System.out.println("Operação não implementada");
                    break;
                case 5:
                    vendasController.getRelatorioVendas();
                    break;
                case 6:
                    vendasController.getRelatorioVendasCategoria();
                    break;
                case opcao_voltar:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("---- Menu principal ----");
        System.out.printf("%d - Gerenciar categorias%n", CATEGORIAS_OPTION);
        System.out.printf("%d - Gerenciar fornecedores%n", FORNECEDORES_OPTION);
        System.out.printf("%d - Gerenciar vendedores%n", VENDEDORES_OPTION);
        System.out.printf("%d - Gerenciar produtos%n", PRODUTOS_OPTION);
        System.out.printf("%d - Gerenciar vendas%n", VENDAS_OPTION);
        System.out.printf("%d - Sair do sistema%n", QUIT_OPTION);
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuCrud(String nomeEntidadeSingular, String nomeEntidadePlural) {
        System.out.println("---- Gerenciando " + nomeEntidadePlural + " ----");
        System.out.println("1 - Listar " + nomeEntidadePlural);
        System.out.println("2 - Adicionar " + nomeEntidadeSingular);
        System.out.println("3 - Atualizar " + nomeEntidadeSingular);
        System.out.println("4 - Excluir " + nomeEntidadeSingular);
        System.out.println("5 - Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuCrudVendas(String nomeEntidadeSingular, String nomeEntidadePlural) {
        System.out.println("---- Gerenciando " + nomeEntidadePlural + " ----");
        System.out.println("1 - Listar " + nomeEntidadePlural);
        System.out.println("2 - Adicionar " + nomeEntidadeSingular);
        System.out.println("3 - Atualizar " + nomeEntidadeSingular);
        System.out.println("4 - Excluir " + nomeEntidadeSingular);
        System.out.println("5 - Gerar relatorio de " + nomeEntidadePlural + " por Vendedor");
        System.out.println("6 - Gerar relatorio de " + nomeEntidadePlural + " por Categoria");
        System.out.println("7 - Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuCrudProdutos(String nomeEntidadeSingular, String nomeEntidadePlural) {
        System.out.println("---- Gerenciando " + nomeEntidadePlural + " ----");
        System.out.println("1 - Listar " + nomeEntidadePlural);
        System.out.println("2 - Adicionar " + nomeEntidadeSingular);
        System.out.println("3 - Atualizar " + nomeEntidadeSingular);
        System.out.println("4 - Excluir " + nomeEntidadeSingular);
        System.out.println("5 - Gerar relatorio de " + nomeEntidadePlural + " por Fornecedor");
        System.out.println("6 - Gerar relatorio de " + nomeEntidadePlural + " por Categoria");
        System.out.println("7 - Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
    }
}