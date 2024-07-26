import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArbitroTestCase {
    @Test
    public void testCreateArbitro() {
        var arbitro = new Arbitro("Artur Soares Dias", 'J', 'M');

        assertEquals("Artur Soares Dias", arbitro.getNome());
        assertEquals('J', arbitro.getModalidade());
        assertEquals('M', arbitro.getGenero());
    }

    @Test
    public void testEditarArbitro() {
        var arbitro = new Arbitro("Artur Soares Dias", 'J', 'M');

        arbitro.setNome("Maria Santos");
        arbitro.setModalidade('A');
        arbitro.setGenero('F');

        assertEquals("Maria Santos", arbitro.getNome());
        assertEquals('A', arbitro.getModalidade());
        assertEquals('F', arbitro.getGenero());
    }

}
