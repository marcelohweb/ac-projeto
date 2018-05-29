/**
 * 
 */
package br.com.ufpb.oac.uc;

import java.util.Scanner;

/**
 * @author Marcelo
 *
 */
public class Converter {

	private static Scanner scanner;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		scanner = new Scanner(System.in);
		
		System.out.println("Informe um número em binário");
		
		String entrada = scanner.next();
		
		int retornoDec = binaryToDec(entrada);
		
		System.out.println(retornoDec);
		
		System.out.println("Informe um número em decimal");
		
		int numero = scanner.nextInt();
		
		String retornoBin = decToBinary(numero);
		
		System.out.println(retornoBin);
		
	}
	
	/**
	 * Converte de binário para decimal
	 * @param entrada
	 */
	public static int binaryToDec(String entrada) {

		int potencia = 0;
		int decimal = 0;
		
		for (int aux = entrada.length() -1; aux>= 0; aux--) {
			
			decimal += Math.pow(2, potencia) * Character.getNumericValue(entrada.charAt(aux));
			potencia++;
			
		}
		
		return decimal;
		
	}
	
	/**
	 * Converte de decimal para binário
	 * @param numero
	 * @return
	 */
	public static String decToBinary(int numero) {
		
		StringBuffer binario = new StringBuffer();
		while (numero > 0) {
			int b = numero % 2;
			binario.append(b);
			numero = numero >> 1;
		}
		
		return binario.reverse().toString();
		
	}

}
