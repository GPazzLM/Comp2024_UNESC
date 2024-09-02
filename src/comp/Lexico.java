package comp;

import java.io.*;
import java.util.*;

public class Lexico {
	
	private static final char SEP_LINHA = '&';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o caminho do arquivo .txt: ");
        String filePath = scanner.nextLine();

        // Verifica se o caminho do arquivo está correto
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Arquivo não encontrado ou caminho inválido.");
            return;
        }

        List<String> fileLines = readFile(filePath);
        List<LogToken> logTokens = new ArrayList<>();
        List<String> logSaida = new ArrayList<>();
        
        processaDados(fileLines, logTokens, logSaida);
        
        mostraResultados(fileLines, logTokens, logSaida);
    }

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

    private static void processaDados(List<String> fileLines, List<LogToken> logTokens, List<String> logSaida) {
        for (int i = 0; i < fileLines.size(); i++) {
            String linha = fileLines.get(i);
            int index = 0;
            int length = linha.length();
            StringBuilder palavra = new StringBuilder();
            int linhaIndex = i + 1;

            while (index < length) {
                char charAt = linha.charAt(index);

                if (charAt != SEP_LINHA) {
                    if (charAt != ' ' && charAt != '\t') {
                        if (isAlphanumeric(charAt)) {
                            palavra.append(charAt);
                        } else {
                            if (palavra.length() > 0) {
                                int token = recuperaToken(palavra.toString());
                                if (token != 0) {
                                    logTokens.add(new LogToken(token, palavra.toString()));
                                } else {
                                    logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                                }
                                palavra.setLength(0);
                            }

                            palavra.append(charAt);
                            int token = recuperaToken(palavra.toString());
                            if (token != 0) {
                                logTokens.add(new LogToken(token, palavra.toString()));
                            } else {
                                logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                            }
                            palavra.setLength(0);
                        }
                    } else {
                        if (palavra.length() > 0) {
                            int token = recuperaToken(palavra.toString());
                            if (token != 0) {
                                logTokens.add(new LogToken(token, palavra.toString()));
                            } else {
                                logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
                            }
                            palavra.setLength(0);
                        }
                    }
                }
                index++;
            }
        }
    }

    private static boolean isAlphanumeric(char c) {
        return Character.isLetterOrDigit(c);
    }

    private static int recuperaToken(String palavra) {
        switch (palavra) {
            case "while": return 1;
            case "void": return 2;
            case "string": return 3;
            case "return": return 4;
            case "numerointeiro": return 5;
            case "numerofloat": return 6;
            case "nomevariavel": return 7;
            case "nomechar": return 8;
            case "nomedavariavel": return 9;
            case "nomedastring": return 10;
            case "main": return 11;
            case "literal": return 12;
            case "integer": return 13;
            case "int": return 14;
            case "inicio": return 15;
            case "if": return 16;
            case "î": return 17;
            case "for": return 18;
            case "float": return 19;
            case "fim": return 20;
            case "else": return 21;
            case "double": return 22;
            case "do": return 23;
            case "cout": return 24;
            case "cin": return 25;
            case "char": return 26;
            case "callfuncao": return 27;
            case ">>": return 28;
            case ">=": return 29;
            case ">": return 30;
            case "==": return 31;
            case "=": return 32;
            case "<=": return 33;
            case "<<": return 34;
            case "<": return 35;
            case "++": return 36;
            case "+": return 37;
            case "{": return 38;
            case "}": return 39;
            case ";": return 40;
            case ":": return 41;
            case "/": return 42;
            case ",": return 43;
            case "*": return 44;
            case "(": return 45;
            case ")": return 46;
            case "$": return 47;
            case "!=": return 48;
            case "--": return 49;
            case "-": return 50;
            default: return 0;
        }
    }

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
        System.out.println("Identificação dos tokens recuperados:");
        logTokens.stream()
                .distinct()
                .sorted(Comparator.comparingInt(LogToken::getToken))
                .forEach(logToken -> System.out.println("[" + logToken.getToken() + "] " + logToken.getProd()));

        System.out.println();
        System.out.println("Logs de erro:");
        if (logSaida.isEmpty()) {
            System.out.println("Nenhum erro léxico identificado.");
        } else {
            for (String log : logSaida) {
                System.out.println(log);
            }
        }
    }

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
}
