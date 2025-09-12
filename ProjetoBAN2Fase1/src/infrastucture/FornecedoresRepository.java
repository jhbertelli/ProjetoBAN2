package infrastucture;

import domain.Fornecedor;

import java.sql.*;
import java.util.ArrayList;

public class FornecedoresRepository {
    private final Connection connection;

    public FornecedoresRepository(Connection connection) {
        this.connection = connection;
    }

    public void createFornecedor(Fornecedor fornecedor) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO fornecedores (endereco, telefone, nome, nome_fantasia, documento, email_contato) VALUES (?,?,?,?,?,?)"
        );


        st.setString(1, fornecedor.getEndereco());
        st.setString(2, fornecedor.getTelefone());
        st.setString(3, fornecedor.getNome());
        st.setString(4, fornecedor.getNomeFantasia());
        st.setString(5, fornecedor.getDocumento());
        st.setString(6, fornecedor.getEmail());

        st.execute();
        st.close();
    }

    public void deleteFornecedor(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM fornecedores WHERE id_fornecedor = ?"
        );

        st.setInt(1, id);

        st.execute();
        st.close();
    }

    public ArrayList<Fornecedor> getAllFornecedores() throws SQLException {
        Statement st = connection.createStatement();
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();

        ResultSet result = st.executeQuery(
                "SELECT * FROM fornecedores ORDER BY id_fornecedor"
        );

        while (result.next()) {
            fornecedores.add(
                    new Fornecedor(
                            result.getInt(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getString(7)
                    )
            );
        }

        return fornecedores;
    }

    public void updateFornecedor(Fornecedor fornecedor) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE fornecedores SET endereco=?, telefone=?, nome=?, nome_fantasia=?, documento=?, email_contato=?"
        );

        st.setString(1, fornecedor.getEndereco());
        st.setString(2, fornecedor.getTelefone());
        st.setString(3, fornecedor.getNome());
        st.setString(4, fornecedor.getNomeFantasia());
        st.setString(5, fornecedor.getDocumento());
        st.setString(6, fornecedor.getEmail());

        st.execute();
        st.close();
    }
}