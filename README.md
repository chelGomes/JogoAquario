# JogoAquario

## Especificação do Programa: Jogo do Aquário

Considere um aquário representado por uma matriz bidimensional MxN. Nesse aquário há dois tipos de peixes, os peixes do tipo A, que comem plâncton, e os peixes do tipo B que comem os peixes do tipo A. Os peixes seguem as seguintes regras:

### Regras dos peixes do tipo A:

1. Se houver uma célula livre à sua volta, movimentam-se para a célula livre.
2. Se se movimentarem durante RA vezes seguidas e se a sua volta houver uma célula livre, reproduzem-se ficando na mesma célula e o filho na célula livre.
3. Se não se movimentarem durante MA vezes seguidas, morrem de fome.

### Regras dos peixes do tipo B:

1. Se houver a sua volta algum peixe do tipo A, movimenta-se para lá e come-o. Senão, movimenta-se para uma célula livre.
2. Quando tiver comido RB peixes do tipo A e a sua volta não existir nenhum peixe do seu tipo e houver uma célula livre, reproduz-se ficando na mesma célula e o filho na célula livre.
3. Se durante MB vezes não comer nenhum peixe do tipo A, morre de fome.

## Detalhes de Implementação

O jogo deverá ser inicializado com os seguintes parâmetros de entrada: (1) uma matriz bidimensional MxN com X peixes do tipo A e Y peixes do tipo B; e (2) os valores das variáveis RA, MA, RB, MB. X, Y, RA, MA, RB, MB representam valores inteiros e positivos.

O jogador poderá ver o resultado de cada iteração e ir avançando para ver os resultados. Entende-se por uma iteração, uma movimentação de todos os peixes possíveis. A cada iteração, o programa deverá mostrar na tela o resultado e dar a opção ao jogador de continuar ou encerrar o jogo.

Ao final, o jogo deverá informar a pontuação obtida para um dado conjunto de entrada. A pontuação do jogo será o número de iterações ocorridas do início ao término do jogo. O jogo termina quando não houver mais peixes do tipo B ou o jogador encerrar o jogo. Com os parâmetros corretos o jogo pode nunca acabar, resultando em um ecossistema sustentável.

## Como Executar o Projeto

### Pré-requisitos

- Java 18 ou superior
- Maven 3.6 ou superior

### Compilar o Projeto

Navegue até o diretório do projeto e execute:

```bash
cd Jogo_Aquario
mvn clean compile
```

### Executar o Jogo

Após compilar, execute o jogo com:

```bash
mvn exec:java -Dexec.mainClass="JogoAquario"
```

Ou, se preferir compilar e executar manualmente:

```bash
# Compilar
mvn compile

# Executar
java -cp target/classes JogoAquario
```

### Executar os Testes

Para executar os testes unitários:

```bash
mvn test
```

Para executar os testes com mais detalhes:

```bash
mvn test -Dtest=AquarioTest
```
