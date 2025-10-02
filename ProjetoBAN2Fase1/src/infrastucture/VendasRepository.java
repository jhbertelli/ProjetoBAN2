package infrastucture;

import domain.Produto;
import domain.Venda;
import domain.Vendedor;

import java.sql.*;
import java.util.*;

public class VendasRepository {
    private final Connection connection;

    public VendasRepository(Connection connection) {
        this.connection = connection;
    }

    public void createVenda(Venda venda) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO vendas (id_vendedor) VALUES (?)");

        if (venda.getVendedor() == null)
            st.setNull(1, Types.INTEGER);
        else
            st.setInt(1, venda.getVendedor().getId());

        st.execute();

        for (var vendaProduto : venda.getVendaProdutos()) {
            st = connection.prepareStatement(
                "INSERT INTO venda_produto (id_produto, id_venda, quantidade_vendida) VALUES (?,?,?)"
            );

            st.setInt(1, vendaProduto.getProduto().getId());
            st.setInt(2, vendaProduto.getIdVenda());
            st.setInt(3, vendaProduto.getQuantidadeVendida());

            st.execute();
        }

        st.close();
    }

    public int getProximoIdVenda() throws SQLException {
        var statement = connection.createStatement();

        var result = statement
            .executeQuery("SELECT last_value FROM vendas_id_venda_seq");

        result.next();

        var proximoId = result.getInt(1);

        // o last_value começa com 1 mesmo se não há registros, e ao adicionar o primeiro registro se mantém o 1
        // assim. é feito essa condicional para garantir o valor do próximo ID como sendo o ID do último + 1
        if (proximoId == 1) {
            var resultQtdRegistros = statement.executeQuery("SELECT COUNT(*) FROM vendas");

            resultQtdRegistros.next();

            int qtdRegistros = resultQtdRegistros.getInt(1);

            if (qtdRegistros != 0) proximoId = 2;

            return proximoId;
        } else {
            proximoId++;
        }

        return proximoId;
    }

    public ArrayList<Venda> getAllVendas() throws SQLException {
        Statement st = connection.createStatement();
        ArrayList<Venda> vendas = new ArrayList<>();

        ResultSet result = st.executeQuery(
            "SELECT id_venda, vendedores.nome FROM vendas LEFT JOIN vendedores ON vendas.id_vendedor = vendedores.id_vendedor ORDER BY id_venda"
        );

        while (result.next()) {
            var venda = new Venda(result.getInt(1));

            var nomeVendedor = result.getString(2);

            if (nomeVendedor != null) {
                var vendedor = new Vendedor();
                vendedor.setNome(nomeVendedor);
                venda.setVendedor(vendedor);
            }

            PreparedStatement stVendaProduto = connection.prepareStatement(
                "SELECT vp.quantidade_vendida, p.nome, p.preco, p.id_produto FROM venda_produto vp JOIN produtos p ON vp.id_produto = p.id_produto WHERE id_venda = ?"
            );

            stVendaProduto.setInt(1, venda.getId());
            ResultSet resultVendaProduto = stVendaProduto.executeQuery();

            while (resultVendaProduto.next()) {
                int quantidadeVendida = resultVendaProduto.getInt(1);
                var produto = new Produto(resultVendaProduto.getInt(4));
                produto.setNome(resultVendaProduto.getString(2));
                produto.setPreco(resultVendaProduto.getDouble(3));

                venda.adicionarProduto(produto, quantidadeVendida);
            }

            vendas.add(venda);
            stVendaProduto.close();
        }

        return vendas;
    }

    public ArrayList<Venda> getRelatorioVendas(int id) throws SQLException {
        String sql ="SELECT " +
                    "ve.id_vendedor, " +
                    "ve.nome AS vendedor, " +
                    "v.id_venda, " +
                    "p.id_produto, " +
                    "p.nome AS produto, " +
                    "vp.quantidade_vendida, " +
                    "p.preco " +
                    "FROM vendas v " +
                    "JOIN vendedores ve ON v.id_vendedor = ve.id_vendedor " +
                    "JOIN venda_produto vp ON v.id_venda = vp.id_venda " +
                    "JOIN produtos p ON vp.id_produto = p.id_produto " +
                    "WHERE v.id_vendedor = ? " +
                    "ORDER BY v.id_venda";

        ArrayList<Venda> relatorioFinal = new ArrayList<>();
        Venda vendaAtual = null;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);

        ResultSet result = st.executeQuery();
        while (result.next()) {
            int idVendaDaLinha = result.getInt("id_venda");

            if(vendaAtual == null || vendaAtual.getId() != idVendaDaLinha) {
                vendaAtual = new Venda(idVendaDaLinha);

                int idVendedor = result.getInt("id_vendedor");
                String nomeVendedor = result.getString("vendedor");
                vendaAtual.setVendedor(new Vendedor(idVendedor, nomeVendedor, null, null, null));

                relatorioFinal.add(vendaAtual);
            }

            int idProduto = result.getInt("id_produto");
            String nomeProduto = result.getString("produto");
            double precoProduto = result.getDouble("preco");
            int quantidadeVendida = result.getInt("quantidade_vendida");

            Produto produto = new Produto(idProduto, nomeProduto, precoProduto, 0, null, 0);

            vendaAtual.adicionarProduto(produto, quantidadeVendida);
        }


        return  relatorioFinal;
    }

    public ArrayList<Venda> getRelatorioVendasCategoria(int id) throws SQLException {
        String sql = """
               SELECT
               ve.nome AS vendedor,
               c.nome AS categoria,
               v.id_venda,
               p.id_produto,
               p.nome AS produto,
               vp.quantidade_vendida,
               p.preco
               FROM vendas v
               LEFT JOIN vendedores ve on ve.id_vendedor=v.id_vendedor
               JOIN venda_produto vp on v.id_venda=vp.id_venda
               JOIN produtos p on vp.id_produto=p.id_produto
               JOIN categorias c on p.id_categoria=c.id_categoria
               WHERE c.id_categoria = ?
               ORDER BY v.id_venda""";

        ArrayList<Venda> relatorioFinal = new ArrayList<>();
        Venda vendaAtual = null;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,id);

        ResultSet result = st.executeQuery();
        while (result.next()) {
            int idVendaDaLinha = result.getInt("id_venda");

            if(vendaAtual == null || vendaAtual.getId() != idVendaDaLinha) {
                vendaAtual = new Venda(idVendaDaLinha);

                String nomeVendedor = result.getString("vendedor");

                if (nomeVendedor != null) {
                    var vendedor = new Vendedor();
                    vendedor.setNome(nomeVendedor);
                    vendaAtual.setVendedor(vendedor);
                }

                relatorioFinal.add(vendaAtual);
            }

            int idProduto = result.getInt("id_produto");
            String nomeProduto = result.getString("produto");
            double precoProduto = result.getDouble("preco");
            int quantidadeVendida = result.getInt("quantidade_vendida");

            Produto produto = new Produto(idProduto, nomeProduto, precoProduto, 0, null, 0);

            vendaAtual.adicionarProduto(produto, quantidadeVendida);
        }

        return relatorioFinal;
    }

    public void updateVenda(Venda venda) throws SQLException {
        PreparedStatement st = connection.prepareStatement("UPDATE vendas SET id_vendedor = ? WHERE id_venda = ?");

        if (venda.getVendedor() == null) {
            st.setNull(1, Types.INTEGER);
        } else {
            st.setInt(1, venda.getVendedor().getId());
        }
        st.setInt(2, venda.getId());
        st.executeUpdate();

        st = connection.prepareStatement("DELETE FROM venda_produto WHERE id_venda = ?");
        st.setInt(1, venda.getId());
        st.execute();

        for (var vendaProduto : venda.getVendaProdutos()) {
            st = connection.prepareStatement(
                "INSERT INTO venda_produto (id_produto, id_venda, quantidade_vendida) VALUES (?,?,?)"
            );
            st.setInt(1, vendaProduto.getProduto().getId());
            st.setInt(2, vendaProduto.getIdVenda());
            st.setInt(3, vendaProduto.getQuantidadeVendida());
            st.execute();
        }

        st.close();
    }

    public void deleteVenda(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM venda_produto WHERE id_venda = ?");
        st.setInt(1, id);
        st.execute();

        st = connection.prepareStatement("DELETE FROM vendas WHERE id_venda = ?");
        st.setInt(1, id);
        st.execute();
        st.close();
    }
}
