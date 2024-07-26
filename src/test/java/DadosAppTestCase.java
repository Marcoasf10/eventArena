import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class DadosAppTestCase {
    @Test
    public void testGetInstance() {
        DadosApp dadosApp1 = DadosApp.getInstance();
        DadosApp dadosApp2 = DadosApp.getInstance();

        assertNotNull(dadosApp1);
        assertNotNull(dadosApp2);
        assertSame(dadosApp1, dadosApp2);
    }
}
