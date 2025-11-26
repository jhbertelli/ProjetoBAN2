package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import domain2.Categoria;
import domain2.Fornecedor;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class FornecedoresRepository {
    private final MongoCollection<Fornecedor> fornecedoresCollection;

    public FornecedoresRepository(MongoDatabase database) {
        fornecedoresCollection = database.getCollection("fornecedores", Fornecedor.class);
    }

    public void createFornecedor(Fornecedor fornecedor) {
        fornecedoresCollection.insertOne(fornecedor);
    }

    public void deleteFornecedor(int id) {
        Bson filter = Filters.eq("_id", id);
        fornecedoresCollection.deleteOne(filter);
    }



    public ArrayList<Fornecedor> getAllFornecedores() {
        return fornecedoresCollection.find().into(new ArrayList<>());
    }

    public void updateFornecedor(Fornecedor fornecedor) {
        Bson filter = Filters.eq("_id", fornecedor.getId());

        Bson updates = Updates.combine(
                Updates.set("nome", fornecedor.getNome()),
                Updates.set("endereco", fornecedor.getEndereco()),
                Updates.set("telefone", fornecedor.getTelefone()),
                Updates.set("email", fornecedor.getEmail()),
                Updates.set("documento", fornecedor.getDocumento()),
                Updates.set("nomeFantasia", fornecedor.getNomeFantasia())
        );

        fornecedoresCollection.updateOne(filter, updates);
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