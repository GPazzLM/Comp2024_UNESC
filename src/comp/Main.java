package comp;

import comp.semantico.Semantico2;
import comp.sintatico.Sintatico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
/*Autores:
Luana Nietto
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        //***********************ARQUIVOS DE EXEMPLOS******************************
        //TODO: o programa irá solicitar o caminho do arquivo na sua máquina, mas dá pra adicionar chumbado aqui para não precisar ficar colocando arquivo por arquivo, a cada teste.
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/exemploSucesso.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/inicioProgramaInvalido.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/constVariavelDuplicada.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/blocoIncorreto.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/atribuicoesIncorretas.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/identificadorNaoDeclarado.txt";
//        String filePathLex = "/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/tipoInexistente.txt";
//
        System.out.print("***********************ANÁLISE LÉXICA******************************");
        System.out.print("\nInsira o caminho do arquivo de exemplo .txt: ");
        String filePathLex = scanner.nextLine();
        Lexico lexico = new Lexico();
        lexico.executar(filePathLex);

        System.out.print("\n***********************ANÁLISE SINTÁTICA***************************\n");
        System.out.print("\nInsira o caminho do arquivo de token .txt: ");
        String filePathSintatico = scanner.nextLine();
//
        Sintatico sintatico = new Sintatico();
//        int[] tokens = sintatico.leTokens("/home/luana-agil/Downloads/Luana/Automato/AnalisadorLexico/tokens.txt");
        int[] tokens = sintatico.leTokens(filePathSintatico);
        sintatico.analisar(tokens);

        System.out.println("\n***********************ANÁLISE SEMANTICA***************************\n");

        String codigo = lerArquivoComoString(filePathLex);

        Semantico2 analisador = new Semantico2();

        System.out.println("Interpretando código:");
        System.out.println(codigo);

        analisador.interpretarCodigo(codigo);

        System.out.println("\nResultado da análise:");
        System.out.println(analisador.getMsgRetornoSemantico());

        analisador.imprimirTabelaDeSimbolos();
    }

    private static String lerArquivoComoString(String caminhoArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
    }
}