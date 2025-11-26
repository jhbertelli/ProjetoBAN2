package domain2;

public class VendaProduto {
    private Produto produto;
    private int idVenda;
    private int quantidadeVendida;

    public VendaProduto() {
    }

    public VendaProduto(int idVenda, Produto produto, int quantidadeVendida) {
        this.idVenda = idVenda;
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }
}
