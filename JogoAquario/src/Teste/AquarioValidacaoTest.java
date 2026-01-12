package Teste;

import org.junit.Test;
import static org.junit.Assert.*;
import aquario.Aquario;

/**
 * Testes de validação de entrada do Aquario
 * Cobre os casos de teste CT01 a CT12 (validação de parametros)
 */
public class AquarioValidacaoTest {
    
    /**
     * CT01: Todas as classes de equivalancia validas
     * Entrada: 5, 5, 2, 1, 3, 3, 3, 3
     * Saida Esperada: Jogo inicia corretamente
     */
    @Test
    public void testCT01_TodasClassesValidas() {
        try {
            Aquario aquario = new Aquario(5, 5, 3, 3, 3, 3);
            aquario.inicializarPeixes(2, 1);
            
            assertEquals("Aquario deve ter 5 linhas", 5, aquario.getLinhas());
            assertEquals("Aquario deve ter 5 colunas", 5, aquario.getColunas());
            assertEquals("Deve haver 2 peixes A", 2, aquario.getQuantidadePeixesA());
            assertEquals("Deve haver 1 peixe B", 1, aquario.getQuantidadePeixesB());
            assertFalse("Jogo nao deve ter terminado", aquario.jogoTerminou());
        } catch (IllegalArgumentException e) {
            fail("Nao deveria lançar exceção com valores validos");
        }
    }
    
    /**
     * CT02: Dimensao M invalida (M < 1)
     * Entrada: 0, 5, 2, 1, 3, 3, 3, 3
     * Saida Esperada: Erro: Dimensao M invalida
     */
    @Test
    public void testCT02_DimensaoMInvalida() {
        try {
            new Aquario(0, 5, 3, 3, 3, 3);
            fail("Deveria lançar IllegalArgumentException para M = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Dimensao M invalida.", e.getMessage());
        }
    }
    
    /**
     * CT03: Dimensao N invalida (N < 1)
     * Entrada: 5, 0, 2, 1, 3, 3, 3, 3
     * Saida Esperada: Erro: Dimensao N invalida
     */
    @Test
    public void testCT03_DimensaoNInvalida() {
        try {
            new Aquario(5, 0, 3, 3, 3, 3);
            fail("Deveria lançar IllegalArgumentException para N = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Dimensao N invalida.", e.getMessage());
        }
    }
    
    /**
     * CT04: Quantidade X invalida (X < 1)
     * Entrada: 5, 5, 0, 1, 3, 3, 3, 3
     * Saida Esperada: Erro: Quantidade X invalida
     */
    @Test
    public void testCT04_QuantidadeXInvalida() {
        try {
            Aquario aquario = new Aquario(5, 5, 3, 3, 3, 3);
            aquario.inicializarPeixes(0, 1);
            fail("Deveria lançar IllegalArgumentException para X = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Quantidade X invalida.", e.getMessage());
        }
    }
    
    /**
     * CT05: Quantidade Y invalida (Y < 1)
     * Entrada: 5, 5, 2, 0, 3, 3, 3, 3
     * Saida Esperada: Erro: Quantidade Y invalida
     */
    @Test
    public void testCT05_QuantidadeYInvalida() {
        try {
            Aquario aquario = new Aquario(5, 5, 3, 3, 3, 3);
            aquario.inicializarPeixes(2, 0);
            fail("Deveria lançar IllegalArgumentException para Y = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Quantidade Y invalida.", e.getMessage());
        }
    }
    
    /**
     * CT06: Parametro RA invalido (RA < 1)
     * Entrada: 5, 5, 2, 1, 0, 3, 3, 3
     * Saida Esperada: Erro: Parametro RA invalido
     */
    @Test
    public void testCT06_ParametroRAInvalido() {
        try {
            new Aquario(5, 5, 0, 3, 3, 3);
            fail("Deveria lançar IllegalArgumentException para RA = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Parametro RA invalido.", e.getMessage());
        }
    }
    
    /**
     * CT07: Parametro MA invalido (MA < 1)
     * Entrada: 5, 5, 2, 1, 3, 0, 3, 3
     * Saida Esperada: Erro: Parametro MA invalido
     */
    @Test
    public void testCT07_ParametroMAInvalido() {
        try {
            new Aquario(5, 5, 3, 0, 3, 3);
            fail("Deveria lançar IllegalArgumentException para MA = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Parametro MA invalido.", e.getMessage());
        }
    }
    
    /**
     * CT08: Parametro RB invalido (RB < 1)
     * Entrada: 5, 5, 2, 1, 3, 3, 0, 3
     * Saida Esperada: Erro: Parametro RB invalido
     */
    @Test
    public void testCT08_ParametroRBInvalido() {
        try {
            new Aquario(5, 5, 3, 3, 0, 3);
            fail("Deveria lançar IllegalArgumentException para RB = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Parametro RB invalido.", e.getMessage());
        }
    }
    
    /**
     * CT09: Parametro MB invalido (MB < 1)
     * Entrada: 5, 5, 2, 1, 3, 3, 3, 0
     * Saida Esperada: Erro: Parametro MB invalido
     */
    @Test
    public void testCT09_ParametroMBInvalido() {
        try {
            new Aquario(5, 5, 3, 3, 3, 0);
            fail("Deveria lançar IllegalArgumentException para MB = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Parametro MB invalido.", e.getMessage());
        }
    }
    
    /**
     * CT10: Excesso de peixes (Capacidade excedida)
     * Entrada: 3, 3, 5, 5, 3, 3, 3, 3
     * Saida Esperada: Erro: Excesso de peixes (10 peixes para 9 celulas)
     */
    @Test
    public void testCT10_ExcessoDePeixes() {
        try {
            Aquario aquario = new Aquario(3, 3, 3, 3, 3, 3);
            aquario.inicializarPeixes(5, 5);
            fail("Deveria lançar IllegalArgumentException para excesso de peixes");
        } catch (IllegalArgumentException e) {
            assertTrue("Mensagem deve mencionar excesso de peixes", 
                e.getMessage().contains("Excesso de peixes"));
            assertTrue("Mensagem deve mencionar quantidade correta", 
                e.getMessage().contains("10 peixes para 9 celulas"));
        }
    }
    
    /**
     * CT11: Teste de Valor Limite (Minimo M, N, X)
     * Entrada: 1, 1, 1, 0, 3, 3, 3, 3
     * Saida Esperada: Erro Y (Y=0 sao invalido)
     */
    @Test
    public void testCT11_ValorLimiteMinimoComYInvalido() {
        try {
            Aquario aquario = new Aquario(1, 1, 3, 3, 3, 3);
            aquario.inicializarPeixes(1, 0);
            fail("Deveria lançar IllegalArgumentException para Y = 0");
        } catch (IllegalArgumentException e) {
            assertEquals("Quantidade Y invalida.", e.getMessage());
        }
    }
    
    /**
     * CT12: Jogo inicia com aquario cheio (4 posiçoees, 4 peixes)
     * Entrada: 2, 2, 2, 2, 1, 1, 1, 1
     * Saida Esperada: Jogo inicia. Parametros minimos.
     */
    @Test
    public void testCT12_AquarioCheioParametrosMinimos() {
        try {
            Aquario aquario = new Aquario(2, 2, 1, 1, 1, 1);
            aquario.inicializarPeixes(2, 2);
            
            assertEquals("Aquario deve ter 2 linhas", 2, aquario.getLinhas());
            assertEquals("Aquario deve ter 2 colunas", 2, aquario.getColunas());
            assertEquals("Deve haver 2 peixes A", 2, aquario.getQuantidadePeixesA());
            assertEquals("Deve haver 2 peixes B", 2, aquario.getQuantidadePeixesB());
            
            // Verificar que o aquario estao cheio (4 peixes em 4 celulas)
            int totalPeixes = aquario.getQuantidadePeixesA() + aquario.getQuantidadePeixesB();
            int capacidade = aquario.getLinhas() * aquario.getColunas();
            assertEquals("Aquario deve estar cheio", capacidade, totalPeixes);
        } catch (IllegalArgumentException e) {
            fail("Nao deveria lançar exceçao com valores validos no limite");
        }
    }
    
    /**
     * Teste adicional: Valores negativos para M
     */
    @Test
    public void testDimensaoMNegativa() {
        try {
            new Aquario(-1, 5, 3, 3, 3, 3);
            fail("Deveria lançar IllegalArgumentException para M negativo");
        } catch (IllegalArgumentException e) {
            assertEquals("Dimensao M invalida.", e.getMessage());
        }
    }
    
    /**
     * Teste adicional: Valores negativos para N
     */
    @Test
    public void testDimensaoNNegativa() {
        try {
            new Aquario(5, -1, 3, 3, 3, 3);
            fail("Deveria lançar IllegalArgumentException para N negativo");
        } catch (IllegalArgumentException e) {
            assertEquals("Dimensao N invalida.", e.getMessage());
        }
    }
}