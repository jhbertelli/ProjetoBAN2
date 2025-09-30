package domain;

public class VendaProduto {
    private final int idProduto;
    private final int idVenda;
    private final int quantidadeVendida;

    public VendaProduto(int idVenda, int idProduto, int quantidadeVendida) {
        this.idProduto = idProduto;
        this.idVenda = idVenda;
        this.quantidadeVendida = quantidadeVendida;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }
}
