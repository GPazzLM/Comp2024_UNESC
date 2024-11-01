package comp.sintatico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Sintatico {

    private final Stack<Integer> PILHA;
    private final int[][] TABELA_PARSING;
    private final int[][] PRODUCOES;

    public Sintatico() {
        PILHA = new Stack<>();
        TABELA_PARSING = inicializaTabelaParsing();
        PRODUCOES = inicializaProducoes();
    }

    private int[][] inicializaProducoes() {
        return new int[][]{
                {9, 16, 42, 54, 46},
                {22, 56, 57, 58},
                {23, 16, 31, 59, 42, 60},
                {17},
                {16, 31, 59, 42, 60},
                {17},
                {22, 61, 43, 59, 42, 62},
                {17},
                {16, 63},
                {47, 16, 63},
                {17},
                {61, 43, 59, 42, 62},
                {17},
                {27, 41, 37, 45, 37, 40, 12, 64},
                {14},
                {24},
                {5},
                {7},
                {14},
                {24},
                {5},
                {7},
                {10, 16, 65, 57, 58, 42, 55},
                {17},
                {50, 61, 43, 59, 42, 62, 49},
                {17},
                {26, 66, 42, 67, 19},
                {66, 42, 67},
                {17},
                {15, 40, 68, 41, 4, 26, 66, 19, 69},
                {1, 40, 68, 41, 21, 26, 66, 19},
                {6, 66, 2, 40, 68, 41},
                {8, 50, 71, 49},
                {25, 16, 72},
                {0, 50, 73, 74, 49},
                {18, 40, 16, 31, 68, 41, 3, 40, 68, 41, 21, 26, 66, 19},
                {17},
                {50, 61, 49},
                {17},
                {13},
                {68},
                {47, 73, 74},
                {17},
                {75, 76, 77},
                {78, 79},
                {37},
                {16},
                {38},
                {39},
                {36},
                {50, 68, 49},
                {31, 80},
                {34, 80},
                {30, 80},
                {29, 80},
                {33, 80},
                {32, 80},
                {17},
                {35, 75, 76},
                {52, 75, 76},
                {75, 76},
                {35, 75, 76},
                {52, 75, 76},
                {11, 75, 76},
                {17},
                {48, 78, 79},
                {44, 78, 79},
                {28, 78, 79},
                {20, 26, 66, 19},
                {17},
                {16, 71},
                {47, 16, 71},
                {17}
        };
    }

    private int[][] inicializaTabelaParsing() {
        int[][] tabela = new int[81][53];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 53; j++) {
                tabela[i][j] = -1; // -1 representa entrada vazia
            }
        }
        tabela[53][9] = 0;
        tabela[54][10] = 1;
        tabela[54][23] = 1;
        tabela[54][22] = 1;
        tabela[54][26] = 1;
        tabela[56][23] = 2;
        tabela[56][10] = 3;
        tabela[56][22] = 3;
        tabela[56][26] = 3;
        tabela[56][16] = 3;
        tabela[60][16] = 4;
        tabela[60][22] = 5;
        tabela[60][26] = 5;
        tabela[57][22] = 6;
        tabela[57][26] = 7;
        tabela[57][16] = 7;
        tabela[57][17] = 7;
        tabela[61][16] = 8;
        tabela[63][47] = 9;
        tabela[63][42] = 10;
        tabela[63][43] = 10;
        tabela[59][27] = 13;
        tabela[64][14] = 14;
        tabela[64][24] = 15;
        tabela[64][5] = 16;
        tabela[64][7] = 17;
        tabela[58][22] = 26;
        tabela[58][26] = 26;
        tabela[66][15] = 29;
        tabela[66][1] = 30;
        tabela[66][6] = 31;
        tabela[66][8] = 32;
        tabela[66][25] = 33;
        tabela[66][0] = 34;
        tabela[66][18] = 35;
        tabela[66][19] = 36;
        tabela[72][50] = 37;
        tabela[72][42] = 38;
        tabela[73][13] = 39;
        tabela[73][16] = 40;
        tabela[73][37] = 40;
        tabela[73][36] = 40;
        tabela[73][38] = 40;
        tabela[73][39] = 40;
        tabela[73][50] = 40;
        tabela[74][47] = 41;
        tabela[74][49] = 42;
        tabela[68][16] = 43;
        tabela[68][37] = 43;
        tabela[68][36] = 43;
        tabela[68][38] = 43;
        tabela[68][39] = 43;
        tabela[68][50] = 43;
        tabela[75][16] = 44;
        tabela[75][37] = 44;
        tabela[75][36] = 44;
        tabela[75][38] = 44;
        tabela[75][39] = 44;
        tabela[75][50] = 44;
        tabela[78][37] = 45;
        tabela[78][16] = 46;
        tabela[78][38] = 47;
        tabela[78][39] = 48;
        tabela[78][36] = 49;
        tabela[78][50] = 50;
        tabela[77][31] = 51;
        tabela[77][34] = 52;
        tabela[77][30] = 53;
        tabela[77][29] = 54;
        tabela[77][33] = 55;
        tabela[77][32] = 56;
        tabela[77][42] = 57;
        tabela[80][35] = 58;
        tabela[80][52] = 59;
        tabela[80][16] = 60;
        tabela[58][16] = 60;
        tabela[76][35] = 61;
        tabela[76][52] = 62;
        tabela[76][11] = 63;
        tabela[76][17] = 64;
        tabela[79][48] = 65;
        tabela[79][44] = 66;
        tabela[79][28] = 67;
        tabela[79][17] = 68;
        tabela[69][20] = 69;
        tabela[69][17] = 70;
        tabela[70][16] = 71;
        tabela[71][47] = 72;
        tabela[71][49] = 73;

        return tabela;
    }

    public void analisar(int[] tokens) {
        PILHA.push(53);
        int posicaoToken = 0;

        while (!PILHA.isEmpty()) {
            int topoPilha = PILHA.peek();

            if (isTerminal(topoPilha)) {
                if (topoPilha == tokens[posicaoToken]) {
                    System.out.println("Match: " + getSimbolo(topoPilha));
                    PILHA.pop();
                    posicaoToken++;
                } else {
                    reportaErro(topoPilha, tokens[posicaoToken], posicaoToken);
                    break;
                }
            } else {
                int entrada = TABELA_PARSING[topoPilha][tokens[posicaoToken]];
                if (entrada != -1) {
                    PILHA.pop();
                    empilhaProducao(entrada);
                } else {
                    System.out.println("topo "+topoPilha);
                    System.out.println("token: "+tokens[posicaoToken]);
                    reportaErro(topoPilha, tokens[posicaoToken], posicaoToken);
                    break;
                }
            }
            imprimePilha();
        }

        if (PILHA.isEmpty() && posicaoToken == tokens.length) {
            System.out.println("Análise concluída sem erros.");
        } else {
            System.out.println("Erro: Análise incompleta.");
        }
    }

    private void empilhaProducao(int producao) {
        int[] simbolos = PRODUCOES[producao];
        System.out.println("Produção aplicada: " + Arrays.toString(PRODUCOES[0]));

        for (int i = simbolos.length - 1; i >= 0; i--) {
            if (!(simbolos[i] == 17)) { // Ignora produções vazias
                PILHA.push(getCodigoSimbolo(simbolos[i]));
            }
        }
    }

    private boolean isTerminal(int simbolo) {
        return simbolo < 53;
    }

    private void imprimePilha() {
        System.out.print("Pilha: ");
        for (int s : PILHA) {
            System.out.print(getSimbolo(s) + " ");
        }
        System.out.println();
    }

    private void reportaErro(int esperado, int recebido, int posicao) {
        System.out.println("Erro de sintaxe na posição " + (posicao+1) + ": esperado " + getSimbolo(esperado) + " mas encontrado " + getSimbolo(recebido));
    }

    private String getSimbolo(int codigo) {
        return String.valueOf(codigo);
    }

    private int getCodigoSimbolo(int simbolo) {
        return simbolo;
    }

    public int[] leTokens(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo não encontrado ou caminho inválido.");
            return new int[0];
        }

        List<String> tokens = readFile(filePath);

        return tokens.stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
