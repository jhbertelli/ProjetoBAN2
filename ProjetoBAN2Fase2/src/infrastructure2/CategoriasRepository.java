package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import domain2.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class CategoriasRepository {
    private final MongoCollection<Categoria> categoriasCollection;

    public CategoriasRepository(MongoDatabase database) {
        categoriasCollection = database.getCollection("categorias", Categoria.class);
    }

    public void createCategoria(Categoria categoria) {
        categoriasCollection.insertOne(categoria);
    }

    public void deleteCategoria(int id) {
//        PreparedStatement st = connection.prepareStatement(
//            "DELETE FROM categorias where id_categoria = ?"
//        );
//
//        st.setInt(1, id);
//
//        st.execute();
//        st.close();
    }

    public ArrayList<Categoria> getAllCategorias() {
        return new ArrayList<>();
//        Statement st = connection.createStatement();
//        ArrayList<Categoria> categorias = new ArrayList<>();
//
//        ResultSet result = st.executeQuery(
//            "SELECT * FROM categorias ORDER BY id_categoria"
//        );
//
//        while (result.next()) {
//            categorias.add(
//                new Categoria(result.getInt(1), result.getString(2))
//            );
//        }
//
//        return categorias;
    }

    public void updateCategoria(Categoria categoria) {
//        PreparedStatement st = connection.prepareStatement(
//            "UPDATE categorias SET nome=? WHERE id_categoria=?"
//        );
//
//        st.setString(1, categoria.getNome());
//        st.setInt(2, categoria.getId());
//
//        st.execute();
//        st.close();
    }
}
