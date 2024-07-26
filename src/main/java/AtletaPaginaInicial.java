import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;

public class AtletaPaginaInicial extends JFrame{
    private JButton criarAtletaButton;
    private JButton importarAtletaSButton;
    private JPanel painelPrincipal;
    private JTable tabelaAtletas;
    private JButton voltarButton;
    private LinkedList<Atleta> atletas;
    private char sessao;

    public AtletaPaginaInicial() {
        this.atletas = DadosApp.getInstance().getAtletas();
        sessao = IniciarSessao.getInstance().getSessao();
        setContentPane(painelPrincipal);
        pack();
        criarAtletaButton.addActionListener(this::criarAtletaBtnActionPerformed);
        importarAtletaSButton.addActionListener(this::importarAtletasBtnActionPerformed);
        voltarButton.addActionListener(this::voltarButtonActionPerformed);
        DefaultTableModel modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Idade");
        modeloTabela.addColumn("Genero");
        modeloTabela.addColumn("Peso");
        modeloTabela.addColumn("País");
        modeloTabela.addRow(new Object[]{"Nome", "Idade", "Genero","Peso", "País"});
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (atletas != null && atletas.size() > 0) {
            for (Atleta atleta : atletas
            ) {
                LocalDate dataDeNascimentoLocalDate = atleta.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataAtual = LocalDate.now();
                int idade = Period.between(dataDeNascimentoLocalDate, dataAtual).getYears();
                modeloTabela.addRow(new Object[]{atleta.getNome(), idade, atleta.getGenero(), atleta.getPeso(),atleta.getPais()});
            }
        }
        tabelaAtletas.setModel(modeloTabela);
        tabelaAtletas.setDefaultRenderer(Object.class, new CustomRenderer());
        tabelaAtletas.setRowSelectionAllowed(false);
        tabelaAtletas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tabelaAtletas.getSelectedRow() != 0) {
                    int selectedRow = tabelaAtletas.getSelectedRow();
                    System.out.println("Selected row: " + selectedRow);
                    Atleta atleta = atletas.get(selectedRow-1);
                    new AtletasIndividual(atleta, atletas).setVisible(true);
                    setVisible(false);
                }
            }
        });
        setLocationRelativeTo(null);
    }

    public void criarAtletaBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode criar atletas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new CriarAtleta(atletas).setVisible(true);
        setVisible(false);
    }

    public void importarAtletasBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode importar atletas'", "Erro", JOptionPane.ERROR_MESSAGE);
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
                    if (data.length == 5) {
                        String nome = data[0].trim();
                        Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(data[1].trim());
                        char genero = data[2].trim().charAt(0);
                        float peso = Float.parseFloat(data[3].trim());
                        String pais = data[4].trim();

                        Atleta atleta = new Atleta(nome, dataNascimento, genero, peso, pais);
                        if(!atletas.contains(atleta)){
                            atletas.add(atleta);
                            i++;
                        }
                    } else {
                        System.err.println("Invalid data format: " + line);
                    }
                }
                if(i==0){
                    JOptionPane.showMessageDialog(this, "Não há novos atletas a importar", "Erro", JOptionPane.ERROR_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(this, "Atletas importados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                new AtletaPaginaInicial().setVisible(true);
                setVisible(false);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro na formatação. Exemplo: 'António Costa, 17/07/1961, M, 75.5, Índia'", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void voltarButtonActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
    }
}
