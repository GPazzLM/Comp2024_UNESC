package comp;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //***********************ANÁLISE LÉXICA******************************
        // Solicita ao usuário que insira o caminho do arquivo .txt
        //System.out.print("Insira o caminho do arquivo .txt: ");
        //String filePath = scanner.nextLine();
		String filePath = "C:/Users/glaucos.pazzeto/Documents/entrada.txt";

        // Cria uma instância da classe Lexico e chama o método 'executar'
        Lexico lexico = new Lexico();
        lexico.executar(filePath);

        
        //***********************ANÁLISE SINTÁTICA***************************
        // Solicita ao usuário que insira o caminho do arquivo .txt
        //System.out.print("Insira o caminho do arquivo .txt: ");
        //String filePath1 = scanner.nextLine();
		String filePath1 = "C:/Users/glaucos.pazzeto/Documents/tokens.txt";

        // Cria uma instância da classe Sintatico e chama o método 'executar'
        Sintatico sintatico = new Sintatico();
        sintatico.executar(filePath1);
    }
}