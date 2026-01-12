package aquario;

/**
 * Peixe do Tipo A - Come plancton
 * Regras:
 * 1. Se houver celula livre, movimenta-se
 * 2. Se movimentar RA vezes seguidas, reproduz-se
 * 3. Se nao se movimentar MA vezes seguidas, morre de fome
 */
public class PeixeA extends Peixe {
    
    public PeixeA(int linha, int coluna) {
        super(linha, coluna);
    }
    
    @Override
    public boolean executarAcao(Aquario aquario) {
        // Verificar se ha celula livre ao redor
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(linha, coluna);
        
        if (celulaLivre != null) {
            // Ha celula livre - mover
            int novaLinha = celulaLivre[0];
            int novaColuna = celulaLivre[1];
            
            aquario.moverPeixe(linha, coluna, novaLinha, novaColuna);
            setPosicao(novaLinha, novaColuna);
            incrementarMovimentos();
            
            return true;
        } else {
            // Nao ha celula livre - incrementar contador de inatividade
            incrementarSemAcao();
            return false;
        }
    }
    
    /**
     * Verifica se o peixe deve se reproduzir
     * @param ra Parametro de reproduçao
     * @param aquario O aquario
     * @return true se reproduziu, false caso contrario
     */
    public boolean verificarReproducao(int ra, Aquario aquario) {
        if (contadorMovimentos >= ra) {
            int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(linha, coluna);
            if (celulaLivre != null) {
                // Reproduzir - criar filho na celula livre
                PeixeA filho = new PeixeA(celulaLivre[0], celulaLivre[1]);
                aquario.adicionarPeixe(filho, celulaLivre[0], celulaLivre[1]);
                resetarContadorMovimentos();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean deveMorrer(int ma) {
        return contadorSemAcao >= ma;
    }
    
    @Override
    public String toString() {
        return "A";
    }
}