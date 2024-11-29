package comp.semantico;

import java.util.HashMap;
import java.util.Map;

public class TabelaDeSimbolos {
    private Map<String, Simbolo> tabela;

    public TabelaDeSimbolos() {
        tabela = new HashMap<>();
    }

    public boolean adicionarSimbolo(String nome, String tipo, String categoria) {
        if (tabela.containsKey(nome)) {
            return false; // Simbolo já declarado
        }
        tabela.put(nome, new Simbolo(nome, tipo, categoria));
        return true;
    }

    public Simbolo buscarSimbolo(String nome) {
        return tabela.get(nome);
    }

    public boolean existeSimbolo(String nome) {
        return tabela.containsKey(nome);
    }

    public void imprimirTabela() {
        for (Simbolo simbolo : tabela.values()) {
            System.out.println(simbolo);
        }
    }
}
