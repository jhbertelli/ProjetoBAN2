package domain;

import java.sql.PreparedStatement;

public class Fornecedor {
    private final int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String emailContato;
    private String documento;
    private String nomeFantasia;

    public Fornecedor(String endereco, String telefone, String nome, String nomeFantasia, String documento, String emailContato ) {
        this(0, endereco, telefone, nome, nomeFantasia, documento, emailContato);
    }

    public Fornecedor(int id, String  endereco, String telefone, String nome, String nomeFantasia, String documento, String emailContato){
        this.nome = nome;
        this.id = id;
        this.endereco = endereco;
        this.telefone = telefone;
        this.documento = documento;
        this.nomeFantasia = nomeFantasia;
        this.emailContato = emailContato;
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
        return emailContato;
    }

    public void setEmail(String emailContato) {
        this.emailContato = emailContato;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia() {
        this.nomeFantasia = nomeFantasia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento() {
        this.documento = documento;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nome: %s | Nome Fantasia: %s | Endereço: %s | Telefone %s | Documento: %s | Email: %s\n",
            id,
            nome,
            nomeFantasia,
            endereco,
            telefone,
            documento,
            emailContato
        );
    }
}
