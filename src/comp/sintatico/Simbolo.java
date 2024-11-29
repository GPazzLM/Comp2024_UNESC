package comp.sintatico;

public class Simbolo {
    private final String nome;
    private final String tipo;
    private Object valor;

    public Simbolo(String nome, String tipo, Object valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
