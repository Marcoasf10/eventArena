import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Luta {
    private Atleta atleta1;
    private Atleta atleta2;
    private DefaultTableModel modeloTabela;
    private JTable table;
    private int selectedRow;
    private int meio;
    private int jogos;
    private JLabel lugar1;
    private JLabel lugar2;
    private Prova prova;
    private Atleta vencedor;
    private Atleta derrotado;

    public Luta(Atleta atleta1, Atleta atleta2, DefaultTableModel modeloTabela, JTable table, int selectedRow, int meio, int jogos, JLabel lugar1, JLabel lugar2, Prova prova) {
        this.atleta1 = atleta1;
        this.atleta2 = atleta2;
        this.modeloTabela = modeloTabela;
        this.table = table;
        this.selectedRow = selectedRow;
        this.meio = meio;
        this.jogos = jogos;
        this.lugar1 = lugar1;
        this.lugar2 = lugar2;
        this.prova = prova;
    }

    public Atleta showWinnerSelectionWindow() {
        JFrame winnerFrame = new JFrame("Escolha o Vencedor");
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setSize(300, 100);
        winnerFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JRadioButton radioButton1 = new JRadioButton(atleta1.getNome());
        JRadioButton radioButton2 = new JRadioButton(atleta2.getNome());
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        panel.add(radioButton1);
        panel.add(radioButton2);

        JButton submitButton = new JButton("Escolher");
        submitButton.addActionListener(e -> {

            if (radioButton1.isSelected()) {
                System.out.println("Winner: " + atleta1.getNome());
                vencedor = atleta1;
                derrotado = atleta2;
            } else if (radioButton2.isSelected()) {
                System.out.println("Winner: " + atleta2.getNome());
                vencedor = atleta2;
                derrotado = atleta1;
            } else {
                // Nenhum atleta selecionado como vencedor
                return;
            }

            modeloTabela.setValueAt(vencedor, selectedRow, 4);

            System.out.println(selectedRow);
            System.out.println(meio);
            if (selectedRow <= meio) {
                int luta = (int) table.getValueAt(selectedRow, 0);
                int linha = (luta / 2) + luta % 2 + meio;
                int coluna = luta % 2 == 0 ? 2 : 1;
                System.out.println(linha);
                System.out.println(coluna);
                modeloTabela.setValueAt(vencedor, linha, coluna);
            }

            if ((int) table.getValueAt(selectedRow, 0) == jogos) {
                lugar1.setText("1º Lugar: " + vencedor.getNome());
                lugar2.setText("2º Lugar: " + derrotado.getNome());
                vencedor.setMedalhas(vencedor.getMedalhas() + 1);
                derrotado.setMedalhas(derrotado.getMedalhas() + 1);
                prova.setTermidado(true);

                Object[][] tabelaEstado = new Object[modeloTabela.getRowCount()][modeloTabela.getColumnCount()];
                for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                    for (int j = 0; j < modeloTabela.getColumnCount(); j++) {
                        tabelaEstado[i][j] = modeloTabela.getValueAt(i, j);
                    }
                }

                prova.setTabelaEstado(tabelaEstado);
                prova.setVencedor(vencedor);
                prova.setSegundo(derrotado);
            }

            table.repaint();
            winnerFrame.dispose();
        });

        panel.add(submitButton);
        winnerFrame.add(panel);
        winnerFrame.setVisible(true);

        return vencedor;
    }
}
