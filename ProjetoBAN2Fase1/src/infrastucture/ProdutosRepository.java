package infrastucture;

import domain.Categoria;
import domain.Fornecedor;
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
        ArrayList<Produto> aProdutos = new ArrayList<>();

        ResultSet result = st.executeQuery(
            "SELECT " +
                    "p.id_produto, " +
                    "c.nome as categoria_nome, " +
                    "p.nome, " +
                    "p.preco, " +
                    "p.tempo_garantia, " +
                    "p.data_recebimento, " +
                    "p.quantidade, " +
                    "f.nome as fornecedor_nome " +
                    "FROM produtos p " +
                    "NATURAL JOIN categorias c " +
                    "NATURAL JOIN fornecedores f " +
                    "ORDER BY id_produto"
        );

        while (result.next()) {
            Produto p = new Produto(
                    result.getInt(1),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5),
                    result.getDate(6),
                    result.getInt(7)
            );

            Categoria c = new Categoria(
                    result.getString("categoria_nome")
            );

            Fornecedor f = new Fornecedor(
                    result.getString("fornecedor_nome")
            );

            //p.setIdCategoria(c.getId());
            //p.setIdFornecedor(f.getId());
            p.setNomeCategoria(c.getNome());
            p.setNomeFornecedor(f.getNome());

            aProdutos.add(p);
        }

        return aProdutos;
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
