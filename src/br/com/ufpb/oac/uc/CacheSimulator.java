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

			System.out.println("Lendo a linha " + Converter.binaryToDec(line) + " da memória cache");
			
			String lineCache = FileAccess.readLine(cacheFile, Converter.binaryToDec(line));
			
			System.out.println(lineCache);
			
			String cacheTag = lineCache.substring(1, 9);

			System.out.println(cacheTag);
			
			String raValue;

			// Verifica se o bloco que contém a RA está na cache
			boolean isRAInCache = true;

			if (tag.equals(cacheTag)) {
				
				System.out.println("CacheHit. Ler um dos 4 bytes da cache a partir da palavra");
				
				System.out.println("Byte carregado: " + Converter.binaryToDec(word));
				
				String byteValue = null;
				
				//O número da palavra com 2 bits é usado para selecionar um dos 4 bytes nessa linha.
				switch (word) {
				case "00":
					byteValue = lineCache.substring(9, 17);
					break;
					
				case "01":
					byteValue = lineCache.substring(17, 25);
					break;
					
				case "10":
					byteValue = lineCache.substring(25, 33);
					break;
					
				case "11":
					byteValue = lineCache.substring(33, 41);
					break;

				default:
					break;
				}

				System.out.println("Valor do byte em binário: " + byteValue);
				
				System.out.println("Valor do byte em decimal: " + Converter.binaryToDec(byteValue));
				
			} else {
				
				System.out.println("CacheMiss");
				
				String tagLine = tag + line + "00";

				System.out.println("Acessa à memória principal no endereço " + tagLine);

				System.out.println("Armazena bloco da memória da cache");

				// raValue =

			}

			System.out.println("Entrega linha da RA para a CPU");

			System.out.println("-------------------------\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
