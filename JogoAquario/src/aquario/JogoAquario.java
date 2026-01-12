package aquario;

import java.util.Scanner;

/**
 * Classe principal do Jogo do Aquario
 */
public class JogoAquario {
    private Aquario aquario;
    
    public JogoAquario(int m, int n, int x, int y, int ra, int ma, int rb, int mb) {
        aquario = new Aquario(m, n, ra, ma, rb, mb);
        aquario.inicializarPeixes(x, y);
    }
    
    public Aquario getAquario() {
        return aquario;
    }
    
    /**Executa o jogo com interface de console*/
    public void jogar() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== JOGO DO AQUARIO ===");
        System.out.println(aquario);
        
        while (!aquario.jogoTerminou()) {
            System.out.print("\nPressione ENTER para continuar ou 's' para sair: ");
            String entrada = scanner.nextLine();
            
            if (entrada.equalsIgnoreCase("s")) {
                break;
            }
            
            aquario.executarIteracao();
            System.out.println(aquario);
        }
        
        System.out.println("\n=== FIM DO JOGO ===");
        System.out.println("Pontuação (numero de iterações): " + aquario.getIteracoes());
        
        scanner.close();
    }
    
    /**
     * Executa iterações automaticamente atÃ© o fim do jogo ou limite
     */
    public int executarJogoAutomatico(int maxIteracoes) {
        int contador = 0;
        while (!aquario.jogoTerminou() && contador < maxIteracoes) {
            aquario.executarIteracao();
            contador++;
        }
        return aquario.getIteracoes();
    }
    
    public static void main(String[] args) {
        try {
            // Exemplo de uso
            JogoAquario jogo = new JogoAquario(5, 5, 4, 4, 3, 3, 3, 3);
            jogo.jogar();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}