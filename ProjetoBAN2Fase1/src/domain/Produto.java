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
    private String nomeCategoria;
    private String nomeFornecedor;

    public Produto() {
        this.id = 0;
    }

    public Produto(int id, String nome, double preco, int tempoGarantia, Date dataRecebimento, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tempoGarantia = tempoGarantia;
        this.dataRecebimento = dataRecebimento;
        this.quantidade = quantidade;
    }

    public Produto(int id) {
        this.id = id;
    }

    public Produto(int id, String nome, int idCategoria, String nomeCategoria, double preco, int tempoGarantia, Date dataRecebimento, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
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

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
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

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %s | Categoria: %s | Preço: R$%.2f | Quantidade: %d | Tempo de Garantia: %d meses | Data de Recebimento: %s | Fornecedor: %s",
            id,
            nome,
            nomeCategoria,
            preco,
            quantidade,
            tempoGarantia,
            dataRecebimento,
            nomeFornecedor
        );
    }
}
