package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import domain2.Categoria;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Filter;

public class CategoriasRepository {
    private final MongoCollection<Categoria> categoriasCollection;

    public CategoriasRepository(MongoDatabase database) {
        categoriasCollection = database.getCollection("categorias", Categoria.class);
    }

    public void createCategoria(Categoria categoria) {
        categoriasCollection.insertOne(categoria);
    }

    public void deleteCategoria(int id) {
        Bson filter = Filters.eq("_id", id);
        categoriasCollection.deleteOne(filter);
//        PreparedStatement st = connection.prepareStatement(
//            "DELETE FROM categorias where id_categoria = ?"
//        );
//
//        st.setInt(1, id);
//
//        st.execute();
//        st.close();
    }

    public ArrayList<Categoria> getById(int id) {
        Bson filter = Filters.eq("_id", id);
        return categoriasCollection.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Categoria> getAllCategorias() {
        return categoriasCollection.find().into(new ArrayList<>());
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
        Bson filter = Filters.eq("_id", categoria.getId());
        categoriasCollection.replaceOne(filter, categoria);
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

    public int getHighestId() {
        Categoria categoriaMaiorID = categoriasCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        if ( categoriaMaiorID == null) {
            return 0;
        }
        return categoriaMaiorID.getId();
    }
}
