package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;
import domain2.Categoria;
import domain2.Fornecedor;
import domain2.Produto;
import domain2.Venda;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class FornecedoresRepository {
    private final MongoCollection<Fornecedor> fornecedoresCollection;
    private final MongoCollection<Produto> produtosCollection;
    private final MongoCollection<Venda> vendasCollection;

    public FornecedoresRepository(MongoDatabase database) {
        fornecedoresCollection = database.getCollection("fornecedores", Fornecedor.class);
        produtosCollection = database.getCollection("produtos", Produto.class);
        vendasCollection = database.getCollection("vendas", Venda.class);
    }

    public void createFornecedor(Fornecedor fornecedor) {
        fornecedoresCollection.insertOne(fornecedor);
    }

    public void deleteFornecedor(int id) {
        long count = produtosCollection.countDocuments(Filters.eq("idFornecedor", id));

        if (count > 0) {
            throw new IllegalStateException("ERRO: Não é possível excluir este fornecedor pois existem produtos vinculados a ele.");
        }

        Bson filter = Filters.eq("_id", id);
        fornecedoresCollection.deleteOne(filter);
    }



    public ArrayList<Fornecedor> getAllFornecedores() {
        return fornecedoresCollection.find().into(new ArrayList<>());
    }

    public void updateFornecedor(Fornecedor fornecedor) {
        Bson filter = Filters.eq("_id", fornecedor.getId());


        // O update aqui está desse jeito para mostrar outra forma de fazer update.
        Bson updates = Updates.combine(
                Updates.set("nome", fornecedor.getNome()),
                Updates.set("endereco", fornecedor.getEndereco()),
                Updates.set("telefone", fornecedor.getTelefone()),
                Updates.set("email", fornecedor.getEmail()),
                Updates.set("documento", fornecedor.getDocumento()),
                Updates.set("nomeFantasia", fornecedor.getNomeFantasia())
        );

        fornecedoresCollection.updateOne(filter, updates);

        Bson filterProdutos = Filters.eq("idFornecedor", fornecedor.getId());
        Bson updateProdutos = Updates.set("nomeFornecedor", fornecedor.getNome());
        produtosCollection.updateMany(filterProdutos, updateProdutos);

        Bson filterVendas = Filters.eq("vendaProdutos.produto.idFornecedor", fornecedor.getId());
        Bson updateVendas = Updates.set("vendaProdutos.$[elem].produto.nomeFornecedor", fornecedor.getNome());

        UpdateOptions options = new UpdateOptions().arrayFilters(
                List.of(Filters.eq("elem.produto.idFornecedor", fornecedor.getId()))
        );

        vendasCollection.updateMany(filterVendas, updateVendas, options);
    }

    public int getHighestId() {
        Fornecedor fornecedorMaiorID = fornecedoresCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        return fornecedorMaiorID == null ? 0 : fornecedorMaiorID.getId();
    }

}