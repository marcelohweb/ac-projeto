package br.com.ufpb.oac.uc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Simulador de acesso à Memória Cache
 * @author Marcelo
 *
 */
public class CacheSimulator {

	/**
	 * Nome do arquivo que representa a memória cache
	 */
	static String cacheFile = "cache.txt";
	
	/**
	 * HashMap para representar a memória principal. A
	 */
	static HashMap<String, String> mainMemory = new HashMap<>();

	/**
	 * Inicia o simulador
	 */
	public static void start() {

		try {
			
			//Block 1
			mainMemory.put("111111110000000000001100", "00001010");//word 1
			mainMemory.put("111111110000000000001101", "00001011");//word 2
			mainMemory.put("111111110000000000001110", "00001100");//word 3
			mainMemory.put("111111110000000000001111", "00001101");//word 4

			//Block 2
			mainMemory.put("101111110000000000001000", "00001010");//word 1
			mainMemory.put("101111110000000000001001", "00001011");//word 2
			mainMemory.put("101111110000000000001010", "00001100");//word 3
			mainMemory.put("101111110000000000001011", "00001101");//word 4

			System.out.println("Acesso à memória cache");

			// Read Address - Endereço de leitura da memória
			String ra = "101111110000000000001000";
			
			System.out.println("Recebe um endereço RA de 32 bits da CPU: " + ra);

			String tag = ra.substring(0, 8);
			String line = ra.substring(8, 22);
			String word = ra.substring(22, 24);

			System.out.println("Tag (8 bits): " + tag);
			System.out.println("Line (22 bits): " + line);
			System.out.println("WordNumber (2 bits): " + word);

			System.out.println("Iniciando verificação na memória cache");
			
			System.out.println("Lendo a linha " + Converter.binaryToDec(line) + " da memória cache");
			
			String lineCache = FileAccess.readLine(cacheFile, Converter.binaryToDec(line));
			
			System.out.println("Conteúdo da linha " +  Converter.binaryToDec(line) + " da memória cache: " + lineCache);
			
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
				
				/**
				 * ▶ Senão, cache miss.
					⋆ Determina-se o bloco ao qual pertence o endereço.
					⋆ Todo o bloco é trazido para a cache, na linha adequada.
				 * 
				 */
				
				System.out.println("CacheMiss");
				
				//Carregar o bloco da memória principal na cache
				
				String tagLine = tag + line + "00";
				
				System.out.println("Acessa à memória principal no endereço " + tagLine);
				
				String data = mainMemory.get(tag + line + "00") + mainMemory.get(tag + line + "01") + mainMemory.get(tag + line + "10") + mainMemory.get(tag + line + "11");

				System.out.println("Palavras do bloco da memória principal: " + data);
				
				System.out.println("Armazena bloco da memória da cache");
				
				String validityBit = "1";
				
				List<String> cacheContent = FileAccess.readAllLines(cacheFile);
				cacheContent.remove(Converter.binaryToDec(line));
				cacheContent.add(Converter.binaryToDec(line), validityBit + tag + data);
				
				FileAccess.writeListEachElementByRow(cacheFile, cacheContent);
				
			}

			System.out.println("Entrega linha da RA para a CPU");

			System.out.println("-------------------------\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
