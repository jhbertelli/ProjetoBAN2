package domain2;

public class Categoria {
    private int id;
    private String nome;

    public Categoria() {
    }

    public Categoria(String nome) {
        this(0, nome);
    }

    public Categoria(int id, String nome) {
        this.nome = nome;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s", id, nome);
    }
}
