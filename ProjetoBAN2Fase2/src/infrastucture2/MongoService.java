package infrastucture2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoService {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "lojaMusica";

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);

                database = mongoClient.getDatabase(DATABASE_NAME);

                System.out.println("Conectado com sucesso.");

            } catch (Exception ex) {
                handleError(ex);
            }
        }
        return database;
    }

    public static void closeClient() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexão fechada.");
        }
    }

    private static void handleError(Exception ex) {
        Logger
                .getLogger(MongoService.class.getName())
                .log(Level.SEVERE, "Erro ao conectar ao MongoDB", ex);

        System.exit(1);
    }
}