import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ProvaTestCase {
    @Test
    public void testCriarProva() {
        var prova = new Prova("Prova 1", "Seniores", "<75Kg", 'M', 'J');

        assertEquals("Prova 1", prova.getNome());
        assertEquals("Seniores", prova.getFaixaEtaria());
        assertEquals("<75Kg", prova.getCategoriaPeso());
        assertEquals('M', prova.getGenero());
        assertEquals('J', prova.getModalidade());

    }
    @Test
    public void testEditarProva(){
        var prova = new Prova("Prova 1", "Seniores", "<75Kg", 'M', 'J');

        prova.setNome("Prova 2");
        prova.setFaixaEtaria("Sub-21");
        prova.setCategoriaPeso(">=95Kg");
        prova.setGenero('F');
        prova.setModalidade('K');

        assertEquals("Prova 2", prova.getNome());
        assertEquals("Sub-21", prova.getFaixaEtaria());
        assertEquals(">=95Kg", prova.getCategoriaPeso());
        assertEquals('F', prova.getGenero());
        assertEquals('K', prova.getModalidade());
    }
    @Test
    public void testAdicionarERemoverAtletas() {
        var prova = new Prova("Prova 1", "Seniores", "<75Kg", 'M', 'J');

        var atleta1 = new Atleta("Atleta 1", new Date(103,7,10), 'M', 62, "Portugal");
        var atleta2 = new Atleta("Atleta 2",  new Date(103,7,10), 'M', 62, "Portugal");

        prova.adicionar(atleta1);
        prova.adicionar(atleta2);

        assertEquals(2, prova.getAtletas().size());
        assertTrue(prova.getAtletas().contains(atleta1));
        assertTrue(prova.getAtletas().contains(atleta2));

        prova.remover(atleta1);

        assertEquals(1, prova.getAtletas().size());
        assertFalse(prova.getAtletas().contains(atleta1));
        assertTrue(prova.getAtletas().contains(atleta2));
    }
    @Test
    public void testTabelaEstado() {
        var prova = new Prova("Prova 1", "Seniores", "<75Kg", 'M', 'J');

        assertNull(prova.getTabelaEstado());

        Object[][] tabelaEstado = new Object[2][2];
        tabelaEstado[0][0] = "A";
        tabelaEstado[0][1] = "B";
        tabelaEstado[1][0] = "C";
        tabelaEstado[1][1] = "D";

        prova.setTabelaEstado(tabelaEstado);

        assertArrayEquals(tabelaEstado, prova.getTabelaEstado());
    }
    @Test
    public void testVencedorESegundo() {
        var prova = new Prova("Prova 1", "Seniores", "<75Kg", 'M', 'J');

        assertNull(prova.getVencedor());
        assertNull(prova.getSegundo());

        var atleta1 = new Atleta("Diogo Guerra", new Date(103, 7, 10), 'M', 73, "Portugal");
        var atleta2 = new Atleta("Valter G", new Date(103, 3, 24), 'M', 53, "Portugal");

        prova.setVencedor(atleta2);
        prova.setSegundo(atleta1);

        assertEquals(atleta2, prova.getVencedor());
        assertEquals(atleta1, prova.getSegundo());
    }
}
