import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.*;

public class EstatisticaEvento extends JFrame {
    private JPanel painelPrincipal;
    private JLabel lblEstatistica;
    private JTable tabelaPais;
    private JButton voltarButton;

    public EstatisticaEvento() {
        voltarButton.addActionListener(this::voltarButtonActionPerformed);

        LinkedList<Atleta> atletas = DadosApp.getInstance().getAtletas(); // Obtém a lista de atletas do evento específico

        HashMap<String, Integer> medalhasPorPais = new HashMap<>();
        for (Atleta atleta : atletas) {
            String pais = atleta.getPais();
            medalhasPorPais.put(pais, medalhasPorPais.getOrDefault(pais, 0) + atleta.getMedalhas());
        }

        List<Map.Entry<String, Integer>> listaMedalhasPais = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : medalhasPorPais.entrySet()) {
            if (entry.getValue() > 0) {
                listaMedalhasPais.add(entry);
            }
        }
        Collections.sort(listaMedalhasPais, (a, b) -> Integer.compare(b.getValue(), a.getValue())); // Ordena a lista de países com base no número de medalhas em ordem decrescente

        DefaultTableModel modeloTabelaPais = new DefaultTableModel();
        modeloTabelaPais.addColumn("Pais");
        modeloTabelaPais.addColumn("Medalhas");
        modeloTabelaPais.addRow(new Object[]{"País", "Medalhas"});

        for (Map.Entry<String, Integer> entry : listaMedalhasPais) {
            String pais = entry.getKey();
            int medalhas = entry.getValue();
            modeloTabelaPais.addRow(new Object[]{pais, medalhas});
        }

        tabelaPais.setModel(modeloTabelaPais);
        tabelaPais.setDefaultRenderer(Object.class, new CustomRenderer());
        tabelaPais.setEnabled(false); // Desabilita a edição da tabela

        setContentPane(painelPrincipal);
        pack();
        setLocationRelativeTo(null);
    }

    private void voltarButtonActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
    }
}
