package infrastucture;

import domain.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class CategoriasRepository {
    private final Connection connection;

    public CategoriasRepository(Connection connection) {
        this.connection = connection;
    }

    public void createCategoria(Categoria categoria) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "INSERT INTO categorias (nome) VALUES (?)"
        );

        st.setString(1, categoria.getNome());

        st.execute();
        st.close();
    }

    public void deleteCategoria(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "DELETE FROM categorias where id_categoria = ?"
        );

        st.setInt(1, id);

        st.execute();
        st.close();
    }

    public ArrayList<Categoria> getAllCategorias() throws SQLException {
        Statement st = connection.createStatement();
        ArrayList<Categoria> categorias = new ArrayList<>();

        ResultSet result = st.executeQuery(
            "SELECT * FROM categorias ORDER BY id_categoria"
        );

        while (result.next()) {
            categorias.add(
                new Categoria(result.getInt(1), result.getString(2))
            );
        }

        return categorias;
    }

    public void updateCategoria(Categoria categoria) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
            "UPDATE categorias SET nome=? WHERE id_categoria=?"
        );

        st.setString(1, categoria.getNome());
        st.setInt(2, categoria.getId());

        st.execute();
        st.close();
    }
}
