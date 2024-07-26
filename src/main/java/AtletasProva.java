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

public class AtletasProva extends JFrame{
    private JButton criarAtletaButton;
    private JButton importarAtletasButton;
    private JPanel painelPrincipal;
    private JTable tabelaAtletas;
    private JButton adicionarAtletaButton;
    private JButton voltarButton;
    private LinkedList<Atleta> atletas;
    private Prova prova;
    private Evento evento;
    private char sessao;

    public AtletasProva(Prova prova, Evento evento) {
        sessao = IniciarSessao.getInstance().getSessao();
        this.atletas = DadosApp.getInstance().getAtletas();
        this.evento = evento;
        this.prova = prova;
        setContentPane(painelPrincipal);
        pack();
        criarAtletaButton.addActionListener(this::criarAtletaBtnActionPerformed);
        importarAtletasButton.addActionListener(this::importarAtletasBtnActionPerformed);
        adicionarAtletaButton.addActionListener(this::adicionarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
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
                }
            }
        });

        setLocationRelativeTo(null);
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        new ProvasIndividual(prova, evento).setVisible(true);
        setVisible(false);
    }

    private void adicionarAtletaBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'G'){
            JOptionPane.showMessageDialog(null, "Erro - Apenas o auxiliar pode adicionar atletas à provas.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tabelaAtletas.getSelectedRow() != -1) {
            int selectedRow = tabelaAtletas.getSelectedRow();
            Atleta atleta = atletas.get(selectedRow - 1);
            System.out.println("Selected atleta: " + atleta.getNome());
            if (prova.getAtletas().contains(atleta)) {
                JOptionPane.showMessageDialog(null, "Erro - O Atleta " + atleta.getNome() + " já se encontra inscrito nesta prova.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                if (atleta.getGenero() == 'M' && prova.getGenero() == 'F' || atleta.getGenero() == 'F' && prova.getGenero() == 'M') {
                    JOptionPane.showMessageDialog(null, "Erro - O género do atleta nao coincide com o género da prova.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate dataDeNascimentoLocalDate = atleta.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataAtual = LocalDate.now();
                int idadeAtleta = Period.between(dataDeNascimentoLocalDate, dataAtual).getYears();
                if (prova.getFaixaEtaria().equals("Sub-12") && idadeAtleta > 12 || prova.getFaixaEtaria().equals("Sub-14") && idadeAtleta > 14 || prova.getFaixaEtaria().equals("Sub-16") && idadeAtleta > 16 || prova.getFaixaEtaria().equals("Sub-18") && idadeAtleta > 18 || prova.getFaixaEtaria().equals("Sub-21") && idadeAtleta > 21 || prova.getFaixaEtaria().equals("Seniores") && idadeAtleta < 18) {
                    JOptionPane.showMessageDialog(null, "Erro - Apenas os atletas " + prova.getFaixaEtaria() + " se podem inscrever nesta prova", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pesoInput = JOptionPane.showInputDialog(null, "Confirme o peso do atleta " + atleta.getNome() + ":");
                try {
                    int pesoConfirmado = Integer.parseInt(pesoInput);
                    atleta.setPeso(pesoConfirmado);
                    new AtletasProva(prova,evento).setVisible(true);
                    setVisible(false);
                    if (prova.getCategoriaPeso().equals("<55Kg") && pesoConfirmado >= 55 || prova.getCategoriaPeso().equals("<65Kg") && pesoConfirmado >= 65 || prova.getCategoriaPeso().equals("<75Kg") && pesoConfirmado >= 75 || prova.getCategoriaPeso().equals("<85Kg") && pesoConfirmado >= 85 || prova.getCategoriaPeso().equals("<95Kg") && pesoConfirmado >= 95 || prova.getCategoriaPeso().equals(">95Kg") && pesoConfirmado < 95) {
                        JOptionPane.showMessageDialog(null, "Erro - Apenas os atletas com peso " + prova.getCategoriaPeso() + " se podem inscrever nesta prova", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    prova.adicionar(atleta);
                    atleta.adicionar(evento);
                    JOptionPane.showMessageDialog(null, "Atleta " + atleta.getNome() + " adicionado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro - Peso inválido. Insira um valor numérico.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum atleta selecionado. Por favor, selecione um atleta da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void criarAtletaBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode criar atletas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new CriarAtleta(atletas, evento, prova).setVisible(true);
        setVisible(false);
    }

    public void importarAtletasBtnActionPerformed(ActionEvent actionEvent){
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
                        }
                    } else {
                        System.err.println("Invalid data format: " + line);
                    }
                }
                new AtletasProva(prova, evento).setVisible(true);
                setVisible(false);
                reader.close();
                JOptionPane.showMessageDialog(this, "Atletas importados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro na formatação. Exemplo: 'António Costa, 17/07/1961, M, 75.5, Índia'", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
