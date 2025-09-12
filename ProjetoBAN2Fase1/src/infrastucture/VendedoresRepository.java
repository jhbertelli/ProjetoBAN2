package infrastucture;

import domain.Vendedor;

import java.sql.*;
import java.util.ArrayList;

public class VendedoresRepository {
    private final Connection connection;

    public VendedoresRepository(Connection connection) {
        this.connection = connection;
    }

    public void createVendedor(Vendedor vendedor) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "INSERT INTO vendedores (nome, endereco, telefone, email) VALUES (?,?,?,?)"
        );

        st.setString(1, vendedor.getNome());
        st.setString(2, vendedor.getEndereco());
        st.setString(3, vendedor.getTelefone());
        st.setString(4, vendedor.getEmail());

        st.execute();
        st.close();
    }

    public void deleteVendedor(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "DELETE FROM vendedores where id_vendedor = ?"
        );

        st.setInt(1, id);

        st.execute();
        st.close();
    }

    public ArrayList<Vendedor> getAllVendedores() throws SQLException {
        Statement st = connection.createStatement();
        ArrayList<Vendedor> vendedores = new ArrayList<>();

        ResultSet result = st.executeQuery(
            "SELECT * FROM vendedores ORDER BY id_vendedor"
        );

        while (result.next()) {
            vendedores.add(
                new Vendedor(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
                )
            );
        }

        return vendedores;
    }

    public void updateVendedor(Vendedor vendedor) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "UPDATE vendedores SET nome=?, endereco=?, telefone=?, email=? WHERE id_vendedor=?"
        );

        st.setString(1, vendedor.getNome());
        st.setString(2, vendedor.getEndereco());
        st.setString(3, vendedor.getTelefone());
        st.setString(4, vendedor.getEmail());
        st.setInt(5, vendedor.getId());

        st.execute();
        st.close();
    }
}
