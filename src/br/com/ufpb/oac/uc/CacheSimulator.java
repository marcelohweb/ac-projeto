package br.com.ufpb.oac.uc;

import java.io.IOException;

public class CacheSimulator {

	/**
	 * Nome do arquivo que representa a memória cache
	 */
	static String cacheFile = "cache.txt";

	/**
	 * Inicia o simulador
	 */
	public static void start() {

		try {

			System.out.println("Acesso à memória cache");

			System.out.println("Recebe endereço RA da CPU");

			// Read Address - Endereço de leitura da memória
			String ra = "111111110000000000001111";

			String tag = ra.substring(0, 8);
			String line = ra.substring(8, 22);
			String word = ra.substring(22, 24);

			System.out.println("Tag: " + tag);
			System.out.println("Line: " + line);
			System.out.println("Word: " + word);

			String lineContent = FileAccess.readLine(cacheFile, Converter.binaryToDec(line));

			System.out.println(lineContent);
			
			String raValue;

			// Verifica se o bloco que contém a RA está na cache
			boolean isRAInCache = true;

			if (isRAInCache) {

				/*
				 * if(tag != tagCache){ System.out.println("CacheMiss"); }else{
				 * System.out.println("CacheHit"); Ler posição }
				 */

				// raValue =

			} else {

				System.out.println("Acessa à memória principal");

				System.out.println("Armazena bloco da memória da cache");

				// raValue =

			}

			System.out.println("Entrega linha da RA para a cpu");

			System.out.println("-------------------------\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
