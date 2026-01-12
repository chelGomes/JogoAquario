package aquario;

/**
 * Peixe do Tipo B - Come peixes do tipo A
 * Regras:
 * 1. Se houver peixe A adjacente, move e come. Senao, move para celula livre
 * 2. Quando comer RB peixes A e nao houver peixe B adjacente, reproduz-se
 * 3. Se nao comer nenhum peixe A por MB vezes, morre de fome
 */
public class PeixeB extends Peixe {
    private int contadorComidas;
    
    public PeixeB(int linha, int coluna) {
        super(linha, coluna);
        this.contadorComidas = 0;
    }
    
    public int getContadorComidas() {
        return contadorComidas;
    }
    
    public void incrementarComidas() {
        this.contadorComidas++;
        this.contadorSemAcao = 0; // Resetar contador de fome
    }
    
    public void resetarContadorComidas() {
        this.contadorComidas = 0;
    }
    
    @Override
    public boolean executarAcao(Aquario aquario) {
        // Prioridade 1: Verificar se ha peixe A adjacente para comer
        int[] posicaoPeixeA = aquario.buscarPeixeAAdjacente(linha, coluna);
        
        if (posicaoPeixeA != null) {
            // Ha peixe A - mover e comer
            int novaLinha = posicaoPeixeA[0];
            int novaColuna = posicaoPeixeA[1];
            
            aquario.removerPeixe(novaLinha, novaColuna); // Remove o peixe A
            aquario.moverPeixe(linha, coluna, novaLinha, novaColuna);
            setPosicao(novaLinha, novaColuna);
            incrementarComidas();
            
            return true;
        }
        
        // Prioridade 2: Mover para celula livre
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(linha, coluna);
        
        if (celulaLivre != null) {
            int novaLinha = celulaLivre[0];
            int novaColuna = celulaLivre[1];
            
            aquario.moverPeixe(linha, coluna, novaLinha, novaColuna);
            setPosicao(novaLinha, novaColuna);
            incrementarSemAcao(); // Nao comeu, entao incrementa contador de fome
            
            return true;
        }
        
        // Nao pode fazer nada
        incrementarSemAcao();
        return false;
    }
    
    /**
     * Verifica se o peixe deve se reproduzir
     * @param rb Parametro de reproduçao
     * @param aquario O aquario
     * @return true se reproduziu, false caso contrario
     */
    public boolean verificarReproducao(int rb, Aquario aquario) {
        if (contadorComidas >= rb) {
            // Verificar se nao ha peixe B adjacente
            if (!aquario.temPeixeBAdjacente(linha, coluna)) {
                int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(linha, coluna);
                if (celulaLivre != null) {
                    // Reproduzir - criar filho na celula livre
                    PeixeB filho = new PeixeB(celulaLivre[0], celulaLivre[1]);
                    aquario.adicionarPeixe(filho, celulaLivre[0], celulaLivre[1]);
                    resetarContadorComidas();
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean deveMorrer(int mb) {
        return contadorSemAcao >= mb;
    }
    
    @Override
    public String toString() {
        return "B";
    }
}