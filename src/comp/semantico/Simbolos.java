package comp.semantico;

public class Simbolos {
    private String nome;
    private String tipo;
    private Object valor;

    public Simbolos(String nome, String tipo, Object valor) {
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

    public void imprimirSimbolo() {
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Valor: " + valor + "\n");
    }
}
