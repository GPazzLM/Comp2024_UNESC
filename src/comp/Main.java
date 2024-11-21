package comp;

import java.util.Scanner;

import comp.lexico.Lexico;
import comp.semantico.Semantico;
import comp.sintatico.Sintatico;

public class Main {
	
	public static boolean noErrors = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //***********************ANÁLISE LÉXICA******************************
        System.out.println("******ANÁLISE LÉXICA******");
		String filePath = "C:/Users/glaucos.pazzeto/Documents/entrada.txt";
        Lexico lexico = new Lexico();
        lexico.executar(filePath);
        
        //***********************ANÁLISE SINTÁTICA***************************
        System.out.println();
        System.out.println("******ANÁLISE SINTÁTICA******");
		String filePath1 = "C:/Users/glaucos.pazzeto/Documents/tokens.txt";
        Sintatico sintatico = new Sintatico();
        int[] tokens = sintatico.leTokens(filePath1);
        sintatico.analisar(tokens);
        
        //***********************ANÁLISE SEMÂNTICA***************************
        System.out.println();
        System.out.println("******ANÁLISE SEMÂNTICA******");
	    String filePath2 = "C:/Users/glaucos.pazzeto/Documents/prod.txt";
        Semantico semantico = new Semantico();
        int[] prod = semantico.leTokens(filePath2);
        semantico.analisar(prod);

    }
}