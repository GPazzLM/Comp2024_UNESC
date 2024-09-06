package comp;

import java.io.*;
import java.util.*;

public class Lexico {

    // Caractere que indica o final de uma linha no arquivo
    private static final char SEP_LINHA = '&';

        // Método que contém a lógica principal, separado de "main"
        public void executar(String filePath) {
            // Verifica se o caminho do arquivo está correto
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                System.out.println("Arquivo não encontrado ou caminho inválido.");
                return;
        }

        // Lê o conteúdo do arquivo em uma lista de strings
        List<String> fileLines = readFile(filePath);

        // Cria listas para armazenar os tokens gerados e logs de saída
        List<LogToken> logTokens = new ArrayList<>();
        List<String> logSaida = new ArrayList<>();

        // Processa as linhas do arquivo para identificar tokens
        processaDados(fileLines, logTokens, logSaida);

        // Exibe os resultados do processamento
        mostraResultados(fileLines, logTokens, logSaida);
        
        Scanner scanner = new Scanner(System.in);

        // Solicita o caminho para salvar o arquivo de saída
        //System.out.print("Insira o caminho para salvar o arquivo de saída (.txt): ");
        //String outputFilePath = scanner.nextLine();
        String outputFilePath  = ("C:/Users/glaucos.pazzeto/Documents/tokens.txt");
        
        // Salva os tokens em um arquivo .txt
        salvaTokens(logTokens, logSaida, outputFilePath);
        
        // Salva os logs em um arquivo .txt
        salvaLogs(fileLines, logSaida, outputFilePath);
    }

    // Função que lê o arquivo e retorna as linhas com o caractere de separação adicionado
    private static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line + SEP_LINHA);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // Função que processa os dados das linhas do arquivo para gerar tokens
    private static void processaDados(List<String> fileLines, List<LogToken> logTokens, List<String> logSaida) {
        for (int i = 0; i < fileLines.size(); i++) {
            String linha = fileLines.get(i);
            int index = 0;
            int length = linha.length();
            StringBuilder palavra = new StringBuilder();
            int linhaIndex = i + 1;

            // Percorre cada caractere da linha
            while (index < length) {
                char charAt = linha.charAt(index);
                              
                if (charAt != SEP_LINHA) {
                    // Se o caractere não for espaço ou tabulação, continua a formação da palavra
                    if (charAt != ' ' && charAt != '\t') {
                        if (isAlphanumeric(charAt)) {
                            // Se for alfanumérico, adiciona o caractere à palavra
                            palavra.append(charAt);
                        } else {
                        	char specialChar = charAt;
                            // Se for um símbolo, processa a palavra anterior e o símbolo
                            if (palavra.length() > 0) {
                                int token = recuperaToken(palavra.toString());
                                if (token != 0) {
                                    logTokens.add(new LogToken(token, palavra.toString()));
                                } else {
                                    logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                                    logTokens.add(new LogToken(token, palavra.toString() + " - Não reconhecido na gramática"));
                                }
                                palavra.setLength(0);
                            }

                            // Verifica se o símbolo atual é o caractere especial armazenado em 'specialChar'
                            if (charAt == specialChar) {
                                int count = 0;
                                // Conta quantos caracteres 'specialChar' consecutivos existem
                                while (index < length && linha.charAt(index) == specialChar) {
                                    count++;
                                    index++;
                                }

                                // Separa os caracteres 'specialChar' em pares, e se sobrar 1, coloca-o separadamente
                                while (count > 0) {
                                    if (count >= 2) {
                                        palavra.append(specialChar).append(specialChar);  // Adiciona dois caracteres 'specialChar'
                                        count -= 2;
                                    } else {
                                        palavra.append(specialChar);  // Adiciona o último caractere 'specialChar' sozinho
                                        count--;
                                    }

                                    // Adiciona cada token separado
                                    int token = recuperaToken(palavra.toString());
                                    if (token != 0) {
                                        logTokens.add(new LogToken(token, palavra.toString()));
                                    } else {
                                        logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                                        logTokens.add(new LogToken(token, palavra.toString() + " - Não reconhecido na gramática"));
                                    }
                                    palavra.setLength(0);
                                }
                            } else {
                                // Processa símbolos que não são o 'specialChar'
                                palavra.append(charAt);
                                int token = recuperaToken(palavra.toString());
                                if (token != 0) {
                                    logTokens.add(new LogToken(token, palavra.toString()));
                                } else {
                                    logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                                    logTokens.add(new LogToken(token, palavra.toString() + " - Não reconhecido na gramática"));
                                }
                                palavra.setLength(0);
                            }
                        }
                    } else {
                        // Processa a palavra anterior se houver espaço ou tabulação
                        if (palavra.length() > 0) {
                            int token = recuperaToken(palavra.toString());
                            if (token != 0) {
                                logTokens.add(new LogToken(token, palavra.toString()));
                            } else {
                                logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                                logTokens.add(new LogToken(token, palavra.toString() + " - Não reconhecido na gramática"));
                            }
                            palavra.setLength(0);
                        }
                    }
                }
                index++;
            }
        }
    }

    // Função que verifica se um caractere é alfanumérico
    private static boolean isAlphanumeric(char c) {
        return Character.isLetterOrDigit(c);
    }

    // Função que mapeia uma palavra a um token específico
    private static int recuperaToken(String palavra) {
        switch (palavra) {
            case "while":          return 1;
            case "void":           return 2;
            case "string":         return 3;
            case "return":         return 4;
            case "numerointeiro":  return 5;
            case "numerofloat":    return 6;
            case "nomevariavel":   return 7;
            case "nomechar":       return 8;
            case "nomedavariavel": return 9;
            case "nomedastring":   return 10;
            case "main":           return 11;
            case "literal":        return 12;
            case "integer":        return 13;
            case "int":            return 14;
            case "inicio":         return 15;
            case "if":             return 16;
            case "î":              return 17;
            case "for":            return 18;
            case "float":          return 19;
            case "fim":            return 20;
            case "else":           return 21;
            case "double":         return 22;
            case "do":             return 23;
            case "cout":           return 24;
            case "cin":            return 25;
            case "char":           return 26;
            case "callfuncao":     return 27;
            case ">>":             return 28;
            case ">=":             return 29;
            case ">":              return 30;
            case "==":             return 31;
            case "=":              return 32;
            case "<=":             return 33;
            case "<<":             return 34;
            case "<":              return 35;
            case "++":             return 36;
            case "+":              return 37;
            case "{":              return 38;
            case "}":              return 39;
            case ";":              return 40;
            case ":":              return 41;
            case "/":              return 42;
            case ",":              return 43;
            case "*":              return 44;
            case "(":              return 45;
            case ")":              return 46;
            case "$":              return 47;
            case "!=":             return 48;
            case "--":             return 49;
            case "-":              return 50;
            default:               return 0;
        }
    }

    // Função que exibe os resultados do processamento no console
    private static void mostraResultados(List<String> fileLines, List<LogToken> logTokens, List<String> logSaida) {
        System.out.println();
        System.out.println("Conteúdo do arquivo:");
        for (int i = 0; i < fileLines.size(); i++) {
            System.out.println("LINHA " + (i + 1) + ": " + fileLines.get(i).replace(SEP_LINHA, ' '));
        }

        System.out.println();
        System.out.println("Tokens gerados:");
        for (LogToken logToken : logTokens) {
            System.out.print(logToken.getToken() + " ");
        }
        System.out.println();

        System.out.println();
        System.out.println("Identificação dos tokens recuperados:");
        logTokens.stream()
                //.distinct()
                //.sorted(Comparator.comparingInt(LogToken::getToken))
                .forEach(logToken -> System.out.println("[" + logToken.getToken() + "] " + logToken.getProd()));

        System.out.println();
        System.out.println("Logs de erro:");
        if (logSaida.isEmpty()) {
            System.out.println("Nenhum erro léxico identificado.");
            System.out.println("Dados validados com sucesso.");
        } else {
            for (String log : logSaida) {
                System.out.println(log);
            }
            System.out.println("Os dados fornecidos são inválidos.");
        }
    }

    // Classe interna que representa um token e seu produto
    static class LogToken {
        private final int token;
        private final String prod;

        public LogToken(int token, String prod) {
            this.token = token;
            this.prod = prod;
        }

        public int getToken() {
            return token;
        }

        public String getProd() {
            return prod;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogToken logToken = (LogToken) o;
            return token == logToken.token;
        }

        @Override
        public int hashCode() {
            return Objects.hash(token);
        }
    }

    private static void salvaTokens(List<LogToken> logTokens, List<String> logSaida, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            //writer.write("Tokens gerados:\n");
            for (LogToken logToken : logTokens) {
                //writer.write(logToken.getToken() + " [" + logToken.getProd() + "]\n");
                writer.write(logToken.getToken() + "\n");
            }
    
            System.out.println();
            System.out.println("Dados de tokens salvos em: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }
    
    private static void salvaLogs(List<String> fileLines, List<String> logSaida, String outputFilePath) {
        // Extraindo o diretório do caminho do arquivo original
        File originalFile = new File(outputFilePath);
        String directory = originalFile.getParent();
        
        // Novo nome de arquivo
        String additionalFilePath = directory + File.separator + "logs.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(additionalFilePath))) {
            writer.write("Conteúdo original do arquivo lido:\n");
            
            for (int i = 0; i < fileLines.size(); i++) {
                writer.write("LINHA " + (i + 1) + ": " + fileLines.get(i).replace(SEP_LINHA, ' ') + "\n");
            }
            
            if (logSaida.isEmpty()) {
                writer.write("Nenhum erro léxico identificado.\n");
            } else {
            	writer.write("\nLogs de erro:\n");
                for (String log : logSaida) {
                    writer.write(log + "\n");
                }
            }
            
            System.out.println("Dados de logs salvos em: " + additionalFilePath);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados adicionais no arquivo: " + e.getMessage());
        }
    }
}
