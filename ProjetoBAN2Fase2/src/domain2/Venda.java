package domain2;

import java.util.ArrayList;

public class Venda {
    private final int id;
    private final ArrayList<VendaProduto> vendaProdutos = new ArrayList<>();
    private Vendedor vendedor;

    public Venda(int id) {
        this.id = id;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        vendaProdutos.add(new VendaProduto(this.id, produto, quantidade));
    }

    public void atualizarProduto(Produto produto, int quantidade) {
        removerProduto(produto);
        adicionarProduto(produto, quantidade);
    }

    public void removerProduto(Produto produto) {
        vendaProdutos.removeIf(x -> x.getProduto() == produto);
    }

    public int getId() {
        return id;
    }

    public ArrayList<VendaProduto> getVendaProdutos() {
        return vendaProdutos;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(
            String.format(
                "ID: %d | Vendedor: %s | Produtos vendidos: ",
                id,
                vendedor == null ? "Nenhum" : vendedor.getNome()
            )
        );

        double valorTotal = 0.0;

        for (var vendaProduto : vendaProdutos) {
            stringBuilder.append(
                String.format(
                    "%s (R$%.2f) (x%d) ",
                    vendaProduto.getProduto().getNome(),
                    vendaProduto.getProduto().getPreco(),
                    vendaProduto.getQuantidadeVendida()
                )
            );
            valorTotal += vendaProduto.getProduto().getPreco() * vendaProduto.getQuantidadeVendida();
        }

        stringBuilder.append(String.format( "| Lucro total: R$ %.2f", valorTotal));

        return stringBuilder.toString();
    }
}
