package Teste;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import aquario.Aquario;
import aquario.PeixeA;
import aquario.PeixeB;

/**
 * Testes das regras de negocio do Jogo do Aquario
 * Cobre os cenarios funcionais: Morte por Fome, Alimentaçao e Reproduçao
 */
public class AquarioRegrasNegocioTest {
    
    private Aquario aquario;
    
    @Before
    public void setUp() {
        
    }
    
    /**
     * Cenario A: Morte por Fome (Peixe A)
     
     */
    @Test
    public void testCenarioA_MortePorFomePeixeA() {
        // Criar aquario 1x1 com apenas peixe A (bloqueado)
        aquario = new Aquario(1, 1, 1, 2, 1, 3);
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        assertEquals("Deve haver 1 peixe A inicialmente", 1, aquario.getQuantidadePeixesA());
        
        // Iteraçao 1: Peixe nao pode se mover (sem espaço)
        aquario.executarIteracao();
        assertEquals("Peixe A ainda deve estar vivo apos 1 iteraçao", 1, aquario.getQuantidadePeixesA());
        assertEquals("Contador de inatividade deve ser 1", 1, peixeA.getContadorSemAcao());
        
        // Iteraçao 2: Peixe ainda nao pode se mover
        aquario.executarIteracao();
        assertEquals("Peixe A deve morrer apos MA=2 iteraçoes sem mover", 0, aquario.getQuantidadePeixesA());
    }
    
    /**
     * Cenario A - Variante: Peixe A nao morre se conseguir se mover
     */
    @Test
    public void testCenarioA_PeixeANaoMorreSeMovimentar() {
        // Criar aquario 2x2 com peixe A (pode se mover)
        aquario = new Aquario(2, 2, 1, 2, 1, 3);
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        assertEquals("Deve haver 1 peixe A inicialmente", 1, aquario.getQuantidadePeixesA());
        
        // Executar 3 iteraaçoes (peixe deve se mover e nao morrer)
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        
        assertEquals("Peixe A nao deve morrer se conseguir se mover", 1, aquario.getQuantidadePeixesA());
        assertTrue("Contador de movimentos deve ser maior que 0", peixeA.getContadorMovimentos() > 0);
    }
    
    /**
     * Cenario B: Alimentaçao (Peixe B come A)
     
     */
    @Test
    public void testCenarioB_PeixeBComeA() {
        // Criar aquario 2x2 com peixe A e peixe B adjacentes
        aquario = new Aquario(2, 2, 1, 1, 2, 3);
        
        PeixeA peixeA = new PeixeA(0, 0);
        PeixeB peixeB = new PeixeB(0, 1);
        
        aquario.adicionarPeixe(peixeA, 0, 0);
        aquario.adicionarPeixe(peixeB, 0, 1);
        
        assertEquals("Deve haver 1 peixe A inicialmente", 1, aquario.getQuantidadePeixesA());
        assertEquals("Deve haver 1 peixe B inicialmente", 1, aquario.getQuantidadePeixesB());
        assertEquals("Contador de comidas do B deve ser 0", 0, peixeB.getContadorComidas());
        
        // Executar uma iteraçao
        aquario.executarIteracao();
        
        assertEquals("Peixe A deve ter sido comido", 0, aquario.getQuantidadePeixesA());
        assertEquals("Peixe B ainda deve estar vivo", 1, aquario.getQuantidadePeixesB());
        assertEquals("Contador de comidas do B deve ser 1", 1, peixeB.getContadorComidas());
    }
    
    /**
     * Cenario B - Variante: Peixe B come multiplos peixes A
     */
    @Test
    public void testCenarioB_PeixeBComeMultiplosA() {
        // Criar aquario 3x3 com 1 peixe B e 2 peixes A
        aquario = new Aquario(3, 3, 1, 1, 2, 5);
        
        PeixeA peixeA1 = new PeixeA(0, 0);
        PeixeA peixeA2 = new PeixeA(2, 2);
        PeixeB peixeB = new PeixeB(1, 1);
        
        aquario.adicionarPeixe(peixeA1, 0, 0);
        aquario.adicionarPeixe(peixeA2, 2, 2);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        int peixesAIniciais = aquario.getQuantidadePeixesA();
        
        // Executar iteraçoes ate B comer pelo menos 1 peixe
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
            if (aquario.getQuantidadePeixesA() < peixesAIniciais) {
                break;
            }
        }
        
        assertTrue("Pelo menos um peixe A deve ter sido comido", 
            aquario.getQuantidadePeixesA() < peixesAIniciais);
        assertTrue("Contador de comidas do B deve ser maior que 0", 
            peixeB.getContadorComidas() > 0);
    }
    
    /**
     * Cenario C: Reproduçao (Peixe A)
     
     */
    @Test
    public void testCenarioC_ReproducaoPeixeA() {
        // Criar aquario 3x3 com 1 peixe A, RA=2
        aquario = new Aquario(3, 3, 2, 5, 2, 5);
        
        PeixeA peixeA = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixeA, 1, 1);
        
        assertEquals("Deve haver 1 peixe A inicialmente", 1, aquario.getQuantidadePeixesA());
        
        // Executar iteraçoes ate reproduçao acontecer
        // Apos 2 movimentos consecutivos, deve reproduzir
        int peixesAntesReproducao = aquario.getQuantidadePeixesA();
        
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
            
            // Verificar se houve reproduçao
            if (aquario.getQuantidadePeixesA() > peixesAntesReproducao) {
                break;
            }
        }
        
        assertTrue("Deve haver mais peixes A apos reproduçao", 
            aquario.getQuantidadePeixesA() > peixesAntesReproducao);
    }
    
    /**
     * Cenario C - Variante: Peixe A nao reproduz sem espaço livre
     */
    @Test
    public void testCenarioC_PeixeANaoReproduzSemEspaco() {
        // Criar aquario 2x2 cheio (sem espaço para reproduçao)
        aquario = new Aquario(2, 2, 1, 5, 2, 5);
        
        PeixeA peixeA1 = new PeixeA(0, 0);
        PeixeA peixeA2 = new PeixeA(0, 1);
        PeixeA peixeA3 = new PeixeA(1, 0);
        PeixeB peixeB = new PeixeB(1, 1);
        
        aquario.adicionarPeixe(peixeA1, 0, 0);
        aquario.adicionarPeixe(peixeA2, 0, 1);
        aquario.adicionarPeixe(peixeA3, 1, 0);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        int peixesAIniciais = aquario.getQuantidadePeixesA();
        
     
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        
       
        assertTrue("Quantidade de peixes A nao deve aumentar sem espaço", 
            aquario.getQuantidadePeixesA() <= peixesAIniciais);
    }
    
    /**
     * Cenario D: ReproduÃ§Ã£o (Peixe B)
     
     */
    @Test
    public void testCenarioD_ReproducaoPeixeB() {
        // Criar aquario 4x4 com peixe B e varios peixes A, RB=2
        aquario = new Aquario(4, 4, 1, 5, 2, 5);
        
        // Adicionar peixe B no centro
        PeixeB peixeB = new PeixeB(2, 2);
        aquario.adicionarPeixe(peixeB, 2, 2);
        
        // Adicionar peixes A ao redor
        PeixeA peixeA1 = new PeixeA(1, 1);
        PeixeA peixeA2 = new PeixeA(1, 2);
        PeixeA peixeA3 = new PeixeA(1, 3);
        
        aquario.adicionarPeixe(peixeA1, 1, 1);
        aquario.adicionarPeixe(peixeA2, 1, 2);
        aquario.adicionarPeixe(peixeA3, 1, 3);
        
        int peixesBIniciais = aquario.getQuantidadePeixesB();
        
       
        for (int i = 0; i < 15; i++) {
            aquario.executarIteracao();
            
            if (aquario.getQuantidadePeixesB() > peixesBIniciais) {
                break;
            }
        }
        
        assertTrue("Deve haver mais peixes B apos comer RB peixes A", 
            aquario.getQuantidadePeixesB() > peixesBIniciais);
    }
    
    /**
     * Cenario E: Morte por Fome (Peixe B)
    
     */
    @Test
    public void testCenarioE_MortePorFomePeixeB() {
        // Criar aquario 3x3 apenas com peixe B (sem peixes A), MB=2
        aquario = new Aquario(3, 3, 1, 5, 2, 2);
        
        PeixeB peixeB = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        assertEquals("Deve haver 1 peixe B inicialmente", 1, aquario.getQuantidadePeixesB());
        
        // Iteraçao 1: Peixe B nao come (sem peixes A)
        aquario.executarIteracao();
        assertEquals("Peixe B ainda deve estar vivo apos 1 iteraçao", 1, aquario.getQuantidadePeixesB());
        
        // Iteraçao 2: Peixe B ainda nao come
        aquario.executarIteracao();
        assertEquals("Peixe B deve morrer apos MB=2 iteraçoes sem comer", 0, aquario.getQuantidadePeixesB());
    }
    
    /**
     * Teste do fim do jogo quando nao ha mais peixes B
     */
    @Test
    public void testFimDoJogo_SemPeixesB() {
        aquario = new Aquario(2, 2, 1, 5, 2, 1);
        
        
        PeixeB peixeB = new PeixeB(0, 0);
        aquario.adicionarPeixe(peixeB, 0, 0);
        
        assertFalse("Jogo nao deve ter terminado inicialmente", aquario.jogoTerminou());
        
        
        aquario.executarIteracao();
        aquario.executarIteracao();
        
        assertTrue("Jogo deve terminar quando nao ha peixes B", aquario.jogoTerminou());
    }
    
    /**
     * Teste de iteraçoes e pontuaçao
     */
    @Test
    public void testIteracoesEPontuacao() {
        aquario = new Aquario(3, 3, 1, 3, 2, 3);
        aquario.inicializarPeixes(2, 1);
        
        assertEquals("Iteraçoes devem começar em 0", 0, aquario.getIteracoes());
        
        aquario.executarIteracao();
        assertEquals("Deve ter 1 iteraçao apos executar", 1, aquario.getIteracoes());
        
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("Deve ter 3 iteraçoes", 3, aquario.getIteracoes());
    }
    
    /**
     * Teste de movimentaçao basica de peixe A
     */
    @Test
    public void testMovimentacaoPeixeA() {
        aquario = new Aquario(3, 3, 1, 5, 2, 5);
        
        PeixeA peixeA = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixeA, 1, 1);
        
        int linhaInicial = peixeA.getLinha();
        int colunaInicial = peixeA.getColuna();
        
        // Executar uma iteraçao
        aquario.executarIteracao();
        
        
        boolean seMoveouOuIncrementou = 
            (peixeA.getLinha() != linhaInicial || peixeA.getColuna() != colunaInicial) ||
            (peixeA.getContadorMovimentos() > 0 || peixeA.getContadorSemAcao() > 0);
        
        assertTrue("Peixe deve ter executado alguma açao", seMoveouOuIncrementou);
    }
}