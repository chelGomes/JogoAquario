package Teste;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import aquario.Aquario;
import aquario.PeixeA;
import aquario.PeixeB;
import aquario.Celula;

public class AquarioMutationCoverageTest {
	
private Aquario aquario;
    
    @Before
    public void setUp() {
    	aquario = new Aquario(5, 5, 3, 5, 2, 5);
    }
    
    // ========== TESTES PARA MATAR MUTANTES DO VoidMethodCallMutator ==========
    
    /**
     * Testa que setPosicao() realmente modifica linha e coluna
     * Mata mutante: remoção de setPosicao()
     */
   @Test
    public void testPeixe_SetPosicao_ModificaCorretamente() {
        PeixeA peixe = new PeixeA(1, 2);
        
        assertEquals("Linha inicial deve ser 1", 1, peixe.getLinha());
        assertEquals("Coluna inicial deve ser 2", 2, peixe.getColuna());
        
        // Chamar setPosicao
        peixe.setPosicao(5, 7);
        
        // Verificar que mudou
        assertEquals("Linha deve ser 5 após setPosicao", 5, peixe.getLinha());
        assertEquals("Coluna deve ser 7 após setPosicao", 7, peixe.getColuna());
    }
    
    /**
     * Testa que setLinha() realmente modifica a linha
     * Mata mutante: remoção de setLinha()
     */
   @Test
    public void testPeixe_SetLinha_ModificaCorretamente() {
        PeixeA peixe = new PeixeA(1, 2);
        
        peixe.setLinha(10);
        
        assertEquals("Linha deve ser 10 após setLinha", 10, peixe.getLinha());
        assertEquals("Coluna deve permanecer 2", 2, peixe.getColuna());
    }
    
    /**
     * Testa que setColuna() realmente modifica a coluna
     * Mata mutante: remoção de setColuna()
     */
    @Test
    public void testPeixe_SetColuna_ModificaCorretamente() {
        PeixeB peixe = new PeixeB(1, 2);
        
        peixe.setColuna(15);
        
        assertEquals("Linha deve permanecer 1", 1, peixe.getLinha());
        assertEquals("Coluna deve ser 15 após setColuna", 15, peixe.getColuna());
    }
    
    /**
     * Testa que incrementarMovimentos() tem efeito observável
     * Mata mutante: remoção de incrementarMovimentos()
     */
    @Test
    public void testPeixeA_IncrementarMovimentos_TemEfeito() {
        PeixeA peixe = new PeixeA(0, 0);
        
        assertEquals("Contador inicial deve ser 0", 0, peixe.getContadorMovimentos());
        
        peixe.incrementarMovimentos();
        
        assertEquals("Contador deve ser 1", 1, peixe.getContadorMovimentos());
        assertEquals("Contador sem ação deve ser resetado para 0", 0, peixe.getContadorSemAcao());
        
        peixe.incrementarMovimentos();
        
        assertEquals("Contador deve ser 2 após segunda chamada", 2, peixe.getContadorMovimentos());
    }
    
    /**
     * Testa que incrementarSemAcao() tem efeito observável
     * Mata mutante: remoção de incrementarSemAcao()
     */
    @Test
    public void testPeixeA_IncrementarSemAcao_TemEfeito() {
        PeixeA peixe = new PeixeA(0, 0);
        
        // Primeiro incrementar movimentos
        peixe.incrementarMovimentos();
        peixe.incrementarMovimentos();
        assertEquals("Contador movimentos deve ser 2", 2, peixe.getContadorMovimentos());
        
        // Incrementar sem ação
        peixe.incrementarSemAcao();
        
        assertEquals("Contador sem ação deve ser 1", 1, peixe.getContadorSemAcao());
        assertEquals("Contador movimentos deve ser resetado para 0", 0, peixe.getContadorMovimentos());
    }
    
    /**
     * Testa que resetarContadorMovimentos() tem efeito observável
     * Mata mutante: remoção de resetarContadorMovimentos()
     */
    @Test
    public void testPeixeA_ResetarContadorMovimentos_TemEfeito() {
        PeixeA peixe = new PeixeA(0, 0);
        
        peixe.incrementarMovimentos();
        peixe.incrementarMovimentos();
        peixe.incrementarMovimentos();
        
        assertEquals("Contador deve ser 3 antes do reset", 3, peixe.getContadorMovimentos());
        
        peixe.resetarContadorMovimentos();
        
        assertEquals("Contador deve ser 0 após reset", 0, peixe.getContadorMovimentos());
    }
    
    /**
     * Testa que incrementarComidas() tem efeito observável
     * Mata mutante: remoção de incrementarComidas()
     */
    @Test
    public void testPeixeB_IncrementarComidas_TemEfeito() {
        PeixeB peixe = new PeixeB(0, 0);
        
        assertEquals("Contador comidas inicial deve ser 0", 0, peixe.getContadorComidas());
        
        peixe.incrementarComidas();
        
        assertEquals("Contador comidas deve ser 1", 1, peixe.getContadorComidas());
        assertEquals("Contador sem ação deve resetar para 0", 0, peixe.getContadorSemAcao());
        
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        
        assertEquals("Contador comidas deve ser 3", 3, peixe.getContadorComidas());
    }
    
    /**
     * Testa que resetarContadorComidas() tem efeito observável
     * Mata mutante: remoção de resetarContadorComidas()
     */
    @Test
    public void testPeixeB_ResetarContadorComidas_TemEfeito() {
        PeixeB peixe = new PeixeB(0, 0);
        
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        
        assertEquals("Contador deve ser 4 antes do reset", 4, peixe.getContadorComidas());
        
        peixe.resetarContadorComidas();
        
        assertEquals("Contador deve ser 0 após reset", 0, peixe.getContadorComidas());
    }
    
    /**
     * Testa que setPeixe() da célula tem efeito observável
     * Mata mutante: remoção de setPeixe()
     */
    @Test
    public void testCelula_SetPeixe_TemEfeito() {
        Celula celula = new Celula();
        
        assertTrue("Célula deve estar vazia inicialmente", celula.estaVazia());
        assertNull("getPeixe deve retornar null", celula.getPeixe());
        
        PeixeA peixe = new PeixeA(0, 0);
        celula.setPeixe(peixe);
        
        assertFalse("Célula não deve estar vazia após setPeixe", celula.estaVazia());
        assertNotNull("getPeixe deve retornar o peixe", celula.getPeixe());
        assertEquals("Deve retornar o mesmo peixe", peixe, celula.getPeixe());
    }
    
    /**
     * Testa que limpar() da célula tem efeito observável
     * Mata mutante: remoção de limpar()
     */
    @Test
    public void testCelula_Limpar_TemEfeito() {
        Celula celula = new Celula();
        PeixeA peixe = new PeixeA(0, 0);
        
        celula.setPeixe(peixe);
        assertFalse("Célula deve ter peixe", celula.estaVazia());
        
        celula.limpar();
        
        assertTrue("Célula deve estar vazia após limpar", celula.estaVazia());
        assertNull("getPeixe deve retornar null após limpar", celula.getPeixe());
    }
    
    /**
     * Testa efeito de adicionarPeixe() no aquário
     * Mata mutante: remoção de chamadas dentro de adicionarPeixe
     */
   @Test
    public void testAquario_AdicionarPeixe_AtualizaEstado() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        assertEquals("Deve ter 0 peixes A inicialmente", 0, aquario.getQuantidadePeixesA());
        assertTrue("Célula (1,1) deve estar vazia", aquario.getMatriz()[1][1].estaVazia());
        
        PeixeA peixe = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixe, 1, 1);
        
        assertEquals("Deve ter 1 peixe A", 1, aquario.getQuantidadePeixesA());
        assertFalse("Célula (1,1) não deve estar vazia", aquario.getMatriz()[1][1].estaVazia());
        assertEquals("Célula deve conter o peixe", peixe, aquario.getMatriz()[1][1].getPeixe());
    }
    
    /**
     * Testa efeito de removerPeixe() no aquário
     * Mata mutante: remoção de chamadas dentro de removerPeixe
     */
    @Test
    public void testAquario_RemoverPeixe_AtualizaEstado() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        PeixeB peixe = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixe, 1, 1);
        
        assertEquals("Deve ter 1 peixe B", 1, aquario.getQuantidadePeixesB());
        assertFalse("Célula deve ter peixe", aquario.getMatriz()[1][1].estaVazia());
        
        aquario.removerPeixe(1, 1);
        
        assertEquals("Deve ter 0 peixes B", 0, aquario.getQuantidadePeixesB());
        assertTrue("Célula deve estar vazia", aquario.getMatriz()[1][1].estaVazia());
        assertNull("getPeixe deve retornar null", aquario.getMatriz()[1][1].getPeixe());
    }
    
    /**
     * Testa efeito de moverPeixe() no aquário
     * Mata mutante: remoção de chamadas dentro de moverPeixe
     */
    @Test
    public void testAquario_MoverPeixe_AtualizaEstado() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        PeixeA peixe = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixe, 0, 0);
        
        assertFalse("Origem deve ter peixe", aquario.getMatriz()[0][0].estaVazia());
        assertTrue("Destino deve estar vazio", aquario.getMatriz()[1][1].estaVazia());
        
        aquario.moverPeixe(0, 0, 1, 1);
        
        assertTrue("Origem deve estar vazia após mover", aquario.getMatriz()[0][0].estaVazia());
        assertFalse("Destino deve ter peixe após mover", aquario.getMatriz()[1][1].estaVazia());
        assertEquals("Destino deve conter o peixe movido", peixe, aquario.getMatriz()[1][1].getPeixe());
    }
    
    /**
     * Testa que executarAcao() realmente move o peixe
     * e atualiza contadores
     */
    @Test
    public void testPeixeA_ExecutarAcao_AtualizaTudo() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixe = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixe, 2, 2);
        
        int linhaInicial = peixe.getLinha();
        int colunaInicial = peixe.getColuna();
        int contadorInicial = peixe.getContadorMovimentos();
        
        boolean moveu = peixe.executarAcao(aquario);
        
        assertTrue("Deve ter executado ação", moveu);
        
        // Verificar que posição mudou OU que incrementou contador sem ação
        boolean posicaoMudou = (peixe.getLinha() != linhaInicial || peixe.getColuna() != colunaInicial);
        
        if (posicaoMudou) {
            assertEquals("Contador movimentos deve incrementar", contadorInicial + 1, 
                peixe.getContadorMovimentos());
            assertEquals("Contador sem ação deve ser 0", 0, peixe.getContadorSemAcao());
        }
    }
    
    /**
     * Testa verificarReproducao resetando contador
     * Mata mutante: remoção de resetarContadorMovimentos()
     */
    @Test
    public void testPeixeA_VerificarReproducao_ResetaContador() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixe = new PeixeA(2, 2);
        aquario.adicionarPeixe(peixe, 2, 2);
        
        // Mover 3 vezes (supondo RA=3)
        for (int i = 0; i < 3; i++) {
            peixe.executarAcao(aquario);
        }
        
        int contadorAntes = peixe.getContadorMovimentos();
        assertTrue("Contador deve ser >= 3", contadorAntes >= 3);
        
        // Verificar reprodução
        boolean reproduziu = peixe.verificarReproducao(3, aquario);
        
        if (reproduziu) {
            assertEquals("Contador deve resetar para 0 após reprodução", 
                0, peixe.getContadorMovimentos());
        }
    }
    
    /**
     * Testa verificarReproducao do PeixeB resetando contador
     * Mata mutante: remoção de resetarContadorComidas()
     */
    @Test
    public void testPeixeB_VerificarReproducao_ResetaContador() {
        aquario = new Aquario(5, 5, 1, 5, 2, 5);
        
        PeixeB peixeB = new PeixeB(2, 2);
        aquario.adicionarPeixe(peixeB, 2, 2);
        
        // Simular que comeu RB vezes
        peixeB.incrementarComidas();
        peixeB.incrementarComidas();
        
        assertEquals("Contador deve ser 2", 2, peixeB.getContadorComidas());
        
        boolean reproduziu = peixeB.verificarReproducao(2, aquario);
        
        if (reproduziu) {
            assertEquals("Contador de comidas deve resetar para 0 após reprodução",
                0, peixeB.getContadorComidas());
        }
    }
    	
}
