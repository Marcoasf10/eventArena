import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ArbitrosPaginaInicial extends JFrame{
    private JButton criarArbitrosButton;
    private JButton importarArbitrosButton;
    private JPanel painelPrincipal;
    private JTable tabelaArbitro;
    private JButton voltarButton;
    private LinkedList<Arbitro> arbitros;
    private char sessao;

    public ArbitrosPaginaInicial(){
        this.arbitros = DadosApp.getInstance().getArbitros();
        sessao = IniciarSessao.getInstance().getSessao();
        setContentPane(painelPrincipal);
        pack();
        criarArbitrosButton.addActionListener(this::criarArbitroBtnActionPerformed);
        importarArbitrosButton.addActionListener(this::importarArbitroBtnActionPerformed);
        voltarButton.addActionListener(this::voltarButtonActionPerformed);
        DefaultTableModel modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Genero");
        modeloTabela.addColumn("Modalidade(s)");
        modeloTabela.addRow(new Object[]{"Nome", "Genero", "Modalidade(s)"});

        if (arbitros != null && arbitros.size() > 0) {
            for (Arbitro arbitro : arbitros
            ) {
                String modalidade = "nenhuma";
                if(arbitro.getModalidade() == 'J'){
                    modalidade = "Judo";
                }
                if (arbitro.getModalidade() == 'K'){
                    modalidade = "Karaté";
                }
                if(arbitro.getModalidade() == 'A'){
                    modalidade = "Judo e Karaté";
                }
                modeloTabela.addRow(new Object[]{arbitro.getNome(),arbitro.getGenero(),modalidade});
            }
        }
        tabelaArbitro.setModel(modeloTabela);
        tabelaArbitro.setDefaultRenderer(Object.class, new CustomRenderer());
        tabelaArbitro.setRowSelectionAllowed(false);
        tabelaArbitro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tabelaArbitro.getSelectedRow() != 0) {
                    int selectedRow = tabelaArbitro.getSelectedRow();
                    System.out.println("Selected row: " + selectedRow);
                    Arbitro arbitro = arbitros.get(selectedRow-1);
                    new ArbitrosIndividual(arbitro).setVisible(true);
                    setVisible(false);
                }
            }
        });
        setLocationRelativeTo(null);

    }

    public void criarArbitroBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode criar árbitros'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new CriarArbitro(arbitros).setVisible(true);
        setVisible(false);
    }

    private void voltarButtonActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
    }
    public void importarArbitroBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode importar árbitros", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        String nome = data[0].trim();
                        char modalidade = data[1].trim().charAt(0);
                        char genero = data[2].trim().charAt(0);
                        Arbitro arbitro = new Arbitro(nome, modalidade, genero);
                        if(!arbitros.contains(arbitro)){
                            arbitros.add(arbitro);
                            i++;
                        }
                    } else {
                        System.err.println("Invalid data format: " + line);
                        JOptionPane.showMessageDialog(this, "Formato de dados inválido. Exemplo: 'Artur Soares Dias,J,M'", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                if(i==0){
                    JOptionPane.showMessageDialog(this, "Não há novos árbitros a importar", "Erro", JOptionPane.ERROR_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(this, "Árbitros importados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                new ArbitrosPaginaInicial().setVisible(true);
                setVisible(false);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
