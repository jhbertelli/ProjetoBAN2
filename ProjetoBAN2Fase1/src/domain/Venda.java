package domain;

import java.util.ArrayList;

public class Venda {
    private final int id;
    private final ArrayList<VendaProduto> vendaProdutos = new ArrayList<>();
    private Vendedor vendedor;

    public Venda(int id) {
        this.id = id;
    }

    public ArrayList<VendaProduto> getVendaProdutos() {
        return vendaProdutos;
    }

    public void adicionarProduto(int idProduto, int quantidade) {
        vendaProdutos.add(new VendaProduto(this.id, idProduto, quantidade));
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
}
