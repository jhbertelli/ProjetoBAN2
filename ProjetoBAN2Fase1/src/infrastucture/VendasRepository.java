package infrastucture;

import domain.Venda;

import java.sql.*;
import java.util.ArrayList;

public class VendasRepository {
    private final Connection connection;

    public VendasRepository(Connection connection) {
        this.connection = connection;
    }

    public void createVenda(Venda venda) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO vendas (id_vendedor) VALUES (?)");

        if (venda.getIdVendedor() == null)
            st.setNull(1, Types.INTEGER);
        else
            st.setInt(1, venda.getIdVendedor());

        st.execute();

        for (var vendaProduto : venda.getVendaProdutos()) {
            st = connection.prepareStatement(
                "INSERT INTO venda_produto (id_produto, id_venda, quantidade_vendida) VALUES (?,?,?)"
            );

            st.setInt(1, vendaProduto.getIdProduto());
            st.setInt(2, vendaProduto.getIdVenda());
            st.setInt(3, vendaProduto.getQuantidadeVendida());

            st.execute();
        }

        st.execute();
    }

    public int getUltimoIdVenda() throws SQLException {
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

//    public ArrayList<Venda> getAllVendas() throws SQLException {
//        Statement st = connection.createStatement();
//        ArrayList<Venda> produtos = new ArrayList<>();
//
//        ResultSet result = st.executeQuery(
//            "SELECT id_produto, nome, preco, tempo_garantia, data_recebimento, quantidade FROM produtos ORDER BY id_produto"
//        );
//
//        while (result.next()) {
//            produtos.add(
//                new Venda(
//                    result.getInt(1),
//                    result.getString(2),
//                    result.getDouble(3),
//                    result.getInt(4),
//                    result.getDate(5),
//                    result.getInt(6)
//                )
//            );
//        }
//
//        return produtos;
//    }
//
//    public void updateVenda(Venda produto) throws SQLException {
//        PreparedStatement st = connection.prepareStatement(
//            "UPDATE produtos SET id_categoria=?, id_fornecedor=?, nome=?, preco=?, tempo_garantia=?, data_recebimento=?, quantidade=? WHERE id_produto=?"
//        );
//
//        st.setInt(1, produto.getIdCategoria());
//        st.setInt(2, produto.getIdFornecedor());
//        st.setString(3, produto.getNome());
//        st.setDouble(4, produto.getPreco());
//        st.setInt(5, produto.getTempoGarantia());
//        st.setDate(6, produto.getDataRecebimento());
//        st.setInt(7, produto.getQuantidade());
//        st.setInt(8, produto.getId());
//
//        st.execute();
//        st.close();
//    }
}
