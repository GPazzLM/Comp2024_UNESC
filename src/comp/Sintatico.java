package comp;

import java.io.*;
import java.util.*;

/*public class Sintatico {

    public void executar(String filePath) {
        // Verifica se o caminho do arquivo está correto
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo não encontrado ou caminho inválido.");
            return;
    }

        // Lê o conteúdo do arquivo em uma lista de strings
        List<String> fileLines = readFile(filePath);

        System.out.println ();
        System.out.println ("Aqui inicia-se a análise sintática, com os Tokens fornecidos (em desenvolvimento):");
        System.out.println(fileLines);

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
}*/

import java.io.*;
import java.util.*;

public class Sintatico {

    // Tabela de Parsing
    private int[][] tabParsing = new int[83][51]; // Defina sua tabela aqui
    private Stack<Integer> pilha = new Stack<>(); // Pilha de Parsing

    // Produções (substitua pelos valores reais de sua gramática)
    private int[][] producoes = {
        {2, 11, 39, 52, 53, 54, 38, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        // Adicione todas as outras produções aqui...
    };

    public void executar(String filePath) {
        // Verifica se o caminho do arquivo está correto
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo não encontrado ou caminho inválido.");
            return;
        }

        // Lê o conteúdo do arquivo em uma lista de tokens
        List<String> tokens = readFile(filePath);

        System.out.println("Aqui inicia-se a análise sintática, com os Tokens fornecidos (em desenvolvimento):");
        System.out.println(tokens);

        // Inicializa a pilha de parsing com o símbolo inicial da gramática (exemplo: 1 para o símbolo inicial S)
        pilha.push(1);

        // Inicia a análise dos tokens
        for (String token : tokens) {
            int tokenValue = Integer.parseInt(token); // Supondo que os tokens no arquivo sejam inteiros

            // Verifica a produção a ser aplicada com base no token atual e no topo da pilha
            while (!pilha.isEmpty()) {
                int topo = pilha.peek(); // Obtém o símbolo no topo da pilha

                if (isTerminal(topo)) {
                    // Se o topo da pilha for um terminal
                    if (topo == tokenValue) {
                        pilha.pop(); // Consome o token
                        System.out.println("Token " + token + " aceito.");
                        break; // Sai do loop interno e passa para o próximo token
                    } else {
                        System.out.println("Erro sintático: esperado " + topo + " mas encontrado " + tokenValue);
                        return;
                    }
                } else {
                    // Se o topo for um não-terminal, consultamos a tabela de parsing
                    int producao = tabParsing[topo][tokenValue]; // Produção a ser aplicada
                    if (producao == -1) {
                        System.out.println("Erro sintático: não há produção para " + topo + " com " + tokenValue);
                        return;
                    }

                    // Aplica a produção
                    pilha.pop(); // Remove o não-terminal do topo
                    aplicarProducao(producao);
                }
            }
        }

        // Verifica se a pilha está vazia ao final
        if (pilha.isEmpty()) {
            System.out.println("Análise sintática concluída com sucesso.");
        } else {
            System.out.println("Erro: pilha não vazia ao final da análise.");
        }
    }

    // Função para verificar se o símbolo é terminal
    private boolean isTerminal(int symbol) {
        // Defina a lógica para identificar terminais (por exemplo, se forem números menores que um certo valor)
        return symbol < 50; // Exemplo: considere terminais como símbolos menores que 50
    }

    // Função para aplicar uma produção (adiciona os símbolos no lado direito da produção na pilha)
    private void aplicarProducao(int producao) {
        System.out.println("Aplicando produção: P" + producao);
        int[] prod = producoes[producao];
        for (int i = prod.length - 1; i >= 0; i--) {
            if (prod[i] != 0) { // Ignora os zeros (vazios)
                pilha.push(prod[i]);
            }
        }
    }

    // Função para ler o arquivo
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
