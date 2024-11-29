package comp.semantico;

public class Simbolo {
    private String nome;
    private String tipo;
    private String categoria;

    public Simbolo(String nome, String tipo, String categoria) {
        this.nome = nome;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Simbolo{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
