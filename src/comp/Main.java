package comp;

import java.util.Scanner;

import comp.sintatico.Sintatico;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //***********************ANÁLISE LÉXICA******************************
		String filePath = "C:/Users/glaucos.pazzeto/Documents/entrada.txt";

        // Cria uma instância da classe Lexico e chama o método 'executar'
        Lexico lexico = new Lexico();
        lexico.executar(filePath);

        
        //***********************ANÁLISE SINTÁTICA***************************
		String filePath1 = "C:/Users/glaucos.pazzeto/Documents/tokens.txt";

        // Cria uma instância da classe Sintatico e chama o método 'executar'
        Sintatico sintatico = new Sintatico();
        int[] tokens = sintatico.leTokens(filePath1);
        sintatico.analisar(tokens);
    }
}