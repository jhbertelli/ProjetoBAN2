package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import domain2.Categoria;
import domain2.Produto;
import domain2.Venda;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

public class CategoriasRepository {
    private final MongoCollection<Categoria> categoriasCollection;
    private final MongoCollection<Produto> produtosCollection;
    private final MongoCollection<Venda> vendasCollection;

    public CategoriasRepository(MongoDatabase database) {
        categoriasCollection = database.getCollection("categorias", Categoria.class);
        produtosCollection = database.getCollection("produtos", Produto.class);
        vendasCollection = database.getCollection("vendas", Venda.class);
    }

    public void createCategoria(Categoria categoria) {
        categoriasCollection.insertOne(categoria);
    }

    public void deleteCategoria(int id) {
        long count = produtosCollection.countDocuments(Filters.eq("idCategoria", id));

        if (count > 0) {
            throw new IllegalStateException("ERRO: Não é possível excluir esta categoria pois existem produtos vinculados a ela.");
        }

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

        Bson filterProdutos = Filters.eq("idCategoria", categoria.getId());
        Bson updateProdutos = Updates.set("nomeCategoria", categoria.getNome());
        produtosCollection.updateMany(filterProdutos, updateProdutos);

        Bson filterVendas = Filters.eq("vendaProdutos.produto.idCategoria", categoria.getId());
        Bson updateVendas = Updates.set("vendaProdutos.$[elem].produto.nomeCategoria", categoria.getNome());

        UpdateOptions options = new UpdateOptions().arrayFilters(
                List.of(Filters.eq("elem.produto.idCategoria", categoria.getId()))
        );

        vendasCollection.updateMany(filterVendas,updateVendas,options);
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
