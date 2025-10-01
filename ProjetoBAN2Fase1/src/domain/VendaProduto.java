package domain;

public class VendaProduto {
    private final Produto produto;
    private final int idVenda;
    private final int quantidadeVendida;

    public VendaProduto(int idVenda, Produto produto, int quantidadeVendida) {
        this.idVenda = idVenda;
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }
}
