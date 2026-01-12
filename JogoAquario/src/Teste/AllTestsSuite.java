package Teste;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    AquarioValidacaoTest.class,
    AquarioRegrasNegocioTest.class,
    JogoAquarioTest.class,
    
})
public class AllTestsSuite {
    // Esta classe permanece vazia
    
}

