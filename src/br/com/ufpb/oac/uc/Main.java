package br.com.ufpb.oac.uc;

import java.util.Scanner;

/**
 * Projetos referentes à disciplina Organização e Arquitetura de Computadores do PPGI/UFPB
 * 
 * Simulador de Unidade de Controle Microprogramada
 * Simulador de Acesso à Memória Cache
 * 
 * @see https://sites.google.com/site/alissonbrito/Home/cursos/2010-2-2011-1-atual/arquitetura-de-computadores
 * @author Marcelo Soares <marceloh.web@gmail.com>
 *
 */
public class Main {

	private static Scanner scanner;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    
		scanner = new Scanner(System.in);
	    int choice= 0;
		
		while(true) {
		
			System.out.println("Selecione o simulador:");
			System.out.println("-------------------------\n");
			System.out.println("1 - Unidade de Controle Microprogramada");
			System.out.println("2 - Acesso à memória cache");
			
		    choice = scanner.nextInt();
	
		    switch (choice) {
		        case 1:
		        	UCSimulator.start();
		            break;
		        case 2:
		            CacheSimulator.start();
		            break;
		        default:
		            System.out.println("Opção inválida");
		    }
		    
		}
		
	}

}
