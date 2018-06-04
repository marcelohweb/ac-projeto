package br.com.ufpb.oac.uc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Simulador de acesso à Memória Cache
 * @author Marcelo
 *
 */
public class CacheSimulator {

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
	public static void start() {

		try {
			
			//Block 1
			mainMemory.put("111111110000000000000000", "00001010");//word 1
			mainMemory.put("111111110000000000000001", "00001011");//word 2
			mainMemory.put("111111110000000000000010", "00001100");//word 3
			mainMemory.put("111111110000000000000011", "00001101");//word 4
			
			//Block 2
			mainMemory.put("111111100000000000000100", "00001010");//word 1
			mainMemory.put("111111100000000000000101", "00001011");//word 2
			mainMemory.put("111111100000000000000110", "00001100");//word 3
			mainMemory.put("111111100000000000000111", "00001101");//word 4
			
			//Block 3
			mainMemory.put("111111010000000000001000", "00001010");//word 1
			mainMemory.put("111111010000000000001001", "00001011");//word 2
			mainMemory.put("111111010000000000001010", "00001100");//word 3
			mainMemory.put("111111010000000000001011", "00001101");//word 4
			
			//Block 4
			mainMemory.put("101111110000000000001100", "00001010");//word 1
			mainMemory.put("101111110000000000001101", "00001011");//word 2
			mainMemory.put("101111110000000000001110", "00001100");//word 3
			mainMemory.put("101111110000000000001111", "00001101");//word 4
			
			//Block 5
			mainMemory.put("100111110000000000000000", "00001010");//word 1
			mainMemory.put("100111110000000000000001", "00001011");//word 2
			mainMemory.put("100111110000000000000010", "00001100");//word 3
			mainMemory.put("100111110000000000000011", "00001101");//word 4
			
			//Block 6
			mainMemory.put("101011110000000000000100", "00001010");//word 1
			mainMemory.put("101011110000000000000101", "00001011");//word 2
			mainMemory.put("101011110000000000000110", "00001100");//word 3
			mainMemory.put("101011110000000000000111", "00001101");//word 4

			System.out.println("Acesso à memória cache");
			
			String ra;
			
			try {
				
				//Solicitando que o usuário informe o CPI médio do processador para o cálculo de ciclos de clocks da aplicação
				Scanner input = new Scanner(System.in);
				System.out.print("Informe o endereço de memória: ");
				ra = input.next();
			
			}catch(Exception e){
				System.out.println("Valor incorreto");
				return;
			}

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
			
			System.out.println("Conteúdo da linha " +  Converter.binaryToDec(line) + " da memória cache: " + lineCache);
			
			String validityBit = lineCache.substring(0, 1);
			
			System.out.println("Bit de validade da tag: " + validityBit);
			
			boolean isCacheHit;
			
			if(validityBit.equals("1")) {
			
				String cacheTag = lineCache.substring(1, 9);
	
				System.out.println("Comparando valor da tag extraída do endereço com tag da linha da cache:");
				
				System.out.println("(" + tag + "==" + cacheTag + ")");
				
				if (tag.equals(cacheTag)) {
					
					isCacheHit = true;
					
				}else {
					isCacheHit = false;
				}
			
			}else {
				isCacheHit = false;
			}
				
			if(isCacheHit) {
				
				System.out.println("CacheHit");
				
				System.out.println("Ler um dos 4 bytes da cache a partir da palavra");
				
				System.out.println("Número do byte a carregar: " + Converter.binaryToDec(word));
				
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
				
			}else{
				
				System.out.println("CacheMiss");
				
				//Carregar o bloco da memória principal na cache
				
				String tagLine = tag + line + "00";
				
				System.out.println("Acessa à memória principal no endereço " + tagLine);
				
				String data = mainMemory.get(tag + line + "00") + mainMemory.get(tag + line + "01") + mainMemory.get(tag + line + "10") + mainMemory.get(tag + line + "11");
				
				if(data == null) {
					System.out.println("Não foi possível encontrar o endereço na memória principal");
				}else {

					System.out.println("Palavras do bloco da memória principal: " + data);
					
					System.out.println("Armazena bloco da memória da cache");
					
					String newValidityBit = "1";
					
					List<String> cacheContent = FileAccess.readAllLines(cacheFile);
					cacheContent.remove(Converter.binaryToDec(line));
					cacheContent.add(Converter.binaryToDec(line), newValidityBit + tag + data);
					
					FileAccess.writeListEachElementByRow(cacheFile, cacheContent);
				
				}
				
			}

			System.out.println("Entrega linha da RA para a CPU");

			System.out.println("-------------------------\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
