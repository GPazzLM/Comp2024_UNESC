package comp.semantico;

import java.util.*;

public class Semantico2 {
    private Set<String> constantes;
    private Set<String> variaveis;
    private Set<String> procedures;
    private Set<String> identificadores;
    private Stack<String> inicioBloco;
    private Stack<String> inicioBlocoFor;
    private Stack<String> finalBloco;
    private Stack<String> finalBlocoFor;
    private Stack<String> writes;
    private String msgRetornoSemantico = "";

    private TabelaDeSimbolos tabelaDeSimbolos;

    public Semantico2() {
        inicializarEstruturas();
        tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    private void inicializarEstruturas() {
        this.constantes = new HashSet<>();
        this.variaveis = new HashSet<>();
        this.procedures = new HashSet<>();
        this.identificadores = new HashSet<>();
        this.inicioBloco = new Stack<>();
        this.finalBloco = new Stack<>();
        this.inicioBlocoFor = new Stack<>();
        this.finalBlocoFor = new Stack<>();
        this.writes = new Stack<>();
    }

    public String getMsgRetornoSemantico() {
        return msgRetornoSemantico;
    }

    public void interpretarCodigo(String codigo) {
        String[] linhas = codigo.split("\\n");

        for (String linha : linhas) {
            linha = linha.trim();

            if (linha.isEmpty()) continue;

            if (linha.startsWith("program")) {
                if (!linha.matches("program\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*;")) {
                    msgRetornoSemantico += "Erro semântico: Esperado 'program identificador;'. Linha: " + linha + "\n";
                }
                msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
            } else if (linha.startsWith("const")) {
                if (!linha.matches("const\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*.+")) {
                    msgRetornoSemantico += "Erro semântico: Declaração de constante inválida. Esperado 'const exemplo = integer;'. Atual: " + linha + "\n";
                } else {
                    String nomeConstante = linha.split("\\s+")[1];
                    if (tabelaDeSimbolos.existeSimbolo(nomeConstante)) {
                        msgRetornoSemantico += "Erro semântico: Constante '" + nomeConstante + "' já declarada.\n";
                    } else {
                        tabelaDeSimbolos.adicionarSimbolo(nomeConstante, "constante", "const");
                        constantes.add(nomeConstante);
                        msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
                    }
                }
            } else if (linha.startsWith("declaravariaveis")) {
                String[] partes = linha.split("\\s+");
                String nomeVariavel = partes[1];
                String tipoVariavel = partes[3];
                if (!linha.matches("declaravariaveis\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*(integer|real|string|char)\\s*;")) {
                    if (!(tipoVariavel.equals("integer") || tipoVariavel.equals("string") || tipoVariavel.equals("real") || tipoVariavel.equals("char"))) {
                        msgRetornoSemantico += "Erro semântico: Declaração de variável inválida. Esperado 'declaravariaveis exemplo : real | integer | string | char;'. Atual:  Linha: " + linha + "\n";
                    } else {
                        msgRetornoSemantico += "Erro semântico: Declaração de variável inválida. Esperado 'declaravariaveis exemplo : tipo;'. Atual:  Linha: " + linha + "\n";
                    }
                } else {
                    if (tabelaDeSimbolos.existeSimbolo(nomeVariavel)) {
                        msgRetornoSemantico += "Erro semântico: Variável '" + nomeVariavel + "' já declarada.\n";
                    } else {
                        tabelaDeSimbolos.adicionarSimbolo(nomeVariavel, tipoVariavel, "declaravariaveis");
                        variaveis.add(nomeVariavel);
                        msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
                    }
                }
            } else if (linha.startsWith("procedure")) {
                String nomeProcedimento = proximo(linha);
                if (tabelaDeSimbolos.existeSimbolo(nomeProcedimento)) {
                    msgRetornoSemantico += "Erro semântico: Procedimento '" + nomeProcedimento + "' já declarado.\n";
                } else {
                    tabelaDeSimbolos.adicionarSimbolo(nomeProcedimento, "Procedure", "procedure");
                    procedures.add(nomeProcedimento);
                    msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
                }
            } else if (linha.startsWith("begin")) {
                tabelaDeSimbolos.adicionarSimbolo(linha, "Bloco", "begin");
                inicioBloco.push(linha);
                msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
            } else if (linha.startsWith("end.")) {
                if (!inicioBloco.isEmpty()) {
                    inicioBloco.pop();
                    finalBloco.push(linha);
                    tabelaDeSimbolos.adicionarSimbolo(linha, "Bloco", "end.");
                    msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
                } else {
                    msgRetornoSemantico += "Erro semântico: 'end.' encontrado sem um bloco 'begin' correspondente.\n";
                }
            } else if (linha.startsWith("for")) {
                String identificador = linha.substring(linha.indexOf("[") + 1, linha.indexOf("=")).trim();
                if (identificadores.contains(identificador)) {
                    msgRetornoSemantico += "Erro semântico: Identificador '" + identificador + "' usada no 'for' já foi declarado.\n";
                }
                if (linha.contains("begin")){
                    tabelaDeSimbolos.adicionarSimbolo(linha, "Bloco", "COMANDO");
                    inicioBlocoFor.push(linha);
                }
                identificadores.add(identificador);
                tabelaDeSimbolos.adicionarSimbolo(identificador, "identificador", "identificador");
                msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
            } else if (linha.startsWith("end;")) {
                if (!inicioBlocoFor.isEmpty()) {
                    inicioBlocoFor.pop();
                    finalBlocoFor.push(linha);
                    tabelaDeSimbolos.adicionarSimbolo(linha, "Bloco", "COMANDO");
                    msgRetornoSemantico += "Linha reconhecida:'" + linha + "'.\n";
                } else {
                    msgRetornoSemantico += "Erro semântico: 'end;' encontrado sem um bloco for correspondente 'begin'.\n";
                }
            } else if (linha.startsWith("write")) {
                tabelaDeSimbolos.adicionarSimbolo(linha, "write", "write");
                writes.push(linha);
            } else {
                msgRetornoSemantico += "Erro semântico: Linha não reconhecida ou inválida (identificador não declarado): " + linha + "\n";
            }
        }

        // Verificar se todos os blocos "begin" possuem "end."
        if (!inicioBloco.isEmpty()) {
            msgRetornoSemantico += "Erro semântico: Bloco 'begin' sem correspondente 'end.'\n";
        }
    }

    private String proximo(String linha) {
        String[] partes = linha.split("\\s+");
        return partes.length > 1 ? partes[1].replace(";", "") : "";
    }

    public void imprimirTabelaDeSimbolos() {
        System.out.println("Tabela de Símbolos:");
        tabelaDeSimbolos.imprimirTabela();
    }
}

