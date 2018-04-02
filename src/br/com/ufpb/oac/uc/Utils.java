package br.com.ufpb.oac.uc;

/**
 * Classe auxiliar
 * @author Marcelo Soares
 *
 */
public class Utils {

	/**
	 * Método auxiliarpara retornar um valor entre parênteses que representa um endereço de memória
	 * @return
	 */
	public static int getValue(String value) {
		return Integer.parseInt(value.replace("(", "").replace(")", ""));
	}
	
	/**
	 * Retorna o nome do arquivo que representa um registrador
	 * @return {@link String}
	 */
	public static String getRegisterFileName(String registerName) {
		return registerName + ".txt";
	}
	
	/**
	 * Retira caracteres especiais para retornar o nome do registrador
	 * @return {@link String}
	 */
	public static String getRegisterName(String registerName) {
		return registerName.replace(",", "");
	}
	
}
