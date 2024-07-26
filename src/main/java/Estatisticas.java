import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class Estatisticas extends JFrame {
    private JTable table1;
    private JTable table2;
    private JButton voltarButton;
    private JPanel painelPrincipal;
    private LinkedList<Atleta> atletas;

    public Estatisticas() {
        this.atletas = DadosApp.getInstance().getAtletas();

        voltarButton.addActionListener(this::voltarButtonActionPerformed);


        DefaultTableModel modeloTabelaAtleta = new DefaultTableModel();
        modeloTabelaAtleta.addColumn("Atleta");
        modeloTabelaAtleta.addColumn("Medalhas");
        modeloTabelaAtleta.addRow(new Object[]{"Atleta", "Medalhas"});

        LinkedList<Atleta> atletasMedalhados = new LinkedList<>();

        for (Atleta atleta : atletas) {
            if (atleta.getMedalhas() > 0) {
                atletasMedalhados.add(atleta);
            }
        }
        Collections.sort(atletasMedalhados, Comparator.comparingInt(Atleta::getMedalhas).reversed());
        for (Atleta atleta : atletasMedalhados
        ) {
            modeloTabelaAtleta.addRow(new Object[]{atleta.getNome(), atleta.getMedalhas()});
        }

        table1.setModel(modeloTabelaAtleta);
        table1.setDefaultRenderer(Object.class, new CustomRenderer());
        table1.setEnabled(false);

        DefaultTableModel modeloTabelaPais = new DefaultTableModel();
        modeloTabelaPais.addColumn("Pais");
        modeloTabelaPais.addColumn("Medalhas");
        modeloTabelaPais.addRow(new Object[]{"Pais", "Medalhas"});

        HashMap<String, Integer> medalhasPorPais = new HashMap<>();
        for (Atleta atleta : atletasMedalhados) {
            String pais = atleta.getPais();
            medalhasPorPais.put(pais, medalhasPorPais.getOrDefault(pais, 0) + atleta.getMedalhas());
        }
        List<Object[]> paisesMedalhados = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : medalhasPorPais.entrySet()) {
            String pais = entry.getKey();
            int medalhas = entry.getValue();
            paisesMedalhados.add(new Object[]{pais, medalhas});
        }
        Collections.sort(paisesMedalhados, (a, b) -> Integer.compare((int) b[1], (int) a[1])); // Ordenar a lista paisesMedalhados com base no número de medalhas em ordem decrescente:

        for (Object[] paisMedalhado : paisesMedalhados) {
            modeloTabelaPais.addRow(paisMedalhado);
        }

        table2.setModel(modeloTabelaPais);
        table2.setDefaultRenderer(Object.class, new CustomRenderer());
        table2.setEnabled(false);

        setLocationRelativeTo(null);
        setContentPane(painelPrincipal);
        pack();
    }

    private void voltarButtonActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
    }
}