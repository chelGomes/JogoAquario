package aquario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa o aquario onde os peixes vivem
 */
public class Aquario {
    private int linhas;
    private int colunas;
    private Celula[][] matriz;
    private List<PeixeA> peixesA;
    private List<PeixeB> peixesB;
    private int ra, ma, rb, mb;
    private int iteracoes;
    private Random random;
    
    public Aquario(int linhas, int colunas, int ra, int ma, int rb, int mb) {
        if (linhas < 1) {
            throw new IllegalArgumentException("Dimensão M invalida.");
        }
        if (colunas < 1) {
            throw new IllegalArgumentException("Dimensão N invalida.");
        }
        if (ra < 1) {
            throw new IllegalArgumentException("Parametro RA invalido.");
        }
        if (ma < 1) {
            throw new IllegalArgumentException("Parametro MA invalido.");
        }
        if (rb < 1) {
            throw new IllegalArgumentException("Parametro RB invalido.");
        }
        if (mb < 1) {
            throw new IllegalArgumentException("Parametro MB invalido.");
        }
        
        this.linhas = linhas;
        this.colunas = colunas;
        this.ra = ra;
        this.ma = ma;
        this.rb = rb;
        this.mb = mb;
        this.iteracoes = 0;
        this.random = new Random();
        
        this.matriz = new Celula[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = new Celula();
            }
        }
        
        this.peixesA = new ArrayList<PeixeA>();
        this.peixesB = new ArrayList<PeixeB>();
    }
    
    /**
     * Adiciona peixes no aquario
     */
    public void inicializarPeixes(int quantidadeA, int quantidadeB) {
        if (quantidadeA < 1) {
            throw new IllegalArgumentException("Quantidade X invalida.");
        }
        if (quantidadeB < 1) {
            throw new IllegalArgumentException("Quantidade Y invalida.");
        }
        if (quantidadeA + quantidadeB > linhas * colunas) {
            throw new IllegalArgumentException("Excesso de peixes (" + 
                (quantidadeA + quantidadeB) + " peixes para " + 
                (linhas * colunas) + " celulas).");
        }
        
        // Adicionar peixes do tipo A
        for (int i = 0; i < quantidadeA; i++) {
            int[] posicao = encontrarPosicaoLivre();
            if (posicao != null) {
                PeixeA peixe = new PeixeA(posicao[0], posicao[1]);
                adicionarPeixe(peixe, posicao[0], posicao[1]);
            }
        }
        
        // Adicionar peixes do tipo B
        for (int i = 0; i < quantidadeB; i++) {
            int[] posicao = encontrarPosicaoLivre();
            if (posicao != null) {
                PeixeB peixe = new PeixeB(posicao[0], posicao[1]);
                adicionarPeixe(peixe, posicao[0], posicao[1]);
            }
        }
    }
    
    /**
     * Encontra uma posição livre aleatória no aquario
     */
    private int[] encontrarPosicaoLivre() {
        List<int[]> posicoesLivres = new ArrayList<int[]>();
        
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (matriz[i][j].estaVazia()) {
                    posicoesLivres.add(new int[]{i, j});
                }
            }
        }
        
        if (posicoesLivres.isEmpty()) {
            return null;
        }
        
        return posicoesLivres.get(random.nextInt(posicoesLivres.size()));
    }
    
    /**
     * Adiciona um peixe na posição especificada
     */
    public void adicionarPeixe(Peixe peixe, int linha, int coluna) {
        matriz[linha][coluna].setPeixe(peixe);
        if (peixe instanceof PeixeA) {
            peixesA.add((PeixeA) peixe);
        } else if (peixe instanceof PeixeB) {
            peixesB.add((PeixeB) peixe);
        }
    }
    
    /**
     * Remove um peixe da posição especificada
     */
    public void removerPeixe(int linha, int coluna) {
        Peixe peixe = matriz[linha][coluna].getPeixe();
        if (peixe != null) {
            if (peixe instanceof PeixeA) {
                peixesA.remove(peixe);
            } else if (peixe instanceof PeixeB) {
                peixesB.remove(peixe);
            }
            matriz[linha][coluna].limpar();
        }
    }
    
    /**
     * Move um peixe de uma posição para outra
     */
    public void moverPeixe(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Peixe peixe = matriz[linhaOrigem][colunaOrigem].getPeixe();
        matriz[linhaDestino][colunaDestino].setPeixe(peixe);
        matriz[linhaOrigem][colunaOrigem].limpar();
    }
    
    /**
     * Busca celula livre adjacente (8 direções)
     */
    public int[] buscarCelulaLivreAdjacente(int linha, int coluna) {
        List<int[]> celulasLivres = new ArrayList<int[]>();
        
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : direcoes) {
            int novaLinha = linha + dir[0];
            int novaColuna = coluna + dir[1];
            
            if (posicaoValida(novaLinha, novaColuna) && matriz[novaLinha][novaColuna].estaVazia()) {
                celulasLivres.add(new int[]{novaLinha, novaColuna});
            }
        }
        
        if (celulasLivres.isEmpty()) {
            return null;
        }
        
        return celulasLivres.get(random.nextInt(celulasLivres.size()));
    }
    
    /**
     * Busca peixe do tipo A adjacente
     */
    public int[] buscarPeixeAAdjacente(int linha, int coluna) {
        List<int[]> posicoesPeixeA = new ArrayList<int[]>();
        
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : direcoes) {
            int novaLinha = linha + dir[0];
            int novaColuna = coluna + dir[1];
            
            if (posicaoValida(novaLinha, novaColuna)) {
                Peixe peixe = matriz[novaLinha][novaColuna].getPeixe();
                if (peixe instanceof PeixeA) {
                    posicoesPeixeA.add(new int[]{novaLinha, novaColuna});
                }
            }
        }
        
        if (posicoesPeixeA.isEmpty()) {
            return null;
        }
        
        return posicoesPeixeA.get(random.nextInt(posicoesPeixeA.size()));
    }
    
    /**
     * Verifica se ha peixe do tipo B adjacente
     */
    public boolean temPeixeBAdjacente(int linha, int coluna) {
        int[][] direcoes = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : direcoes) {
            int novaLinha = linha + dir[0];
            int novaColuna = coluna + dir[1];
            
            if (posicaoValida(novaLinha, novaColuna)) {
                Peixe peixe = matriz[novaLinha][novaColuna].getPeixe();
                if (peixe instanceof PeixeB) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se uma posição é valida no aquario
     */
    private boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }
    
    /**
     * Executa uma iteração do jogo
     */
    public void executarIteracao() { // Criar copias das listas para evitar ConcurrentModificationException
        List<PeixeA> peixesAAtual = new ArrayList<PeixeA>(peixesA);
        List<PeixeB> peixesBAtual = new ArrayList<PeixeB>(peixesB);
        
        for (PeixeB peixe : peixesBAtual) { // 1. Executar ações dos peixes B (prioridade: comer)
            if (peixesB.contains(peixe)) { // Verificar se ainda esta vivo
                peixe.executarAcao(this);
            }
        }
        
        for (PeixeA peixe : peixesAAtual) {// 2. Executar ações dos peixes A
            if (peixesA.contains(peixe)) { // Verificar se ainda esta vivo
                peixe.executarAcao(this);
            }
        }
        
        for (PeixeA peixe : peixesAAtual) {// 3. Verificar reproduções dos peixes A
            if (peixesA.contains(peixe)) {
                peixe.verificarReproducao(ra, this);
            }
        }
        
        for (PeixeB peixe : peixesBAtual) {// 4. Verificar reproduções dos peixes B
            if (peixesB.contains(peixe)) {
                peixe.verificarReproducao(rb, this);
            }
        }
        
        List<PeixeA> peixesAParaRemover = new ArrayList<PeixeA>();  // 5. Verificar mortes dos peixes A
        for (PeixeA peixe : peixesA) {
            if (peixe.deveMorrer(ma)) {
                peixesAParaRemover.add(peixe);
            }
        }
        for (PeixeA peixe : peixesAParaRemover) {
            removerPeixe(peixe.getLinha(), peixe.getColuna());
        }
        
        List<PeixeB> peixesBParaRemover = new ArrayList<PeixeB>(); // 6. Verificar mortes dos peixes B
        for (PeixeB peixe : peixesB) {
            if (peixe.deveMorrer(mb)) {
                peixesBParaRemover.add(peixe);
            }
        }
        for (PeixeB peixe : peixesBParaRemover) {
            removerPeixe(peixe.getLinha(), peixe.getColuna());
        }
        
        iteracoes++;
    }
    
    /**
     * Verifica se o jogo terminou (nao ha mais peixes B)
     */
    public boolean jogoTerminou() {
        return peixesB.isEmpty();
    }
    
    public int getIteracoes() {
        return iteracoes;
    }
    
    public int getQuantidadePeixesA() {
        return peixesA.size();
    }
    
    public int getQuantidadePeixesB() {
        return peixesB.size();
    }
    
    public int getLinhas() {
        return linhas;
    }
    
    public int getColunas() {
        return colunas;
    }
    
    public Celula[][] getMatriz() {
        return matriz;
    }
    
    /**
     * Retorna representação em String do aquario
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IteraÃ§Ã£o: ").append(iteracoes).append("\n");
        sb.append("Peixes A: ").append(peixesA.size()).append(" | Peixes B: ").append(peixesB.size()).append("\n");
        
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                sb.append(matriz[i][j].toString()).append(" ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}