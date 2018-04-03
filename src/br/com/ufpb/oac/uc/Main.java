package br.com.ufpb.oac.uc;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Implementação de um simulador de Unidade de Controle Microprogramada para a disciplina Arquitetura de Computadores do PPGI - UFPB
 * A implementação foi baseada no capítulo 15 do livro Organização e Arquitetura de Computadores de William Stallings - 8ª edição.
 * 
 * @see https://sites.google.com/site/alissonbrito/Home/cursos/2010-2-2011-1-atual/arquitetura-de-computadores
 * @author Marcelo Soares <marceloh.web@gmail.com>
 *
 */
public class Main {

	/**
	 * Registrador de endereço de memória (MAR): conectado às linhas de endereço do
	 * barramento de sistema. Ele especifica o endereço na memória para uma operação
	 * de leitura ou escrita.
	 */
	static String mar;

	/**
	 * Registrador de buffer de memória (MBR): conectado às linhas de dados do
	 * barramento de sistema. Ele contém o valor a ser guardado na memória ou o
	 * último valor lido da memória.
	 */
	static String mbr;

	/**
	 * contador de programa (pc): guarda o endereço da próxima instrução a ser lida.
	 */
	static int pc = 0;

	/**
	 * Registrador de instrução (IR): guarda a última instrução lida.
	 */
	static String ir;
	
	/**
	 * Registrador código de ciclo de instrução. Indica a etapa a ser realizada: busca de instrução (00), execução (10) e armazenamento em memória (11)
	 */
	static int icc = 00;
	
	/**
	 * Nome do arquivo com as instruções assembly
	 */
	static String programAssemblyFile = "assembly.asm";
	
	/**
	 * Nome do arquivo que representa a memória principal
	 */
	static String memoryFile = "memory.txt";
	
	/**
	 * Armazena os ciclos de clock
	 */
	static int qtdExecutadas = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			System.out.println("Iniciando o simulador de Unidade de Controle Microprogramada");
			
			float cpi = 0;
			
			try {
				//Solicitando que o usuário informe o CPI médio do processador para o cálculo de ciclos de clocks da aplicação
				Scanner input = new Scanner(System.in);
				System.out.print("Informe o CPI médio do seu processador (use vírgula para decimal): ");
				cpi = input.nextFloat();
				input.close();
			
			}catch(Exception e){
				System.out.println("Valor incorreto");
				return;
			}
			
			//Pega a quantidade de isntruções no arquivo assembly.asm para usar no loop principal
			int qtdInstrucoes = FileAccess.getFileSize(programAssemblyFile);

			System.out.printf("Quantidade de instruções encontradas: %d\n\n", qtdInstrucoes);
			
			//O loop principal será executado de acordo com a quantidade de instruções contidas em assembly.asm
			while (pc < qtdInstrucoes) {
				
				//Para quando o icc for 11 que indica (neste simulador) o estágio final armazenamento em memória
				loop: while(true) {
					//UC verifica o código de ciclo de instrução
					switch (icc) {
					// busca
					case 00:
						
						System.out.println("ICC: 00 - Iniciando microoperações para busca");
						
						System.out.printf("Valor do PC (Program Counter): %d. Movendo o valor do endereço da próxima instrução em PC para o registrador MAR\n",pc);
						mar = String.valueOf(pc);
						
						mbr = FileAccess.readLine(programAssemblyFile, pc);
						System.out.println("READ na memória para obter a instrução e colocar no registrador MBR. Instrução a ser executada: " + mbr);
						
						pc++;
						System.out.printf("Incrementando o valor de PC. Novo valor do PC: %d\n", pc);
						
						System.out.println("Movendo o conteúdo de MBR para o registrador IR e liberando o valor de MBR para um possível ciclo indireto");
						ir = mbr;
						mbr = null;
						
						//altera o icc para o de execução
						icc = 10;
						
						System.out.println();
						
						break;
					// execução
					case 10:
						
						//Diferentes microoperações devido aos diferentes opcodes
						
						System.out.println("ICC: 10 - Iniciando microoperações para execução da instrução " + ir);
						
						//Quebra a string da linha do assembly.asm para pegar posteriormente instrução e valores
						String[] aux = ir.split(" ");
						//Pegando a instrução assembly
						String instrucao = aux[0];
						
						qtdExecutadas++;
						
						//Case pelo mnemônico assembly
						switch (instrucao) {
						case "lw"://Carregar dado da memória no registrador (lw register_destination, RAM_source)
							
							//Stallings (2010, p.465)
							mar = ir;
							
							System.out.println("Lendo o valor da memória e adicionando em MBR");
							//Lê o valor da memória e adiciona em mbr
							mbr  = FileAccess.readLine(memoryFile, Utils.getValue(aux[2]));
							
							//Pega o nome do registrador
							String registerName = Utils.getRegisterName(aux[1]);
							//Pega o arquivo de texto que representa o registrador
							String registerFileName = Utils.getRegisterFileName(registerName);
							
							System.out.println("Inserindo o valor " + mbr + " no registrador " + registerName);
							//Grava o valor no arquivo que representa o registrador
							FileAccess.write(registerFileName, mbr);
							
							//Altera o icc para busca
							icc = 00;
							
							break;
						case "sw"://Salva dado de um registrador na memória (sw register_source, RAM_destination)
							
							//Altera o icc para busca para armazenamento em memória
							icc = 11;
							
							break;
						case "add"://Operação de soma
							
							//Stallings (2010, p.465)
							mar = ir;
							
							//Obtendo os operandos dos registradores
							String operando1File = Utils.getRegisterFileName(Utils.getRegisterName(aux[2]));
							String operando2File = Utils.getRegisterFileName(Utils.getRegisterName(aux[3]));
							
							int operandoSoma1Value  = Integer.parseInt(FileAccess.readLine(operando1File, 0));
							int operandoSoma2Value  = Integer.parseInt(FileAccess.readLine(operando2File, 0));
							
							//Aqui a instrução de soma é executada (add)
							int resultSoma = operandoSoma1Value + operandoSoma2Value;
							
							//Obtém o nome do registrador para armazenar o resultado
							String registerResultSomaName = Utils.getRegisterName(aux[1]);
							
							System.out.println("Executando instrução " + String.valueOf(operandoSoma1Value) + " + " + String.valueOf(operandoSoma2Value) + " = " + String.valueOf(resultSoma) + 
									" e salvando o valor no registrador " + registerResultSomaName);
							
							//Obtém o nome do arquivo que representa o registrador que receberá o resultado
							String registerResultSomaFileName = Utils.getRegisterFileName(registerResultSomaName);
							//Escreve o resultado no registrador
							FileAccess.write(registerResultSomaFileName, String.valueOf(resultSoma));
							
							//Altera o icc para busca
							icc = 00;
							
							break;
						case "sub"://Operação de subtração
							
							//Stallings (2010, p.465)
							mar = ir;
						
							//Obtendo os operandos dos registradores. Não acessaremos a memmória pois os dados já foram carregados nos registradores em instrução anterior
							String operandoSub1File = Utils.getRegisterFileName(Utils.getRegisterName(aux[2]));
							String operandoSub2File = Utils.getRegisterFileName(Utils.getRegisterName(aux[3]));
							
							int operandoSub1Value  = Integer.parseInt(FileAccess.readLine(operandoSub1File, 0));
							int operandoSub2Value  = Integer.parseInt(FileAccess.readLine(operandoSub2File, 0));
							
							//Aqui a instrução de subtração é executada (sub)
							int resultSub = operandoSub1Value - operandoSub2Value;
							
							//Obtém o nome do registrador para armazenar o resultado
							String registerResultSubName = Utils.getRegisterName(aux[1]);
							
							System.out.println("Executando instrução " + String.valueOf(operandoSub1Value) + " - " + String.valueOf(operandoSub2Value) + " = " + String.valueOf(resultSub) + 
									" e salvando o valor no registrador " + registerResultSubName);
							
							//Obtém o nome do arquivo que representa o registrador que receberá o resultado
							String registerResultSubFileName = Utils.getRegisterFileName(registerResultSubName);
							//Escreve o resultado no registrador
							FileAccess.write(registerResultSubFileName, String.valueOf(resultSub));
							
							//Altera o icc para busca
							icc = 00;
							
							break;

						default:
							break;
						}
						
						System.out.println();
						
						break;
					//armazenamento em memória
					case 11:
						//Quebra a string da linha do assembly.asm para pegar posteriormente instrução e valores
						String[] auxiliar = ir.split(" ");
						
						System.out.println("ICC: 11 - Iniciando microoperações para armazenamento em memória para a instrução: " + ir);
						
						System.out.println("Buscando o valor do registrador: " + Utils.getRegisterName(auxiliar[1]));
						
						//Obtendo o valor do registrador
						String registrador = Utils.getRegisterFileName(Utils.getRegisterName(auxiliar[1]));
						int registradorValue  = Integer.parseInt(FileAccess.readLine(registrador, 0));
						
						List<String> memoryContent = FileAccess.readAllLines(memoryFile);
						//Escreve na memória no endereço especificado. Neste simulador, o endereço representa o índice da linha do arquivo. Ex: sw $r1, (0) #escreverá o valor de $r1 na primeira linha de memory.txt
						int enderecoMemoria = Utils.getValue(auxiliar[2]);
						
						System.out.println("Armazenando o valor " + String.valueOf(registradorValue) + " no endereço de memória(linha do arquivo): " + String.valueOf(enderecoMemoria));
						
						if(enderecoMemoria < memoryContent.size())
							memoryContent.remove( Utils.getValue(auxiliar[2]));
						memoryContent.add( Utils.getValue(auxiliar[2]), String.valueOf(registradorValue));
						
						FileAccess.writeListEachElementByRow(memoryFile, memoryContent);
						//Fim da escrita na memória
						
						//Altera o icc para busca
						icc = 00;
						
						System.out.println();
						
						break loop;
	
					default:
						break;
					}
				
				}

			}
			
			//Calcula os ciclos de clock do programa de acordo com o CPI médio informado pelo usuário
			float clock = qtdExecutadas * cpi;
			
			String clockFormatted = String.format("%.02f", clock);
			
			System.out.println("O simulador foi executado com sucesso. Verifique o arquivo memory.txt para visualizar os novos valores das variáveis.");
			
			System.out.println("Quantidade de ciclos de clock necessários para executar o programa: " + clockFormatted);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
