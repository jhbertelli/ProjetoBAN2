Instruções

.jar do postgres foi baixado pelo seguinte link: https://jdbc.postgresql.org/download/postgresql-42.7.4.jar

Utilizamos o IntelliJ para fazer o trabalho, portanto para que funcione corretamente é necessário fazer os seguintes passos: Já dentro do projeto > File > Project Structure > Libraries > Add (primeiro +) > Java > Informar caminho do arquivo .jar baixado > Apply > Ok

Para a conexão do banco de dados, é necessário usar os seguintes dados:
- Usuário: postgres
- Senha: udesc
- Nome do banco: lojaMusica
- Porta: 5432 (padrão)

Há um arquivo "database_dump.sql" que contém o banco de dados vazio e um arquivo "database_dump_populado.sql" que gera o banco com alguns registros já criados. Assim, basta executar um deles no pgadmin com a conexão acima para poder executar o projeto.
