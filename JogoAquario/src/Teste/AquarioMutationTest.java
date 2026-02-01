package Teste;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import aquario.Aquario;
import aquario.PeixeA;
import aquario.PeixeB;
import aquario.Celula;

public class AquarioMutationTest {
	
private Aquario aquario;
    
    @Before
    public void setUp() {
    	aquario = new Aquario(5, 5, 3, 5, 2, 5);
    }
    
    // ========== TESTES PARA MATAR MUTANTES DO MathMutator ==========
    
    /**
     * Testa operações matemáticas em direcoes[][] 
     * Mata mutante: linha + dir[0] -> linha - dir[0]
     */
   @Test
    public void testBuscaCelulaAdjacente_OperacoesMatematicas() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        // Peixe no centro
        PeixeA peixe = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixe, 2, 2);
        
        // Buscar célula adjacente
        int[] celula = aquario.buscarCelulaLivreAdjacente(2, 2);
        
        assertNotNull("Deve encontrar célula livre", celula);
        
        // Verificar que a célula está de fato adjacente (distância máx 1)
        int distLinha = Math.abs(celula[0] - 2);
        int distColuna = Math.abs(celula[1] - 2);
        
        assertTrue("Linha deve estar a distância máxima 1", distLinha <= 1);
        assertTrue("Coluna deve estar a distância máxima 1", distColuna <= 1);
    }
    
    /**
     * Testa operações aritméticas nos contadores
     * Mata mutantes de incremento/decremento
     */
    @Test
    public void testIncrementoContadores_PeixeA() {
        PeixeA peixe = new PeixeA(0, 0);
        
        // Estado inicial
        assertEquals("Contador movimentos deve iniciar em 0", 0, peixe.getContadorMovimentos());
        assertEquals("Contador sem ação deve iniciar em 0", 0, peixe.getContadorSemAcao());
        
        // Incrementar movimentos
        peixe.incrementarMovimentos();
        assertEquals("Contador movimentos deve ser 1", 1, peixe.getContadorMovimentos());
        assertEquals("Contador sem ação deve resetar para 0", 0, peixe.getContadorSemAcao());
        
        // Incrementar novamente
        peixe.incrementarMovimentos();
        assertEquals("Contador movimentos deve ser 2", 2, peixe.getContadorMovimentos());
        
        // Incrementar sem ação
        peixe.incrementarSemAcao();
        assertEquals("Contador movimentos deve resetar para 0", 0, peixe.getContadorMovimentos());
        assertEquals("Contador sem ação deve ser 1", 1, peixe.getContadorSemAcao());
    }
    
    /**
     * Testa operações aritméticas específicas para PeixeB
     */
    @Test
    public void testIncrementoContadores_PeixeB() {
        PeixeB peixe = new PeixeB(0, 0);
        
        // Estado inicial
        assertEquals("Contador comidas deve iniciar em 0", 0, peixe.getContadorComidas());
        assertEquals("Contador sem ação deve iniciar em 0", 0, peixe.getContadorSemAcao());
        
        // Incrementar comidas
        peixe.incrementarComidas();
        assertEquals("Contador comidas deve ser 1", 1, peixe.getContadorComidas());
        assertEquals("Contador sem ação deve resetar para 0", 0, peixe.getContadorSemAcao());
        
        // Incrementar comidas múltiplas vezes
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        assertEquals("Contador comidas deve ser 3", 3, peixe.getContadorComidas());
        
        // Resetar
        peixe.resetarContadorComidas();
        assertEquals("Contador comidas deve ser 0 após reset", 0, peixe.getContadorComidas());
    }
    
    /**
     * Testa cálculo de capacidade do aquário
     * Mata mutante: linhas * colunas -> linhas + colunas, etc.
     */
    @Test
    public void testCapacidadeAquario_CalculoCorreto() {
        aquario = new Aquario(3, 4, 1, 5, 1, 5);
        
        int capacidadeEsperada = 3 * 4; // 12
        int quantidadePeixes = 10;
        
        try {
            aquario.inicializarPeixes(5, 5); // Total: 10 peixes
            
            // Verificar que cabem
            assertEquals("Deve haver 5 peixes A", 5, aquario.getQuantidadePeixesA());
            assertEquals("Deve haver 5 peixes B", 5, aquario.getQuantidadePeixesB());
            
        } catch (IllegalArgumentException e) {
            fail("Não deve lançar exceção para 10 peixes em aquário 3x4");
        }
        
        // Tentar exceder capacidade
        try {
            Aquario aquario2 = new Aquario(3, 4, 1, 5, 1, 5);
            aquario2.inicializarPeixes(7, 6); // Total: 13 > 12
            fail("Deveria lançar exceção ao exceder capacidade");
        } catch (IllegalArgumentException e) {
            assertTrue("Mensagem deve conter '13 peixes para 12 celulas'",
                e.getMessage().contains("13 peixes para 12 celulas"));
        }
    }
    
    // ========== TESTES PARA MATAR MUTANTES DO BooleanFalseReturnValsMutator ==========
    
    /**
     * Testa retorno booleano de estaVazia()
     * Mata mutante: return true -> return false
     */
   @Test
    public void testCelula_EstaVazia_Verdadeiro() {
        Celula celula = new Celula();
        
        // Célula recém criada deve estar vazia
        assertTrue("Célula nova deve estar vazia", celula.estaVazia());
        assertNull("getPeixe() deve retornar null", celula.getPeixe());
        
        // Adicionar peixe
        celula.setPeixe(new PeixeA(0, 0));
        assertFalse("Célula com peixe não deve estar vazia", celula.estaVazia());
        
        // Limpar
        celula.limpar();
        assertTrue("Célula após limpar deve estar vazia", celula.estaVazia());
    }
    
    /**
     * Testa retorno booleano de executarAcao() do PeixeA
     * Mata mutante: return true -> return false
     */
    @Test
    public void testPeixeA_ExecutarAcao_RetornoVerdadeiro() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        PeixeA peixe = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixe, 1, 1);
        
        // Executar ação (deve mover)
        boolean resultado = peixe.executarAcao(aquario);
        
        assertTrue("executarAcao deve retornar true ao mover", resultado);
        assertEquals("Contador de movimentos deve incrementar", 1, peixe.getContadorMovimentos());
        assertEquals("Contador sem ação deve ser 0", 0, peixe.getContadorSemAcao());
    }
    
    /**
     * Testa retorno booleano de executarAcao() do PeixeB quando come
     * Mata mutante: return true -> return false
     */
   @Test
    public void testPeixeB_ExecutarAcao_ComePeixeA_RetornoVerdadeiro() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        // Posicionar peixes adjacentes
        PeixeA peixeA = new PeixeA(1, 1);
        PeixeB peixeB = new PeixeB(1, 2);
        
        aquario.adicionarPeixe(peixeA, 1, 1);
        aquario.adicionarPeixe(peixeB, 1, 2);
        
        assertEquals("Deve haver 1 peixe A", 1, aquario.getQuantidadePeixesA());
        
        // PeixeB come PeixeA
        boolean resultado = peixeB.executarAcao(aquario);
        
        assertTrue("executarAcao deve retornar true ao comer", resultado);
        assertEquals("Peixe A deve ter sido comido", 0, aquario.getQuantidadePeixesA());
        assertEquals("Contador de comidas deve incrementar", 1, peixeB.getContadorComidas());
    }
    
    /**
     * Testa retorno booleano de jogoTerminou()
     * Mata mutante: return true -> return false
     */
    @Test
    public void testJogoTerminou_Falso() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        aquario.inicializarPeixes(2, 2);
        
        // Com peixes B, jogo não terminou
        assertFalse("Jogo não deve ter terminado", aquario.jogoTerminou());
        assertEquals("Deve haver 2 peixes B", 2, aquario.getQuantidadePeixesB());
    }
    
    /**
     * Testa retorno booleano de jogoTerminou()
     * Mata mutante: return false -> return true quando isEmpty
     */
    @Test
    public void testJogoTerminou_Verdadeiro() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        aquario.inicializarPeixes(2, 1);
        
        // Remover todos os peixes B
        PeixeB peixeB = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!aquario.getMatriz()[i][j].estaVazia() &&
                    aquario.getMatriz()[i][j].getPeixe() instanceof PeixeB) {
                    aquario.removerPeixe(i, j);
                }
            }
        }
        
        assertEquals("Não deve haver peixes B", 0, aquario.getQuantidadePeixesB());
        assertTrue("Jogo deve ter terminado", aquario.jogoTerminou());
    }
    
    // ========== TESTES PARA MATAR MUTANTES DO ConditionalsBoundaryMutator ==========
    
    /**
     * Testa condição de borda: >= vs >
     * Mata mutante: >= RA -> > RA em verificarReproducao
     */
    @Test
    public void testPeixeA_VerificarReproducao_ExatamenteRA() {
        aquario = new Aquario(5, 5, 3, 5, 2, 5);
        
        PeixeA peixe = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixe, 2, 2);
        
        // Mover exatamente RA vezes (3)
        peixe.executarAcao(aquario);
        peixe.executarAcao(aquario);
        peixe.executarAcao(aquario);
        
        assertEquals("Contador deve ser exatamente 3", 3, peixe.getContadorMovimentos());
        
        int quantidadeAntes = aquario.getQuantidadePeixesA();
        boolean reproduziu = peixe.verificarReproducao(3, aquario);
        
        assertTrue("Deve reproduzir quando contador == RA", reproduziu);
        assertEquals("Deve ter criado novo peixe", quantidadeAntes + 1, 
            aquario.getQuantidadePeixesA());
    }
    
    /**
     * Testa condição de borda: >= vs > para RB
     * Mata mutante: >= RB -> > RB em verificarReproducao
     */
    @Test
    public void testPeixeB_VerificarReproducao_ExatamenteRB() {
        aquario = new Aquario(5, 5, 1, 5, 2, 5);
        
        PeixeB peixeB = new PeixeB(2, 2);
        aquario.adicionarPeixe(peixeB, 2, 2);
        
        // Simular que comeu exatamente RB vezes (2)
        peixeB.incrementarComidas();
        peixeB.incrementarComidas();
        
        assertEquals("Contador de comidas deve ser 2", 2, peixeB.getContadorComidas());
        
        int quantidadeAntes = aquario.getQuantidadePeixesB();
        boolean reproduziu = peixeB.verificarReproducao(2, aquario);
        
        assertTrue("Deve reproduzir quando contador == RB", reproduziu);
        assertEquals("Deve ter criado novo peixe", quantidadeAntes + 1,
            aquario.getQuantidadePeixesB());
    }
    
    /**
     * Testa condição de borda: >= vs > para MA
     * Mata mutante: >= MA -> > MA em deveMorrer
     */
    @Test
    public void testPeixeA_DeveMorrer_ExatamenteMA() {
        PeixeA peixe = new PeixeA(0, 0);
        
        // Incrementar exatamente MA vezes (3)
        peixe.incrementarSemAcao();
        peixe.incrementarSemAcao();
        peixe.incrementarSemAcao();
        
        assertEquals("Contador deve ser 3", 3, peixe.getContadorSemAcao());
        
        boolean deveMorrer = peixe.deveMorrer(3);
        
        assertTrue("Deve morrer quando contador == MA", deveMorrer);
    }
    
    /**
     * Testa condição de borda: >= vs > para MB
     * Mata mutante: >= MB -> > MB em deveMorrer
     */
    @Test
    public void testPeixeB_DeveMorrer_ExatamenteMB() {
        PeixeB peixe = new PeixeB(0, 0);
        
        // Incrementar exatamente MB vezes (4)
        peixe.incrementarSemAcao();
        peixe.incrementarSemAcao();
        peixe.incrementarSemAcao();
        peixe.incrementarSemAcao();
        
        assertEquals("Contador deve ser 4", 4, peixe.getContadorSemAcao());
        
        boolean deveMorrer = peixe.deveMorrer(4);
        
        assertTrue("Deve morrer quando contador == MB", deveMorrer);
    }
    
    /**
     * Testa condições de borda em posicaoValida
     * Mata mutantes: >= 0 -> > 0, < linhas -> <= linhas
     */
    @Test
    public void testPosicaoValida_Bordas() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        // Posição (0,0) - canto superior esquerdo - VÁLIDA
        PeixeA peixe1 = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixe1, 0, 0);
        int[] celula1 = aquario.buscarCelulaLivreAdjacente(0, 0);
        assertNotNull("Deve encontrar célula adjacente em (0,0)", celula1);
        
        // Posição (4,4) - canto inferior direito - VÁLIDA
        PeixeA peixe2 = new PeixeA(4, 4);
        aquario.adicionarPeixe(peixe2, 4, 4);
        int[] celula2 = aquario.buscarCelulaLivreAdjacente(4, 4);
        assertNotNull("Deve encontrar célula adjacente em (4,4)", celula2);
    }
   

}
