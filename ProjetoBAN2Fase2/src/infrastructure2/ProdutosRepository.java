package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;
import domain2.Categoria;
import domain2.Fornecedor;
import domain2.Produto;
import domain2.Venda;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class ProdutosRepository {
    private final MongoCollection<Produto> produtosCollection;
    private final MongoCollection<Venda> vendasCollection;

    public ProdutosRepository(MongoDatabase database) {
        produtosCollection = database.getCollection("produtos", Produto.class);
        vendasCollection = database.getCollection("vendas", Venda.class);
    }

    public void createProduto(Produto produto) {
        produtosCollection.insertOne(produto);
    }

    public void deleteProduto(int id) {
        long count = vendasCollection.countDocuments(Filters.eq("vendaProdutos.produto._id", id));

        if (count > 0) {
            throw new IllegalStateException("ERRO: Não é possível excluir este produto pois ele já foi vendido e consta em registros de vendas.");
        }

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

        Bson filterVendas = Filters.eq("vendaProdutos.produto._id", produto.getId());
        Bson updateVebdas = Updates.set("vendaProdutos.$[elem].produto", produto);

        UpdateOptions options = new UpdateOptions().arrayFilters(
                List.of(Filters.eq("elem.produto._id", produto.getId()))
        );

        vendasCollection.updateMany(filterVendas,updateVebdas,options);
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