package br.com.ufpb.oac.uc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Simulador de acesso à Memória Cache
 * 
 * @author Marcelo
 *
 */
public class CacheAccess {

	/**
	 * Nome do arquivo que representa a memória cache que armazena 4 bytes por linha
	 * Formato do arquivo: ValidityBit(1 bit)|Tag(8 bits)|Data(32 bits)
	 */
	static String cacheFile = "cache.txt";

	/**
	 * HashMap para representar a memória principal.
	 */
	static HashMap<String, String> mainMemory = new HashMap<>();

	/**
	 * Inicia o simulador
	 */
	public static int start(String ra) {

		try {
			
			// Block 1
			mainMemory.put("111111110000000000000000", "00011001");// word 1
			mainMemory.put("111111110000000000000001", "00011010");// word 2
			mainMemory.put("111111110000000000000010", "00011011");// word 3
			mainMemory.put("111111110000000000000011", "00011100");// word 4

			// Block 2
			mainMemory.put("111111100000000000000100", "00011001");// word 1
			mainMemory.put("111111100000000000000101", "00011010");// word 2
			mainMemory.put("111111100000000000000110", "00011011");// word 3
			mainMemory.put("111111100000000000000111", "00011100");// word 4

			// Block 3
			mainMemory.put("111111010000000000001000", "00011001");// word 1
			mainMemory.put("111111010000000000001001", "00011010");// word 2
			mainMemory.put("111111010000000000001010", "00011011");// word 3
			mainMemory.put("111111010000000000001011", "00011100");// word 4

			// Block 4
			mainMemory.put("101111110000000000001100", "00011001");// word 1
			mainMemory.put("101111110000000000001101", "00011010");// word 2
			mainMemory.put("101111110000000000001110", "00011011");// word 3
			mainMemory.put("101111110000000000001111", "00011100");// word 4

			// Block 5
			mainMemory.put("100111110000000000000000", "00011001");// word 1
			mainMemory.put("100111110000000000000001", "00011010");// word 2
			mainMemory.put("100111110000000000000010", "00011011");// word 3
			mainMemory.put("100111110000000000000011", "00011100");// word 4

			// Block 6
			mainMemory.put("101011110000000000000100", "00011001");// word 1
			mainMemory.put("101011110000000000000101", "00011010");// word 2
			mainMemory.put("101011110000000000000110", "00011011");// word 3
			mainMemory.put("101011110000000000000111", "00011100");// word 4

			System.out.println("\n-------------------------");
			
			System.out.println("Iniciando o acesso à memória cache");

			System.out.println("Recebe um endereço RA de 32 bits da CPU: " + ra);

			String tag = ra.substring(0, 8);
			String line = ra.substring(8, 22);
			String word = ra.substring(22, 24);

			System.out.println("Tag (8 bits): " + tag);
			System.out.println("Line (22 bits): " + line);
			System.out.println("WordNumber (2 bits): " + word);

			// Verifica se o bloco que contém a RA está na cache
			System.out.println("Iniciando verificação na memória cache");

			System.out.println("Lendo a linha " + Converter.binaryToDec(line) + " da memória cache");

			String lineCache = FileAccess.readLine(cacheFile, Converter.binaryToDec(line));

			System.out.println("Conteúdo da linha " + Converter.binaryToDec(line) + " da memória cache: " + lineCache);

			String validityBit = lineCache.substring(0, 1);

			System.out.println("Bit de validade da tag: " + validityBit);

			boolean isCacheHit;

			if (validityBit.equals("1")) {

				String cacheTag = lineCache.substring(1, 9);

				System.out.println("Comparando valor da tag extraída do endereço com tag da linha da cache:");

				System.out.println("(" + tag + "==" + cacheTag + ")");

				if (tag.equals(cacheTag)) {

					System.out.println("CacheHit");
					isCacheHit = true;

				} else {
					System.out.println("CacheMiss");
					isCacheHit = false;
				}

			} else {
				System.out.println("CacheMiss");
				isCacheHit = false;
			}

			if (!isCacheHit) {

				// Carregar o bloco da memória principal na cache

				String tagLine = tag + line + "00";

				System.out.println("Acessa à memória principal no endereço " + tagLine);

				String data = mainMemory.get(tag + line + "00") + mainMemory.get(tag + line + "01")
						+ mainMemory.get(tag + line + "10") + mainMemory.get(tag + line + "11");

				if (data == null) {
					System.out.println("Não foi possível encontrar o endereço na memória principal");
				} else {

					System.out.println("Palavras do bloco da memória principal: " + data);

					System.out.println("Armazena bloco da memória da cache");

					String newValidityBit = "1";

					lineCache = newValidityBit + tag + data;
					
					List<String> cacheContent = FileAccess.readAllLines(cacheFile);
					cacheContent.remove(Converter.binaryToDec(line));
					cacheContent.add(Converter.binaryToDec(line), lineCache);

					FileAccess.writeListEachElementByRow(cacheFile, cacheContent);

				}

			}

			System.out.println("Ler um dos 4 bytes da cache a partir da palavra");

			System.out.println("Número do byte a carregar: " + Converter.binaryToDec(word));

			String byteValue = null;

			// O número da palavra com 2 bits é usado para selecionar um dos 4 bytes nessa
			// linha.
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

			System.out.println("Entrega linha da RA para a CPU");

			System.out.println("-------------------------\n");
			
			return Converter.binaryToDec(byteValue);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;

	}

}
