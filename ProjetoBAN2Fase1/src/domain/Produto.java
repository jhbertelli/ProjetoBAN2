package domain;

import java.sql.Date;

public class Produto {
    private final int id;
    private int idCategoria;
    private int idFornecedor;
    private String nome;
    private double preco;
    private int tempoGarantia;
    private Date dataRecebimento;
    private int quantidade;

    public Produto(int id, String nome, double preco, int tempoGarantia, Date dataRecebimento, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tempoGarantia = tempoGarantia;
        this.dataRecebimento = dataRecebimento;
        this.quantidade = quantidade;
    }

    public Produto(String nome, double preco, int tempoGarantia, Date dataRecebimento, int quantidade) {
        this(0, nome, preco, tempoGarantia, dataRecebimento, quantidade);
    }

    public int getId() {
        return id;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getTempoGarantia() {
        return tempoGarantia;
    }

    public void setTempoGarantia(int tempoGarantia) {
        this.tempoGarantia = tempoGarantia;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void decrementarQuantidade() {
        quantidade--;
    }
}
