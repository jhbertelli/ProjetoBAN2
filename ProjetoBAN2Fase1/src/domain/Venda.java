package domain;

import java.util.ArrayList;

public class Venda {
    private final int id;
    private final ArrayList<VendaProduto> vendaProdutos = new ArrayList<>();
    private Integer idVendedor;

    public Venda(int id) {
        this.id = id;
    }

    public ArrayList<VendaProduto> getVendaProdutos() {
        return vendaProdutos;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void adicionarProduto(int idProduto, int quantidade) {
        vendaProdutos.add(new VendaProduto(this.id, idProduto, quantidade));
    }
}
