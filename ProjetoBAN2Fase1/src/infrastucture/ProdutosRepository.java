package infrastucture;

import domain.Produto;

import java.sql.*;
import java.util.ArrayList;

public class ProdutosRepository {
    private final Connection connection;

    public ProdutosRepository(Connection connection) {
        this.connection = connection;
    }

    public void createProduto(Produto produto) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            """
                INSERT INTO produtos (
                    id_categoria,
                    id_fornecedor,
                    nome,
                    preco,
                    tempo_garantia,
                    data_recebimento,
                    quantidade
                ) VALUES (?,?,?,?,?,?,?)"""
        );

        st.setInt(1, produto.getIdCategoria());
        st.setInt(2, produto.getIdFornecedor());
        st.setString(3, produto.getNome());
        st.setDouble(4, produto.getPreco());
        st.setInt(5, produto.getTempoGarantia());
        st.setDate(6, produto.getDataRecebimento());
        st.setInt(7, produto.getQuantidade());

        st.execute();
        st.close();
    }

    public void deleteProduto(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "DELETE FROM produtos where id_produto = ?"
        );

        st.setInt(1, id);

        st.execute();
        st.close();
    }

    public ArrayList<Produto> getAllProdutos() throws SQLException {
        Statement st = connection.createStatement();
        ArrayList<Produto> produtos = new ArrayList<>();

        ResultSet result = st.executeQuery(
            "SELECT id_produto, nome, preco, tempo_garantia, data_recebimento, quantidade FROM produtos ORDER BY id_produto"
        );

        while (result.next()) {
            produtos.add(
                new Produto(
                    result.getInt(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4),
                    result.getDate(5),
                    result.getInt(6)
                )
            );
        }

        return produtos;
    }

    public void updateProduto(Produto produto) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "UPDATE produtos SET id_categoria=?, id_fornecedor=?, nome=?, preco=?, tempo_garantia=?, data_recebimento=?, quantidade=? WHERE id_produto=?"
        );

        st.setInt(1, produto.getIdCategoria());
        st.setInt(2, produto.getIdFornecedor());
        st.setString(3, produto.getNome());
        st.setDouble(4, produto.getPreco());
        st.setInt(5, produto.getTempoGarantia());
        st.setDate(6, produto.getDataRecebimento());
        st.setInt(7, produto.getQuantidade());
        st.setInt(8, produto.getId());

        st.execute();
        st.close();
    }
}
