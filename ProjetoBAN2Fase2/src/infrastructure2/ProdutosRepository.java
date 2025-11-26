package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import domain2.Categoria;
import domain2.Fornecedor;
import domain2.Produto;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Filter;

public class ProdutosRepository {
    private final MongoCollection<Produto> produtosCollection;

    public ProdutosRepository(MongoDatabase database) {
        produtosCollection = database.getCollection("produtos", Produto.class);
    }

    public void createProduto(Produto produto) {
        produtosCollection.insertOne(produto);
    }

    public void deleteProduto(int id) {
        Bson filter = Filters.eq("_id", id);
        produtosCollection.deleteOne(filter);
    }

    public ArrayList<Produto> getById(int id) {
        Bson filter = Filters.eq("_id", id);
        return produtosCollection.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Produto> getAllProdutos() {
        return produtosCollection.find().into(new ArrayList<>());
    }

    public void updateProduto(Produto produto) {
        Bson filter = Filters.eq("_id", produto.getId());
        produtosCollection.replaceOne(filter, produto);
    }

    public void decrementarQuantidade(int id, int quantidade) {
        Bson filter = Filters.eq("_id", id);
        Bson update = Updates.inc("quantidade", -quantidade);

        produtosCollection.updateOne(filter, update);
    }

    public void incrementarQuantidade(int id, int quantidade) {
        Bson filter = Filters.eq("_id", id);
        Bson update = Updates.inc("quantidade", quantidade);

        produtosCollection.updateOne(filter, update);
    }

    public ArrayList<Produto> getRelatorioProdutosFornecedor(int id) {
        Bson filter = Filters.eq("idFornecedor", id);
        return produtosCollection
                .find(filter)
                .into(new ArrayList<>());
    }

    public ArrayList<Produto> getRelatorioProdutosCategoria (int id) {
        Bson filter = Filters.eq("idCategoria", id);
        return produtosCollection
                .find(filter)
                .into(new ArrayList<>());
    }

    public int getHighestId() {
        Produto produtoMaiorId = produtosCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        return produtoMaiorId== null ? 0 : produtoMaiorId.getId();
    }
}