package comp;

import java.io.*;
import java.util.*;

class Lexico {

    private static final char SEP_LINHA = '@';

    void executar(String filePath) {
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
        
        String outputFilePath = "C:/Users/glaucos.pazzeto/Documents/tokens.txt";
        //String outputFilePath = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/tokens.txt";
        salvaTokens(logTokens, logSaida, outputFilePath);
        salvaLogs(fileLines, logSaida, outputFilePath);
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
            String linha = fileLines.get(i).replace(String.valueOf(SEP_LINHA), "");
            int index = 0;
            int length = linha.length();
            StringBuilder palavra = new StringBuilder();
            int linhaIndex = i + 1;

            while (index < length) {
                char charAt = linha.charAt(index);

                // Ignora espaços em branco e comentarios
                if (Character.isWhitespace(charAt) || isComentario(charAt)) {
                    if (palavra.length() > 0) {
                        processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                        palavra.setLength(0);
                    }
                    index++;
                    continue;
                }

                // Captura operadores e símbolos de forma específica
                if ("()[]{};,:+-*/=<>".indexOf(charAt) != -1) {
                    if (palavra.length() > 0) {
                        processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                        palavra.setLength(0);
                    }
                    palavra.append(charAt);
                    processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                    palavra.setLength(0);
                    index++;
                    continue;
                }

                // Tratamento de strings
                if (charAt == '"') {
                    palavra.append(charAt);
                    index++;
                    while (index < length && linha.charAt(index) != '"') {
                        palavra.append(linha.charAt(index));
                        index++;
                    }
                    if (index < length) {
                        palavra.append('"');
                        processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                        palavra.setLength(0);
                        index++;
                    }
                    continue;
                }

                // Tratamento de strings (entre aspas simples)
                if (charAt == '\'') {
                    palavra.append(charAt);
                    index++;
                    while (index < length && linha.charAt(index) != '\'') {
                        palavra.append(linha.charAt(index));
                        index++;
                    }
                    if (index < length) {
                        palavra.append('\'');
                        processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                        palavra.setLength(0);
                        index++;
                    }
                    continue;
                }

                // Tratamento de literais de caractere
                if (charAt == '#') {
                    palavra.append(charAt);
                    index++;
                    while (index < length && linha.charAt(index) != ';' && linha.charAt(index) != ' ' && linha.charAt(index) != '\t') {
                        palavra.append(linha.charAt(index));
                        index++;
                    }
                    processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                    palavra.setLength(0);
                    continue;
                }

                // Tratamento de identificadores e números
                if (Character.isLetter(charAt) || charAt == '_') {
                    while (index < length && (Character.isLetterOrDigit(linha.charAt(index)) || linha.charAt(index) == '_')) {
                        palavra.append(linha.charAt(index));
                        index++;
                    }
                    processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                    palavra.setLength(0);
                    continue;
                }

                // Tratamento de números
                if (Character.isDigit(charAt)) {
                    while (index < length && Character.isDigit(linha.charAt(index))) {
                        palavra.append(linha.charAt(index));
                        index++;
                    }

                    if (index < length && linha.charAt(index) == '.') {
                        palavra.append('.');
                        index++;
                        while (index < length && Character.isDigit(linha.charAt(index))) {
                            palavra.append(linha.charAt(index));
                            index++;
                        }
                    }
                    processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
                    palavra.setLength(0);
                    continue;
                }

                // Para qualquer outro caractere não esperado
                palavra.append(charAt);
                index++;
            }

            // Processa a última palavra se houver
            if (palavra.length() > 0) {
                processaPalavra(palavra.toString(), logTokens, logSaida, linhaIndex);
            }
        }
    }

    private static void processaPalavra(String palavra, List<LogToken> logTokens, List<String> logSaida, int linhaIndex) {
        int token = recuperaToken(palavra);
        if (token != 999) {
            logTokens.add(new LogToken(token, palavra));
        } else {
            logSaida.add("Erro léxico na linha " + linhaIndex + ": [" + palavra + "] não está na gramática.");
            logTokens.add(new LogToken(token, palavra + " - Não reconhecido na gramática"));
        }
    }

    private static int recuperaToken(String palavra) {
        switch (palavra) {
            case "write":
                return 0;
            case "while":
                return 1;
            case "until":
                return 2;
            case "to":
                return 3;
            case "then":
                return 4;
            case "string":
                return 5;
            case "repeat":
                return 6;
            case "real":
                return 7;
            case "read":
                return 8;
            case "program":
                return 9;
            case "procedure":
                return 10;
            case "or":
                return 11;
            case "of":
                return 12;
            case "literal":
                return 13;
            case "integer":
                return 14;
            case "if":
                return 15;
            case "identificador":
                return 16;
            case "î":
                return 17;
            case "for":
                return 18;
            case "end":
                return 19;
            case "else":
                return 20;
            case "do":
                return 21;
            case "declaravariaveis":
                return 22;
            case "const":
                return 23;
            case "char":
                return 24;
            case "chamaprocedure":
                return 25;
            case "begin":
                return 26;
            case "array":
                return 27;
            case "and":
                return 28;

            // Operadores e símbolos
            case ">=":
                return 29;
            case ">":
                return 30;
            case "=":
                return 31;
            case "<>":
                return 32;
            case "<=":
                return 33;
            case "<":
                return 34;
            case "+":
                return 35;
            case "]":
                return 40;
            case "[":
                return 41;
            case ";":
                return 42;
            case ":":
                return 43;
            case "/":
                return 44;
            case "..":
                return 45;
            case ".":
                return 46;
            case ",":
                return 47;
            case "*":
                return 48;
            case ")":
                return 49;
            case "(":
                return 50;
            case "$":
                return 51;
            case "-":
                return 52;

            default:
                if (palavra.matches("\'.*\'")) { //Literais
                    return 13;
                } else if (palavra.matches("[a-zA-Z][a-zA-Z0-9]*")) { // Identificadores
                    return 16;
                } else if (palavra.matches("\\d+\\.\\d+")) { // Números reais
                    return 36;
                } else if (palavra.matches("\\d+")) { // Números inteiros
                    return 37;
                } else if (palavra.matches("\".*\"")) { // Strings
                     return 38;
                } else if (palavra.matches("#.*")) { // Char
                    return 39;
                } else {
                    return 999; // Não reconhecido
                }
        }
    }

    private static void mostraResultados(List<String> fileLines, List<LogToken> logTokens, List<String> logSaida) {
        System.out.println("\nConteúdo do arquivo:");
        for (int i = 0; i < fileLines.size(); i++) {
            System.out.println("LINHA " + (i + 1) + ": " + fileLines.get(i).replace(SEP_LINHA, ' '));
        }

        System.out.println("Tokens gerados:");
        for (LogToken logToken : logTokens) {
            System.out.print(logToken.getToken() + " ");
        }

        System.out.println("\nIdentificação dos tokens recuperados:");
        logTokens.forEach(logToken -> System.out.println("[" + logToken.getToken() + "] " + logToken.getProd()));

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

    private static void salvaTokens(List<LogToken> logTokens, List<String> logSaida, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (LogToken logToken : logTokens) {
                writer.write(logToken.getToken() + "\n");
            }
            System.out.println("Dados de tokens salvos em: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
    }

    private static void salvaLogs(List<String> fileLines, List<String> logSaida, String outputFilePath) {
        File originalFile = new File(outputFilePath);
        String directory = originalFile.getParent();
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

    private static boolean isComentario(char charAt){
        String linha = String.valueOf(charAt);
        return linha.matches("&") || linha.matches("&.*&");
    }
}