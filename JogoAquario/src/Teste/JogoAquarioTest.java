package Teste;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import aquario.JogoAquario;
import aquario.Aquario;

/**
 * Testes da classe principal JogoAquario
 
 */
public class JogoAquarioTest {
    
    private JogoAquario jogo;
    
    @Before
    public void setUp() {
       
    }
    
    /**
     * Teste de criaçao do jogo com parametros validos
     */
    @Test
    public void testCriacaoJogoValido() {
        try {
            jogo = new JogoAquario(5, 5, 2, 1, 3, 3, 3, 3);
            assertNotNull("Jogo deve ser criado", jogo);
            assertNotNull("Aquario deve ser criado", jogo.getAquario());
            
            Aquario aquario = jogo.getAquario();
            assertEquals("Deve haver 2 peixes A", 2, aquario.getQuantidadePeixesA());
            assertEquals("Deve haver 1 peixe B", 1, aquario.getQuantidadePeixesB());
        } catch (IllegalArgumentException e) {
            fail("Nao deveria lançar exceçao: " + e.getMessage());
        }
    }
    
    /**
     * Teste de criacao do jogo com parametros invalidos
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCriacaoJogoInvalido_DimensaoMInvalida() {
        jogo = new JogoAquario(0, 5, 2, 1, 3, 3, 3, 3);
    }
    
    /**
     * Teste de criacao do jogo com excesso de peixes
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCriacaoJogoInvalido_ExcessoPeixes() {
        jogo = new JogoAquario(2, 2, 3, 2, 3, 3, 3, 3);
    }
    
    /**
     * Teste de execucao automatica do jogo
     */
    @Test
    public void testExecutarJogoAutomatico() {
        jogo = new JogoAquario(5, 5, 2, 1, 3, 3, 3, 3);
        
        int iteracoes = jogo.executarJogoAutomatico(10);
        
        assertTrue("Deve ter executado pelo menos 1 iteraçao ou jogo terminou", 
            iteracoes >= 0);
        assertTrue("Nao deve exceder limite de iteraçoes", iteracoes <= 10);
        assertEquals("Contador de iteraçoes deve corresponder", 
            iteracoes, jogo.getAquario().getIteracoes());
    }
    
    /**
     * Teste de execucao automatica ate fim do jogo
     */
    @Test
    public void testExecutarJogoAutomatico_AteOFim() {
        
        // Peixe B vai morrer de fome (MB=1) pois nao ha peixes A suficientes
        jogo = new JogoAquario(3, 3, 1, 1, 1, 3, 1, 1);
        
        int iteracoes = jogo.executarJogoAutomatico(50);
        
        assertTrue("Jogo deve ter terminado", jogo.getAquario().jogoTerminou());
        assertTrue("Deve ter executado algumas iteraçoes", iteracoes > 0);
    }
    
    /**
     * Teste que verifica se jogo nao trava em loop infinito
     */
    @Test(timeout = 5000) // Timeout de 5 segundos
    public void testJogoNaoTravaEmLoopInfinito() {
        jogo = new JogoAquario(5, 5, 3, 2, 2, 2, 2, 2);
        
        // Executar varias iteraçoes
        jogo.executarJogoAutomatico(100);
        
        // Se chegou aqui, nao travou
        assertTrue("Teste completou sem travar", true);
    }
    
    /**
     * Teste de aquario pequeno (valor limite)
     */
    @Test
    public void testAquarioPequeno_1x1() {
        try {
            jogo = new JogoAquario(1, 1, 1, 0, 1, 1, 1, 1);
            fail("Deveria lançar exceçao para Y=0");
        } catch (IllegalArgumentException e) {
            assertTrue("Mensagem deve mencionar Y invalido", 
                e.getMessage().contains("Y"));
        }
    }
    
    /**
     * Teste de aquario medio com multiplos peixes
     */
    @Test
    public void testAquarioMedio() {
        jogo = new JogoAquario(10, 10, 10, 5, 3, 3, 3, 3);
        
        Aquario aquario = jogo.getAquario();
        assertEquals("Deve haver 10 peixes A", 10, aquario.getQuantidadePeixesA());
        assertEquals("Deve haver 5 peixes B", 5, aquario.getQuantidadePeixesB());
        
       
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        
        assertEquals("Deve ter 5 iteraçoes", 5, aquario.getIteracoes());
    }
    
    /**
     * Teste de aquario grande
     */
    @Test
    public void testAquarioGrande() {
        jogo = new JogoAquario(20, 20, 50, 10, 5, 5, 5, 5);
        
        Aquario aquario = jogo.getAquario();
        assertEquals("Deve haver 50 peixes A", 50, aquario.getQuantidadePeixesA());
        assertEquals("Deve haver 10 peixes B", 10, aquario.getQuantidadePeixesB());
        
        int peixesIniciais = aquario.getQuantidadePeixesA() + aquario.getQuantidadePeixesB();
        assertTrue("Total de peixes deve estar dentro da capacidade", 
            peixesIniciais <= 400);
    }
    
    /**
     * Teste de diferentes configuraçoes de parametros
     */
    @Test
    public void testDiferentesConfiguracoes() {
        // Configuracao 1: Reproduçao rapida de A
        JogoAquario jogo1 = new JogoAquario(5, 5, 3, 1, 1, 5, 3, 5);
        assertNotNull(jogo1.getAquario());
        
        // Configuraçao 2: Morte rapida de A
        JogoAquario jogo2 = new JogoAquario(5, 5, 2, 1, 5, 1, 5, 5);
        assertNotNull(jogo2.getAquario());
        
        // Configuracao 3: Parametros balanceados
        JogoAquario jogo3 = new JogoAquario(7, 7, 5, 3, 3, 3, 3, 3);
        assertNotNull(jogo3.getAquario());
    }
    
    /**
     * Teste de consistencia do estado do aquario
     */
    @Test
    public void testConsistenciaEstadoAquario() {
        jogo = new JogoAquario(5, 5, 3, 2, 2, 2, 2, 2);
        Aquario aquario = jogo.getAquario();
        
        
        int peixesAInicial = aquario.getQuantidadePeixesA();
        int peixesBInicial = aquario.getQuantidadePeixesB();
        
       
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
            
            // Verificar que numeros de peixes sao nao-negativos
            assertTrue("Quantidade de peixes A deve ser >= 0", 
                aquario.getQuantidadePeixesA() >= 0);
            assertTrue("Quantidade de peixes B deve ser >= 0", 
                aquario.getQuantidadePeixesB() >= 0);
        }
    }
    
    /**
     * Teste de pontuaçao (numero de iteraçoes)
     */
    @Test
    public void testPontuacao() {
        jogo = new JogoAquario(4, 4, 2, 1, 2, 2, 2, 2);
        Aquario aquario = jogo.getAquario();
        
        assertEquals("Pontuaçao inicial deve ser 0", 0, aquario.getIteracoes());
        
        aquario.executarIteracao();
        assertEquals("Pontuaçao deve ser 1 apos 1 iteraçao", 1, aquario.getIteracoes());
        
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("Pontuacao deve ser 3 apos 3 iteraçoes", 3, aquario.getIteracoes());
    }
    
    /**
     * Teste de fim de jogo
     */
    @Test
    public void testFimDeJogo() {
        // Criar jogo que termina rapidamente
        jogo = new JogoAquario(2, 2, 1, 1, 1, 2, 1, 1);
        Aquario aquario = jogo.getAquario();
        
        assertFalse("Jogo nÃ£o deve ter terminado no inÃ­cio", aquario.jogoTerminou());
        
        
        jogo.executarJogoAutomatico(20);
        
        assertTrue("Jogo deve ter terminado", aquario.jogoTerminou());
        assertEquals("NÃ£o deve haver peixes B no fim", 0, aquario.getQuantidadePeixesB());
    }
    
    /**
     * Teste toString do aquario
     */
    @Test
    public void testToStringAquario() {
        jogo = new JogoAquario(3, 3, 1, 1, 2, 2, 2, 2);
        Aquario aquario = jogo.getAquario();
        
        String representacao = aquario.toString();
        
        assertNotNull("Representaçao nao deve ser null", representacao);
        assertTrue("Deve conter informaçao de iteraçao", 
            representacao.contains("Iteraçao"));
        assertTrue("Deve conter informaçao de peixes A", 
            representacao.contains("Peixes A"));
        assertTrue("Deve conter informaçao de peixes B", 
            representacao.contains("Peixes B"));
    }
}