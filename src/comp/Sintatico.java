package comp;

import java.io.*;
import java.util.*;

public class Sintatico {

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
}