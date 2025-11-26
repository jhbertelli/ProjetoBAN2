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
//        fornecedoresCollection.replaceOne(filter, fornecedor);
//        PreparedStatement st = connection.prepareStatement(
//            "UPDATE fornecedores SET endereco=?, telefone=?, nome=?, nome_fantasia=?, documento=?, email_contato=? WHERE id_fornecedor=?"
//        );
//
//        st.setString(1, fornecedor.getEndereco());
//        st.setString(2, fornecedor.getTelefone());
//        st.setString(3, fornecedor.getNome());
//        st.setString(4, fornecedor.getNomeFantasia());
//        st.setString(5, fornecedor.getDocumento());
//        st.setString(6, fornecedor.getEmail());
//        st.setInt(7, fornecedor.getId());
//
//        st.execute();
//        st.close();
    }

    public int getHighestId() {
        Fornecedor fornecedorMaiorID = fornecedoresCollection
                .find()
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();

        if ( fornecedorMaiorID == null) {
            return 0;
        }
        return fornecedorMaiorID.getId();
    }

}