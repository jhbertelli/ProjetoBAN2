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
    }

    public ArrayList<Categoria> getById(int id) {
        Bson filter = Filters.eq("_id", id);
        return categoriasCollection.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Categoria> getAllCategorias() {
        return categoriasCollection.find().into(new ArrayList<>());
    }

    public void updateCategoria(Categoria categoria) {
        Bson filter = Filters.eq("_id", categoria.getId());
        categoriasCollection.replaceOne(filter, categoria);
    }

    public int getHighestId() {
        Categoria categoriaMaiorID = categoriasCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        return categoriaMaiorID == null ? 0 : categoriaMaiorID.getId();
    }
}
