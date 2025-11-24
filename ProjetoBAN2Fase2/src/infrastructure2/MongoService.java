package infrastructure2;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoService {

    public MongoService() {
        Logger.getLogger( "org.mongodb.driver").setLevel(Level.SEVERE);
    }

    private final String CONNECTION_STRING = "mongodb://localhost:27017";
    private final String DATABASE_NAME = "lojaMusica";

    private MongoClient mongoClient = null;
    private MongoDatabase database = null;

    public MongoDatabase getDatabase() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);

                var codecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
                );

                database = mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(codecRegistry);


                System.out.println("Conectado com sucesso.");

            } catch (Exception ex) {
                handleError(ex);
            }
        }
        return database;
    }

    public void closeClient() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexão fechada.");
        }
    }

    private void handleError(Exception ex) {
        Logger
            .getLogger(MongoService.class.getName())
            .log(Level.SEVERE, "Erro ao conectar ao MongoDB", ex);

        System.exit(1);
    }
}