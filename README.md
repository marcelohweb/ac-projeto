# Arquitetura de Computadores
Implementação de um simulador de Unidade de Controle Microprogramada para a disciplina Arquitetura de Computadores do PPGI - UFPB.

## Autor
Marcelo Hércules Cunha Soares ( <marceloh.web@gmail.com>  )

## Execução do programa

É necessário ter o Java 8 instalado. Obs. Só funcionará na versão 8 do Java.

Importe o projeto no Eclipse como um Java Project, dê um clique inverso no projeto -> Run as -> Java Application.

Certifique-se de que o JRE e compilador estão na versão 1.8

Também é possível exportar como um jar executável.

No Eclipse, siga os seguintes passos:

* Clique inverso no projeto -> Export
* Runnable jar file
* Finish

Copie os arquivos .txt e o arquivo .asm para o mesmo diretório de onde o jar foi gerado.

Caso o java já esteja no path do seu SO, na linha e comando digite java -jar nome-do-jar.jar

### Estrutura de arquivos

* assembly.asm - Arquivo com instruções em assembly que serão executadas pelo simulador. 
* memory.txt - Representa a memória principal. Contém os valores iniciais das variáveis e após a execução conterá os valores finais
* $r1.txt - Representa o registrador $r1 que é referenciado pelo arquivo assemnly.asm
* $r2.txt - Representa o registrador $r2 que é referenciado pelo arquivo assemnly.asm
* $r3.txt - Representa o registrador $r3 que é referenciado pelo arquivo assemnly.asm

Obs. Apenas as instruções lw, sw, add e sub são suportadas na primeira versão. No arquivo assembly.asm, os números entre parênteses (ex: (0)) simulam um endereço de memória, que neste simulador corresponde ao índice da linha no arquivo memory.txt. Por exemplo: a instrução sw $r1, (0) deve escrever o valor do registrador $r1 na primeira linha do arquivo memory.txt

Ao executar o programa, informe o CPI médio do seu processador para o cálculo de ciclos de clock necessários para a execução do programa (assembly).