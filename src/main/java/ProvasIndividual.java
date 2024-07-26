import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.LinkedList;

public class ProvasIndividual extends JFrame{
    private JLabel provaLabel;
    private JLabel modalidadeLabel;
    private JLabel categoriaPesoLabel;
    private JLabel escalaoEtarioLabel;
    private JLabel generoLabel;
    private JButton eliminarProvaButton;
    private JButton adicionarAtletaButton;
    private JPanel painelPrincipal;
    private JButton removerAtletaBtn;
    private JButton editarProvaButton;
    private JButton voltarButton;
    private JButton esquemaProvaButton;
    private JPanel painelTabela;
    private JTable tabelaAtletas;
    private JLabel labelAtletasInscritos;
    private LinkedList<Evento> eventos;
    private Evento evento;
    private Prova prova;
    private LinkedList<Atleta> atletas;
    private char sessao;

    private LinkedList<Arbitro> arbitros;
    public ProvasIndividual(Prova prova, Evento evento){
        this.prova = prova;
        this.evento = evento;
        this.arbitros = DadosApp.getInstance().getArbitros();
        sessao = IniciarSessao.getInstance().getSessao();


        this.atletas = DadosApp.getInstance().getAtletas();
        labelAtletasInscritos.setText("Atletas inscritos ("+prova.getAtletas().size()+"): ");
        provaLabel.setText(prova.getNome());
        if(prova.getGenero() == 'M'){
            generoLabel.setText("Masculino");
        }
        else{
            generoLabel.setText("Feminino");
        }

        if(prova.getModalidade() == 'J'){
            modalidadeLabel.setText("Judo");
        }
        else{
            modalidadeLabel.setText("Karate");
        }
        categoriaPesoLabel.setText(prova.getCategoriaPeso());
        escalaoEtarioLabel.setText(prova.getFaixaEtaria());
        DefaultTableModel modeloTabela = new DefaultTableModel();
        JScrollPane scrollPane  = new JScrollPane(tabelaAtletas);
        scrollPane.setPreferredSize(new Dimension(300,200));
        tabelaAtletas.setTableHeader(null);
        painelTabela.add(scrollPane);
        if (prova.getAtletas() != null && prova.getAtletas().size() > 0) {
            modeloTabela.addColumn("Nome");
            modeloTabela.addColumn("Idade");
            modeloTabela.addColumn("Genero");
            modeloTabela.addColumn("Peso");
            modeloTabela.addColumn("País");
            modeloTabela.addRow(new Object[]{"Nome", "Idade", "Genero","Peso", "País"});
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Atleta atleta : prova.getAtletas()
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

        setContentPane(painelPrincipal);
        pack();
        adicionarAtletaButton.addActionListener(this::adicionarAtletaBtnActionPerformed);
        removerAtletaBtn.addActionListener(this::removerAtletaBtnActionPerformed);
        eliminarProvaButton.addActionListener(this::eliminarProvaBtnActionPerformed);
        editarProvaButton.addActionListener(this::editarProvaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        esquemaProvaButton.addActionListener(this::esquemaProvaBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    private void esquemaProvaBtnActionPerformed(ActionEvent actionEvent) {
        LinkedList<Arbitro> arbitrosLegiveis = new LinkedList<>();

        for (Arbitro arbitro : arbitros) {
            if(arbitro.getModalidade() == 'A' || arbitro.getModalidade() == prova.getModalidade()){
                arbitrosLegiveis.add(arbitro);
            }
        }
        if (prova.getAtletas().size() >= 4 && (prova.getAtletas().size() & (prova.getAtletas().size() - 1)) == 0) {
            if(arbitrosLegiveis.isEmpty()){
                JOptionPane.showMessageDialog(null, "Não existem árbitros para iniciar esta prova", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EsquemaProva(prova, evento).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "O número de atletas deve ser >= 4 e uma potência de 2.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setVisible(false);
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        new EventoIndividual(evento).setVisible(true);
    }

    private void editarProvaBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode editar provas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(prova.isTermidado()){
            JOptionPane.showMessageDialog(null, "Não é possivel editar uma prova que ja terminou.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new EditarProva(prova,evento).setVisible(true);
        setVisible(false);
    }

    public void adicionarAtletaBtnActionPerformed(ActionEvent actionEvent){
        new AtletasProva(prova, evento).setVisible(true);
        setVisible(false);
    }

    public void removerAtletaBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode remover atletas de uma prova'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tabelaAtletas.getSelectedRow() != -1) {
            int selectedRow = tabelaAtletas.getSelectedRow();
            Atleta atleta = prova.getAtletas().get(selectedRow - 1);
            System.out.println("Selected atleta: " + atleta.getNome());

            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que quer remover o atleta "+atleta.getNome()+" da prova?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                prova.remover(atleta);
                JOptionPane.showMessageDialog(null, "Atleta " + atleta.getNome() + " removido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
                new ProvasIndividual(prova, evento).setVisible(true);
                setVisible(false);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nenhum atleta selecionado. Por favor, selecione um atleta da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminarProvaBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode eliminar provas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que quer eliminar a prova "+prova.getNome()+" ?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            evento.getProvas().remove(prova);
            JOptionPane.showMessageDialog(null, "Prova removida com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
            new EventoIndividual(evento).setVisible(true);
            setVisible(false);
        }
    }

}
