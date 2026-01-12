package aquario;

/**
 * Classe abstrata que representa um peixe generico no aquario
 */
public abstract class Peixe {
    protected int linha;
    protected int coluna;
    protected int contadorMovimentos;
    protected int contadorSemAcao;
    
    public Peixe(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.contadorMovimentos = 0;
        this.contadorSemAcao = 0;
    }
    
    public int getLinha() {
        return linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    
    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public int getContadorMovimentos() {
        return contadorMovimentos;
    }
    
    public int getContadorSemAcao() {
        return contadorSemAcao;
    }
    
    public void incrementarMovimentos() {
        this.contadorMovimentos++;
        this.contadorSemAcao = 0; // Resetar contador de inatividade
    }
    
    public void incrementarSemAcao() {
        this.contadorSemAcao++;
        this.contadorMovimentos = 0; // Resetar contador de movimentos
    }
    
    public void resetarContadorMovimentos() {
        this.contadorMovimentos = 0;
    }
    
    /**
     * Metodo  para executar açao do peixe
     * @param aquario O aquario onde o peixe estao
     * @return true se executou alguma açao, false caso contrario
     */
    public abstract boolean executarAcao(Aquario aquario);
    
    /**
     * Metodo para verificar se o peixe deve morrer
     * @param parametroMorte Parametro MA ou MB
     * @return true se deve morrer, false caso contrario
     */
    public abstract boolean deveMorrer(int parametroMorte);
    
    @Override
    public abstract String toString();
}