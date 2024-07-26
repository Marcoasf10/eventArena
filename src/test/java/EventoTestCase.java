import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventoTestCase {
    @Test
    public void testCriarEvento() {
        var dataInicio = new Date(123, 4, 9);
        var dataFim = new Date(123, 4, 12);
        var evento = new Evento("Evento 1", "Judo/Karate", "Leiria", dataInicio, dataFim);

        assertEquals("Evento 1", evento.getNome());
        assertEquals("Judo/Karate", evento.getTipoEvento());
        assertEquals("Leiria", evento.getLocal());
        assertEquals(dataInicio, evento.getDataInicio());
        assertEquals(dataFim, evento.getDataFim());
        assertEquals("A decorrer", evento.getEstado());
    }

    @Test
    public void testAdicionarERemoverProvas() {
        var dataInicio = new Date(123, 4, 9);
        var dataFim = new Date(123, 4, 12);
        var evento = new Evento("Evento 1", "K", "Leiria", dataInicio, dataFim);

        var prova1 = new Prova("Prova 1", "Sub-12", "<65Kg", 'M', 'J');
        var prova2 = new Prova("Prova 2", "Seniores", "<95Kg", 'F', 'K');

        evento.adicionar(prova1);
        evento.adicionar(prova2);

        assertEquals(2, evento.getProvas().size());
        assertTrue(evento.getProvas().contains(prova1));
        assertTrue(evento.getProvas().contains(prova2));

        evento.remover(prova1);

        assertEquals(1, evento.getProvas().size());
        assertFalse(evento.getProvas().contains(prova1));
        assertTrue(evento.getProvas().contains(prova2));
    }

    @Test
    public void testEditarEvento() {
        var dataInicio = new Date(123, 4, 9);
        var dataFim = new Date(123, 4, 12);
        var evento = new Evento("Evento 1", "Judo", "Leiria", dataInicio, dataFim);

        evento.setNome("Evento Judo");
        evento.setTipoEvento("Karate");
        evento.setLocal("Vialonga");
        var novaDataInicio = new Date(123, 4, 10);
        var novaDataFim = new Date(123, 4, 13);
        evento.setDataInicio(novaDataInicio);
        evento.setDataFim(novaDataFim);

        assertEquals("Evento Judo", evento.getNome());
        assertEquals("Karate", evento.getTipoEvento());
        assertEquals("Vialonga", evento.getLocal());
        assertEquals(novaDataInicio, evento.getDataInicio());
        assertEquals(novaDataFim, evento.getDataFim());
    }

}
