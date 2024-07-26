import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class AtletaTestCase {

    @Test
    public void testCreateAtleta(){
        var atleta = new Atleta("Diogo Guerra", new Date(103, 7, 10), 'M', 73, "Portugal");
        System.out.println(atleta.getDataNascimento());
        assertEquals("Diogo Guerra", atleta.getNome());
        assertEquals(new Date(103, 7, 10), atleta.getDataNascimento());
        assertEquals('M', atleta.getGenero());
        assertEquals(73, atleta.getPeso());
        assertEquals("Portugal", atleta.getPais());
        assertEquals(0, atleta.getMedalhas());
        assertTrue(atleta.getEventos().isEmpty());
    }

    @Test
    public void testAdicionarEventos() {
        var atleta = new Atleta("Diogo Guerra", new Date(103, 7, 10), 'M', 73, "Portugal");
        var evento1 = new Evento("Evento 1", "Judo", "Leiria", new Date(103, 7, 10), new Date(103, 8, 10));
        var evento2 = new Evento("Evento 2", "Karate", "Várzeas", new Date(103, 7, 10), new Date(103, 8, 10));

        atleta.adicionar(evento1);
        atleta.adicionar(evento2);

        assertEquals(2, atleta.getEventos().size());
        assertTrue(atleta.getEventos().contains(evento1));
        assertTrue(atleta.getEventos().contains(evento2));
    }

    @Test
    public void testAdicionarMedalhas() {
        var atleta = new Atleta("Diogo Guerra", new Date(103, 7, 10), 'M', 73, "Portugal");

        atleta.setMedalhas(5);

        assertEquals(5, atleta.getMedalhas());
    }

    @Test
    public void testEditarAtleta() {
        var atleta = new Atleta("Carlos Tojal", new Date(103, 7, 10), 'M', 73, "Portugal");

        atleta.setNome("Carla Tojal");
        atleta.setDataNascimento(new Date(1990, 1, 1));
        atleta.setGenero('F');
        atleta.setPeso(65);
        atleta.setPais("Brasil");

        assertEquals("Carla Tojal", atleta.getNome());
        assertEquals(new Date(1990, 1, 1), atleta.getDataNascimento());
        assertEquals('F', atleta.getGenero());
        assertEquals(65, atleta.getPeso());
        assertEquals("Brasil", atleta.getPais());
    }

}
