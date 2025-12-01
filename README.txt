Instruções

.jar do postgres foi baixado pelo seguinte link: https://jdbc.postgresql.org/download/postgresql-42.7.4.jar

Utilizamos o IntelliJ para fazer o trabalho, portanto para que funcione corretamente é necessário fazer os seguintes passos: Já dentro do projeto > File > Project Structure > Libraries > Add (primeiro +) > Java > Informar caminho do arquivo .jar baixado > Apply > Ok

Para a conexão do banco de dados, é necessário usar os seguintes dados:
- Usuário: postgres
- Senha: udesc
- Nome do banco: lojaMusica
- Porta: 5432 (padrão)

Há um arquivo "database_dump.sql" que contém o banco de dados vazio e um arquivo "database_dump_populado.sql" que gera o banco com alguns registros já criados. Assim, basta executar um deles no pgadmin com a conexão acima para poder executar o projeto.

################## FASE 2 ##################

** IMPORTANTE **

Para a execução do projeto é necessário abrir a pasta 'ProjetoBAN2Fase2' com o IntelliJ, caso não dê certo, também é possível abrir o projeto inteiro com o IntelliJ e excluir a classe 'Main' dentro da pasta 'ProjetoBAN2Fase1', deixando apenas a 'Main' do 'ProjetoBAN2Fase2'.

Dentro do IntelliJ, com o projeto aberto: File > Project Structure > Project Settings > Libraries > Add > Selecionar o driver .jar do MongoDB.

link para download do driver: https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver/3.12.11 (em files só clicar onde está escrito 'jar').
*também foi anexado o .jar no moodle, caso necessário.


****************


link para fazer o download local do MongoDB (tem uma checkbox para fazer junto o download do MongoDB Compass, interface gráfica para visualização dos bancos, semelhante ao PgAdmin) : https://www.mongodb.com/try/download/community

O MongoDB funciona de forma um pouco diferente, ele cria as collections (tabelas) e o próprio banco de dados no momento em que se tenta fazer uma inserção, não sendo necessária a criação prévia de uma collection. Entretanto, por questões de costume, utilizamos o MongoDB Compass para criar o banco e as tabelas.

link para download do Compass (caso não tenha marcado a caixinha no download local do MongoDB): https://www.mongodb.com/try/download/compass


Criamos o banco da seguinte forma (utilizando o Compass):

- Criar uma nova conexão (utilizamos os valores padrão, mas caso necessário utilizamos a porta 27017);
- Clicar no '+' ao lado da connection (create database);
- Informar o nome do banco e o nome da primeira collection (necessário informar por o mongo cria a collection e o banco conforme o usuário faz inserções);
- O nome do banco de dados ficou como: lojaMusica;
- Os nomes da collection ficaram da seguinte forma: categorias, fornecedores, produtos, vendas, vendedores;

Informações importantes/Diferenças da primeira parte:

- Da forma que o MongoDB funciona, não é necessário/possível ter uma tabela 'vendaProduto' que nem possuíamos na primeira parte do trabalho usando PostgreSQL. Logo, para substituir essa tabela, na collection de vendas para cada venda adicionamos um campo que é de um array de produtos;

- No PostgreSQL as Foreign Keys (FKs) impediam a exclusão, por exemplo, de um Vendedor associado a uma ou várias vendas. Como no MongoDB não possuímos essas Constraints nativamente implementadas, foi necessária a 'criação' delas dentro das classes 'Repository', exemplo:

    public void deleteProduto(int id) {
        long count = vendasCollection.countDocuments(Filters.eq("vendaProdutos.produto._id", id));

        if (count > 0) {
            throw new IllegalStateException("ERRO: Não é possível excluir este produto pois ele já foi vendido e consta em registros de vendas.");
        }

        Bson filter = Filters.eq("_id", id);
        produtosCollection.deleteOne(filter);
    }


- Também foi necessário mexer nos métodos de update dentro das classes 'Repository', para propagar as mudanças feitas para as ocorrências em outras collections, exemplo:

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

*As classes dentro do domínio permaneceram praticamente iguais às classes da primeira parte, adicionando ou mudando poucas coisas como: métodos construtores vazios e remoção de 'final' dos atributos.
