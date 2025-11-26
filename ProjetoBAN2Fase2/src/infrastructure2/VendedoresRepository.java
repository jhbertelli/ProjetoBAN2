package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import domain2.Fornecedor;
import domain2.Vendedor;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Filter;

public class VendedoresRepository {
    private final MongoCollection<Vendedor> vendedoresCollection;

    public VendedoresRepository(MongoDatabase database) {
        vendedoresCollection = database.getCollection("vendedores", Vendedor.class);
    }

    public void createVendedor(Vendedor vendedor) {
        vendedoresCollection.insertOne(vendedor);
    }

    public void deleteVendedor(int id) {
        Bson filter = Filters.eq("_id", id);
        vendedoresCollection.deleteOne(filter);
    }

    public ArrayList<Vendedor> getAllVendedores() {
        return vendedoresCollection.find().into(new ArrayList<>());
    }

    public void updateVendedor(Vendedor vendedor) {
        //nome=?, endereco=?, telefone=?, email=? WHERE id_vendedor=?"
        Bson filter = Filters.eq("_id", vendedor.getId());
        vendedoresCollection.replaceOne(filter, vendedor);
    }

    public int getHighestId() {
        Vendedor vendedorMaiorID = vendedoresCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        return vendedorMaiorID == null ? 0 : vendedorMaiorID.getId();
    }
}
