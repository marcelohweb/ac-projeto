# Arquitetura de Computadores
Implementação de um simulador de Unidade de Controle Microprogramada para a disciplina Arquitetura de Computadores do PPGI - UFPB.

## Autor
Marcelo Hércules Cunha Soares ( <marceloh.web@gmail.com>  )

## Execução do programa

É necessário ter o Java 8 instalado. Obs. Só funcionará na versão 8 do Java.

Baixe o arquivo uc-simulator.jar juntamente com os arquivos $r1.txt, $r2.txt, $r3.txt, memory.txt, assembly.asm e os coloque na mesma hierarquia de um diretório.

Caso o java já esteja no path do seu SO, na linha e comando digite java -jar uc-simulator.jar

Outra alternativa é importar o projeto no Eclipse como um Java Project, dar um clique inverso no projeto -> Run as -> Java Application. 

Certifique-se de que o JRE e compilador estão na versão 1.8

### Estrutura de arquivos

* assembly.asm - Arquivo com instruções em assembly que serão executadas pelo simulador. 
* memory.txt - Representa a memória principal para o simulador de unidade de controle microprogramada. Contém os valores iniciais das variáveis e após a execução conterá os valores finais
* cache.txt - Representa a memória cache para o simulador de acesso à memória cache
* $r1.txt - Representa o registrador $r1 que é referenciado pelo arquivo assembly.asm
* $r2.txt - Representa o registrador $r2 que é referenciado pelo arquivo assembly.asm
* $r3.txt - Representa o registrador $r3 que é referenciado pelo arquivo assembly.asm

Obs. Apenas as instruções lw, sw, add e sub são suportadas na primeira versão do simulador. No arquivo assembly.asm, os números entre parênteses (ex: (0)) simulam um endereço de memória, que neste simulador corresponde ao índice da linha no arquivo memory.txt. Por exemplo: a instrução sw $r1, (0) deve escrever o valor do registrador $r1 na primeira linha do arquivo memory.txt

Ao executar o programa, informe o CPI médio do seu processador para o cálculo de ciclos de clock necessários para a execução do programa (assembly).
