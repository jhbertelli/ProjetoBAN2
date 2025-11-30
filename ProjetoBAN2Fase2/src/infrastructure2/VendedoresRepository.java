package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import domain2.Fornecedor;
import domain2.Vendedor;
import domain2.Venda;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Filter;

public class VendedoresRepository {
    private final MongoCollection<Vendedor> vendedoresCollection;
    private final MongoCollection<Venda> vendasCollection;

    public VendedoresRepository(MongoDatabase database) {
        vendedoresCollection = database.getCollection("vendedores", Vendedor.class);
        vendasCollection = database.getCollection("vendas", Venda.class);
    }

    public void createVendedor(Vendedor vendedor) {
        vendedoresCollection.insertOne(vendedor);
    }

    public void deleteVendedor(int id) {
        long count = vendasCollection.countDocuments(Filters.eq("vendedor._id", id));

        if (count > 0) {
            throw new IllegalStateException("ERRO: Não é possível excluir este vendedor pois ele possui vendas registradas no sistema.");
        }

        Bson filter = Filters.eq("_id", id);
        vendedoresCollection.deleteOne(filter);
    }

    public ArrayList<Vendedor> getAllVendedores() {
        return vendedoresCollection.find().into(new ArrayList<>());
    }

    public void updateVendedor(Vendedor vendedor) {
        Bson filter = Filters.eq("_id", vendedor.getId());
        vendedoresCollection.replaceOne(filter, vendedor);

        Bson filterVendas = Filters.eq("vendedor._id", vendedor.getId());
        Bson updateVendas = Updates.set("vendedor", vendedor);

        vendasCollection.updateMany(filterVendas, updateVendas);
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
