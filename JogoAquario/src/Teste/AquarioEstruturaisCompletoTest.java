package Teste;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import aquario.Aquario;
import aquario.PeixeA;
import aquario.PeixeB;
import aquario.Celula;

/**
 * Testes Estruturais Adicionais (TestSet-Estr)
 * Criados para aumentar cobertura ap√≥s an√°lise com EclEmma e Badu√≠no
 * 
 * Objetivo: Cobrir branches, n√≥s e caminhos def-uso n√£o exercitados
 * pelos testes funcionais
 */
public class AquarioEstruturaisCompletoTest {
    
    private Aquario aquario;
    
    @Before
    public void setUp() {
        // Setup executado antes de cada teste
    }
    
    // ========== TESTES DE COBERTURA DE BRANCHES ==========
    
    /**
     * Testa branch: buscarCelulaLivreAdjacente retorna null
     * quando n√£o h√° c√©lulas livres
     * Cobre: Branch n√£o coberto em Aquario.buscarCelulaLivreAdjacente()
     */
    @Test
    public void testBuscarCelulaLivreAdjacente_SemCelulasLivres() {
        aquario = new Aquario(2, 2, 1, 1, 1, 1);
        
        // Preencher todas as c√©lulas
        aquario.adicionarPeixe(new PeixeA(0, 0), 0, 0);
        aquario.adicionarPeixe(new PeixeA(0, 1), 0, 1);
        aquario.adicionarPeixe(new PeixeA(1, 0), 1, 0);
        aquario.adicionarPeixe(new PeixeB(1, 1), 1, 1);
        
        // Buscar c√©lula livre (n√£o deve encontrar)
        int[] resultado = aquario.buscarCelulaLivreAdjacente(1, 1);
        
        assertNull("N„o deve encontrar celula livre quando aquario esta cheio", resultado);
    }
    
    /**
     * Testa branch: buscarPeixeAAdjacente retorna null
     * quando n√£o h√° peixes A adjacentes
     * Cobre: Branch false em Aquario.buscarPeixeAAdjacente()
     */
    @Test
    public void testBuscarPeixeAAdjacente_SemPeixesA() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        PeixeB peixeB = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        int[] resultado = aquario.buscarPeixeAAdjacente(1, 1);
        
        assertNull("N„o deve encontrar peixe A quando n„o h· nenhum adjacente", resultado);
    }
    
    /**
     * Testa branch: temPeixeBAdjacente retorna false
     * Cobre: Retorno false em Aquario.temPeixeBAdjacente()
     */
    @Test
    public void testTemPeixeBAdjacente_Falso() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        PeixeB peixeB = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        boolean resultado = aquario.temPeixeBAdjacente(1, 1);
        
        assertFalse("N„o deve ter peixe B adjacente", resultado);
    }
    
    /**
     * Testa branch: temPeixeBAdjacente retorna true
     * Cobre: Retorno true em Aquario.temPeixeBAdjacente()
     */
    @Test
    public void testTemPeixeBAdjacente_Verdadeiro() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        PeixeB peixeB1 = new PeixeB(1, 1);
        PeixeB peixeB2 = new PeixeB(1, 2);
        aquario.adicionarPeixe(peixeB1, 1, 1);
        aquario.adicionarPeixe(peixeB2, 1, 2);
        
        boolean resultado = aquario.temPeixeBAdjacente(1, 1);
        
        assertTrue("Deve ter peixe B adjacente", resultado);
    }
    
    /**
     * Testa branch: PeixeA.executarAcao retorna false
     * quando n√£o h√° c√©lula livre
     * Cobre: Branch else em PeixeA.executarAcao()
     */
    @Test
    public void testPeixeA_ExecutarAcao_SemCelulaLivre() {
        aquario = new Aquario(1, 1, 1, 2, 1, 1);
        
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        boolean resultado = peixeA.executarAcao(aquario);
        
        assertFalse("PeixeA n„o deve executar aÁ„o quando bloqueado", resultado);
        assertEquals("Contador de inatividade deve ser 1", 1, peixeA.getContadorSemAcao());
    }
    
    /**
     * Testa branch: PeixeA.verificarReproducao quando contadorMovimentos < RA
     * Cobre: Branch false da primeira condicional
     */
    @Test
    public void testPeixeA_VerificarReproducao_ContadorInsuficiente() {
        aquario = new Aquario(3, 3, 3, 5, 2, 5);
        
        PeixeA peixeA = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixeA, 1, 1);
        
        // Mover apenas 1 vez (RA = 3)
        peixeA.executarAcao(aquario);
        
        boolean reproduziu = peixeA.verificarReproducao(3, aquario);
        
        assertFalse("N„o deve reproduzir com contador insuficiente", reproduziu);
    }
    
    /**
     * Testa branch: PeixeA.verificarReproducao sem c√©lula livre adjacente
     * Cobre: Branch onde celulaLivre == null dentro de verificarReproducao
     */
    @Test
    public void testPeixeA_VerificarReproducao_SemCelulaLivre() {
        aquario = new Aquario(2, 2, 1, 5, 1, 5);
        
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        // Preencher c√©lulas adjacentes
        aquario.adicionarPeixe(new PeixeB(0, 1), 0, 1);
        aquario.adicionarPeixe(new PeixeB(1, 0), 1, 0);
        aquario.adicionarPeixe(new PeixeB(1, 1), 1, 1);
        
        // ForÁar contador de movimentos
        peixeA.incrementarMovimentos();
        
        boolean reproduziu = peixeA.verificarReproducao(1, aquario);
        
        assertFalse("N„o deve reproduzir sem celula livre", reproduziu);
    }
    
    /**
     * Testa branch: PeixeA.deveMorrer retorna false
     * Cobre: Branch < em deveMorrer
     */
    @Test
    public void testPeixeA_DeveMorrer_Falso() {
        PeixeA peixeA = new PeixeA(0, 0);
        
        peixeA.incrementarSemAcao();
        
        boolean deveMorrer = peixeA.deveMorrer(5);
        
        assertFalse("N„o deve morrer quando contador < MA", deveMorrer);
    }
    
    /**
     * Testa branch: PeixeA.deveMorrer retorna true
     * Cobre: Branch >= em deveMorrer
     */
    @Test
    public void testPeixeA_DeveMorrer_Verdadeiro() {
        PeixeA peixeA = new PeixeA(0, 0);
        
        peixeA.incrementarSemAcao();
        peixeA.incrementarSemAcao();
        peixeA.incrementarSemAcao();
        
        boolean deveMorrer = peixeA.deveMorrer(3);
        
        assertTrue("Deve morrer quando contador >= MA", deveMorrer);
    }
    
    /**
     * Testa branch: PeixeB.executarAcao move para c√©lula livre
     * quando n√£o h√° peixe A adjacente
     * Cobre: Segundo if em PeixeB.executarAcao()
     */
    @Test
    public void testPeixeB_ExecutarAcao_MoveCelulaLivre() {
        aquario = new Aquario(3, 3, 1, 5, 2, 5);
        
        PeixeB peixeB = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        boolean resultado = peixeB.executarAcao(aquario);
        
        assertTrue("PeixeB deve mover para celula livre", resultado);
        assertTrue("Contador de fome deve ter incrementado", peixeB.getContadorSemAcao() > 0);
    }
    
    /**
     * Testa branch: PeixeB.executarAcao retorna false
     * quando n√£o pode fazer nada
     * Cobre: √öltimo branch (sem c√©lula livre, sem peixe A)
     */
    @Test
    public void testPeixeB_ExecutarAcao_Bloqueado() {
        aquario = new Aquario(1, 1, 1, 5, 1, 5);
        
        PeixeB peixeB = new PeixeB(0, 0);
        aquario.adicionarPeixe(peixeB, 0, 0);
        
        boolean resultado = peixeB.executarAcao(aquario);
        
        assertFalse("PeixeB n„o deve executar aÁ„o quando bloqueado", resultado);
        assertEquals("Contador de fome deve ser 1", 1, peixeB.getContadorSemAcao());
    }
    
    /**
     * Testa branch: PeixeB.verificarReproducao com peixe B adjacente
     * Cobre: Branch onde temPeixeBAdjacente() == true
     */
    @Test
    public void testPeixeB_VerificarReproducao_ComPeixeBAdjacente() {
        aquario = new Aquario(3, 3, 1, 5, 1, 5);
        
        PeixeB peixeB1 = new PeixeB(1, 1);
        PeixeB peixeB2 = new PeixeB(1, 2);
        aquario.adicionarPeixe(peixeB1, 1, 1);
        aquario.adicionarPeixe(peixeB2, 1, 2);
        
        // For√ßar contador de comidas
        peixeB1.incrementarComidas();
        
        boolean reproduziu = peixeB1.verificarReproducao(1, aquario);
        
        assertFalse("N„o deve reproduzir com peixe B adjacente", reproduziu);
    }
    
    /**
     * Testa branch: PeixeB.verificarReproducao sem c√©lula livre
     * Cobre: Branch onde celulaLivre == null
     */
    @Test
    public void testPeixeB_VerificarReproducao_SemCelulaLivre() {
        aquario = new Aquario(2, 2, 1, 5, 1, 5);
        
        PeixeB peixeB = new PeixeB(0, 0);
        aquario.adicionarPeixe(peixeB, 0, 0);
        
        // Preencher c√©lulas adjacentes
        aquario.adicionarPeixe(new PeixeA(0, 1), 0, 1);
        aquario.adicionarPeixe(new PeixeA(1, 0), 1, 0);
        aquario.adicionarPeixe(new PeixeA(1, 1), 1, 1);
        
        // For√ßar contador de comidas
        peixeB.incrementarComidas();
        
        boolean reproduziu = peixeB.verificarReproducao(1, aquario);
        
        assertFalse("N„o deve reproduzir sem celula livre", reproduziu);
    }
    
    /**
     * Testa branch: PeixeB.verificarReproducao com contador insuficiente
     * Cobre: Branch contadorComidas < RB
     */
    @Test
    public void testPeixeB_VerificarReproducao_ContadorInsuficiente() {
        aquario = new Aquario(3, 3, 1, 5, 3, 5);
        
        PeixeB peixeB = new PeixeB(1, 1);
        aquario.adicionarPeixe(peixeB, 1, 1);
        
        // Comer apenas 1 vez (RB = 3)
        peixeB.incrementarComidas();
        
        boolean reproduziu = peixeB.verificarReproducao(3, aquario);
        
        assertFalse("N„o deve reproduzir com contador insuficiente", reproduziu);
    }
    
    /**
     * Testa branch: PeixeB.deveMorrer retorna false
     */
    @Test
    public void testPeixeB_DeveMorrer_Falso() {
        PeixeB peixeB = new PeixeB(0, 0);
        
        peixeB.incrementarSemAcao();
        
        boolean deveMorrer = peixeB.deveMorrer(5);
        
        assertFalse("N„o deve morrer quando contador < MB", deveMorrer);
    }
    
    /**
     * Testa branch: PeixeB.deveMorrer retorna true
     */
    @Test
    public void testPeixeB_DeveMorrer_Verdadeiro() {
        PeixeB peixeB = new PeixeB(0, 0);
        
        peixeB.incrementarSemAcao();
        peixeB.incrementarSemAcao();
        peixeB.incrementarSemAcao();
        
        boolean deveMorrer = peixeB.deveMorrer(3);
        
        assertTrue("Deve morrer quando contador >= MB", deveMorrer);
    }
    
    // ========== TESTES DE POSI√á√ïES NA BORDA ==========
    
    /**
     * Testa peixe no canto superior esquerdo (0,0)
     * Cobre: Valida√ß√µes de borda em buscarCelulaLivreAdjacente
     */
    @Test
    public void testPeixe_CantoSuperiorEsquerdo() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(0, 0);
        
        assertNotNull("Deve encontrar celula livre adjacente", celulaLivre);
        assertTrue("Celula deve estar dentro dos limites", 
            celulaLivre[0] >= 0 && celulaLivre[0] < 5 &&
            celulaLivre[1] >= 0 && celulaLivre[1] < 5);
    }
    
    /**
     * Testa peixe no canto inferior direito (M-1, N-1)
     * Cobre: Valida√ß√µes de borda inferior
     */
    @Test
    public void testPeixe_CantoInferiorDireito() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixeA = new PeixeA(4, 4);
        aquario.adicionarPeixe(peixeA, 4, 4);
        
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(4, 4);
        
        assertNotNull("Deve encontrar celula livre adjacente", celulaLivre);
    }
    
    /**
     * Testa peixe na borda superior
     */
    @Test
    public void testPeixe_BordaSuperior() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixeA = new PeixeA(0, 2);
        aquario.adicionarPeixe(peixeA, 0, 2);
        
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(0, 2);
        
        assertNotNull("Deve encontrar celula livre adjacente", celulaLivre);
    }
    
    /**
     * Testa peixe na borda esquerda
     */
    @Test
    public void testPeixe_BordaEsquerda() {
        aquario = new Aquario(5, 5, 1, 5, 1, 5);
        
        PeixeA peixeA = new PeixeA(2, 0);
        aquario.adicionarPeixe(peixeA, 2, 0);
        
        int[] celulaLivre = aquario.buscarCelulaLivreAdjacente(2, 0);
        
        assertNotNull("Deve encontrar celula livre adjacente", celulaLivre);
    }
    
    // ========== TESTES DE M√âTODOS AUXILIARES ==========
    
    /**
     * Testa metodo toString() da classe Celula vazia
     */
    @Test
    public void testCelula_ToString_Vazia() {
        Celula celula = new Celula();
        
        assertEquals("Celula vazia deve retornar '.'", ".", celula.toString());
    }
    
    /**
     * Testa metodo toString() da classe Celula com peixe A
     */
    @Test
    public void testCelula_ToString_ComPeixeA() {
        Celula celula = new Celula();
        PeixeA peixeA = new PeixeA(0, 0);
        celula.setPeixe(peixeA);
        
        assertEquals("Celula com peixe A deve retornar 'A'", "A", celula.toString());
    }
    
    /**
     * Testa metodo toString() da classe Celula com peixe B
     */
    @Test
    public void testCelula_ToString_ComPeixeB() {
        Celula celula = new Celula();
        PeixeB peixeB = new PeixeB(0, 0);
        celula.setPeixe(peixeB);
        
        assertEquals("Celula com peixe B deve retornar 'B'", "B", celula.toString());
    }
    
    /**
     * Testa metodo limpar() da classe Celula
     */
    @Test
    public void testCelula_Limpar() {
        Celula celula = new Celula();
        celula.setPeixe(new PeixeA(0, 0));
        
        assertFalse("Celula n„o deve estar vazia antes de limpar", celula.estaVazia());
        
        celula.limpar();
        
        assertTrue("Celula deve estar vazia apos limpar", celula.estaVazia());
        assertNull("getPeixe() deve retornar null", celula.getPeixe());
    }
    
    /**
     * Testa getters e setters de PeixeA
     */
    @Test
    public void testPeixeA_GettersSetters() {
        PeixeA peixe = new PeixeA(1, 2);
        
        assertEquals("Linha inicial deve ser 1", 1, peixe.getLinha());
        assertEquals("Coluna inicial deve ser 2", 2, peixe.getColuna());
        
        peixe.setLinha(3);
        peixe.setColuna(4);
        
        assertEquals("Linha deve ser 3", 3, peixe.getLinha());
        assertEquals("Coluna deve ser 4", 4, peixe.getColuna());
        
        peixe.setPosicao(5, 6);
        
        assertEquals("Linha deve ser 5", 5, peixe.getLinha());
        assertEquals("Coluna deve ser 6", 6, peixe.getColuna());
    }
    
    /**
     * Testa resetarContadorMovimentos
     */
    @Test
    public void testPeixeA_ResetarContadorMovimentos() {
        PeixeA peixe = new PeixeA(0, 0);
        
        peixe.incrementarMovimentos();
        peixe.incrementarMovimentos();
        
        assertEquals("Contador deve ser 2", 2, peixe.getContadorMovimentos());
        
        peixe.resetarContadorMovimentos();
        
        assertEquals("Contador deve ser 0 aposs reset", 0, peixe.getContadorMovimentos());
    }
    
    /**
     * Testa resetarContadorComidas
     */
    @Test
    public void testPeixeB_ResetarContadorComidas() {
        PeixeB peixe = new PeixeB(0, 0);
        
        peixe.incrementarComidas();
        peixe.incrementarComidas();
        
        assertEquals("Contador de comidas deve ser 2", 2, peixe.getContadorComidas());
        
        peixe.resetarContadorComidas();
        
        assertEquals("Contador deve ser 0 apos reset", 0, peixe.getContadorComidas());
    }
    
    /**
     * Testa toString() dos peixes
     */
    @Test
    public void testPeixes_ToString() {
        PeixeA peixeA = new PeixeA(0, 0);
        PeixeB peixeB = new PeixeB(0, 0);
        
        assertEquals("PeixeA toString deve ser 'A'", "A", peixeA.toString());
        assertEquals("PeixeB toString deve ser 'B'", "B", peixeB.toString());
    }
    
    // ========== TESTES DE CASOS EXTREMOS ==========
    
    /**
     * Testa aqu√°rio retangular (n√£o quadrado)
     */
    @Test
    public void testAquario_Retangular() {
        aquario = new Aquario(2, 5, 1, 5, 1, 5);
        aquario.inicializarPeixes(3, 2);
        
        assertEquals("Deve ter 2 linhas", 2, aquario.getLinhas());
        assertEquals("Deve ter 5 colunas", 5, aquario.getColunas());
        assertEquals("Deve haver 3 peixes A", 3, aquario.getQuantidadePeixesA());
        assertEquals("Deve haver 2 peixes B", 2, aquario.getQuantidadePeixesB());
    }
    
    /**
     * Testa jogo com parametros extremos (todos = 1)
     */
    @Test
    public void testParametrosExtremos_TodosUm() {
        aquario = new Aquario(5, 5, 1, 1, 1, 1);
        aquario.inicializarPeixes(3, 2);
        
        // Executar algumas itera√ß√µes
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        
        // Verificar que o jogo n√£o travou
        assertTrue("Jogo deve ter executado", aquario.getIteracoes() == 10);
    }
    
    /**
     * Testa metodo removerPeixe
     * Cobre: Chamada direta de removerPeixe
     */
    @Test
    public void testAquario_RemoverPeixe() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        PeixeA peixeA = new PeixeA(1, 1);
        aquario.adicionarPeixe(peixeA, 1, 1);
        
        assertEquals("Deve haver 1 peixe A", 1, aquario.getQuantidadePeixesA());
        
        aquario.removerPeixe(1, 1);
        
        assertEquals("N„o deve haver peixes A", 0, aquario.getQuantidadePeixesA());
    }
    
    /**
     * Testa metodo moverPeixe diretamente
     * Cobre: Chamada direta de moverPeixe
     */
    @Test
    public void testAquario_MoverPeixe() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        PeixeA peixeA = new PeixeA(0, 0);
        aquario.adicionarPeixe(peixeA, 0, 0);
        
        aquario.moverPeixe(0, 0, 1, 1);
        
        // Verificar que peixe foi movido
        assertNotNull("Deve haver peixe na nova posiÁ„o", 
            aquario.getMatriz()[1][1].getPeixe());
        assertTrue("PosiÁ„o antiga deve estar vazia", 
            aquario.getMatriz()[0][0].estaVazia());
    }
    
    /**
     * Testa getMatriz()
     * Cobre: Getter de matriz
     */
    @Test
    public void testAquario_GetMatriz() {
        aquario = new Aquario(3, 3, 1, 1, 1, 1);
        
        Celula[][] matriz = aquario.getMatriz();
        
        assertNotNull("Matriz n„o deve ser null", matriz);
        assertEquals("Matriz deve ter 3 linhas", 3, matriz.length);
        assertEquals("Matriz deve ter 3 colunas", 3, matriz[0].length);
    }
    
    /**
     * Testa toString() do Aquario
     * Cobre: M√©todo toString
     */
    @Test
    public void testAquario_ToString() {
        aquario = new Aquario(2, 2, 1, 1, 1, 1);
        aquario.inicializarPeixes(1, 1);
        
        String representacao = aquario.toString();
        
        assertNotNull("RepresentaÁ„o n„o deve ser null", representacao);
        assertTrue("Deve conter 'IteraÁ„o'", representacao.contains("IteraÁ„o"));
        assertTrue("Deve conter 'Peixes A'", representacao.contains("Peixes A"));
        assertTrue("Deve conter 'Peixes B'", representacao.contains("Peixes B"));
    }
}
