package domain;

public class Categoria {
    private final int id;
    private String nome;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
