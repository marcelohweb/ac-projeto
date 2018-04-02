package br.com.ufpb.oac.uc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe para manipular os arquivos em disco
 * @author Marcelo Soares
 *
 */
public class FileAccess {
	
	/**
	 * Retorna uma lista com as linhas do arquivo
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static List<String> readAllLines(String file) throws IOException {
		return Files.readAllLines(Paths.get(file));
	}
	
	/**
	 * Retorna a quantidade de linhas do arquivo
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static int getFileSize(String file) throws IOException {
		return Files.readAllLines(Paths.get(file)).size();
	}
	
	/**
	 * Lê uma linha do arquivo especificado
	 * @param file
	 * @param line
	 * @return {@link String}
	 * @throws IOException 
	 */
	public static String readLine(String file, int line) throws IOException {
		return Files.readAllLines(Paths.get(file)).get(line);
	}

	/**
	 * Escreve em um arquivo de texto
	 * @param file
	 * @param content
	 * @throws IOException 
	 */
	public static void write(String file, String content) throws IOException {
		Files.write(Paths.get(file), content.getBytes());
	}
	
	/**
	 * Escreve em um arquivo de texto a partir de uma lista com um elemento por linha
	 * @param file
	 * @param list
	 * @throws IOException 
	 */
	public static void writeListEachElementByRow(String file, List<String> list) throws IOException {
		String content = "";
		int aux = 0;
		
		for (String element : list) {
			content += element;
			if(aux < list.size())
				content += "\n";
			aux++;
		}
		
		Files.write(Paths.get(file), content.getBytes());
	}
	
}
