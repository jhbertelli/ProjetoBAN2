package domain2;

public class Vendedor {
    private final int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public Vendedor() {
        this.id = 0;
    }

    public Vendedor(int id) {
        this.id = id;
    }

    public Vendedor(String nome, String endereco, String telefone, String email) {
        this(0, nome, endereco, telefone, email);
    }

    public Vendedor(int id, String nome, String endereco, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %s | Endereço: %s | Telefone: %s | E-mail: %s",
            id,
            nome,
            endereco,
            telefone,
            email
        );
    }
}
