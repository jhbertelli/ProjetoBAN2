package infrastructure2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import domain2.Produto;
import domain2.Venda;
import domain2.Vendedor;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.logging.Filter;

public class VendasRepository {
    private final MongoCollection<Venda> vendasCollection;

    public VendasRepository(MongoDatabase database) {
        vendasCollection = database.getCollection("vendas", Venda.class);
    }

    public void createVenda(Venda venda) {
        vendasCollection.insertOne(venda);
    }

    public void updateVenda(Venda venda) {
        Bson filter = Filters.eq("_id", venda.getId());
        vendasCollection.replaceOne(filter, venda);
    }

    public void deleteVenda(int id) {
        Bson filter = Filters.eq("_id", id);
        vendasCollection.deleteOne(filter);
    }

    public ArrayList<Venda> getAllVendas() {
        return vendasCollection.find().into(new ArrayList<>());
    }

    public int getProximoIdVenda() {
        Venda ultima = vendasCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        return ultima == null ? 0 : ultima.getId();
    }

    public ArrayList<Venda> getRelatorioVendas(int id) {
        Bson filter = Filters.eq("vendedor._id", id);
        return vendasCollection.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Venda> getRelatorioVendasCategoria(int id) {
        Bson filter = Filters.eq("vendaProdutos.produto.idCategoria", id);
        return vendasCollection.find(filter).into(new ArrayList<>());
    }
}
