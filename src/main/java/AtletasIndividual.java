import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class AtletasIndividual extends JFrame {
    private JLabel atletaLabel;
    private JLabel dataNascimentoLabel;
    private JLabel pesoLabel;
    private JLabel generoLabel;
    private JButton editarAtletaButton;
    private JButton eliminarAtletaButton;
    private JList historicoEventosList;
    private JPanel painelPrincipal;
    private JButton voltarButton;
    private Evento evento;
    private Prova prova;
    private Atleta atleta;
    private boolean paginaprova;
    private LinkedList<Atleta> atletas;
    private char sessao;

    public AtletasIndividual(Atleta atleta, LinkedList<Atleta> atletas) {
        this.atleta = atleta;
        this.paginaprova = false;
        this.atletas = atletas;
        sessao = IniciarSessao.getInstance().getSessao();

        atletaLabel.setText(atleta.getNome());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataNascimentoLabel.setText(dateFormat.format(atleta.getDataNascimento()));
        pesoLabel.setText(Float.toString(atleta.getPeso()));
        if (atleta.getGenero() == 'M') {
            generoLabel.setText("Masculino");
        } else {
            generoLabel.setText("Feminino");
        }

        setContentPane(painelPrincipal);
        DefaultListModel<String> modeloAtleta = new DefaultListModel<>();
        if(atleta.getEventos().size()>0){
            for (Evento eventoAtleta: atleta.getEventos()) {
                modeloAtleta.addElement(eventoAtleta.toString());
            }

        }

        historicoEventosList.setModel(modeloAtleta);
        pack();

        editarAtletaButton.addActionListener(this::editarAtletaBtnActionPerformed);
        eliminarAtletaButton.addActionListener(this::eliminarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }
    public AtletasIndividual(Atleta atleta, Prova prova, Evento evento, LinkedList<Atleta> atletas) {
        this.atleta = atleta;
        this.evento = evento;
        this.prova = prova;
        this.paginaprova = true;
        this.atletas = atletas;
        sessao = IniciarSessao.getInstance().getSessao();

        atletaLabel.setText(atleta.getNome());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataNascimentoLabel.setText(dateFormat.format(atleta.getDataNascimento()));
        pesoLabel.setText(Float.toString(atleta.getPeso()));
        if (atleta.getGenero() == 'M') {
            generoLabel.setText("Masculino");
        } else {
            generoLabel.setText("Feminino");
        }

        setContentPane(painelPrincipal);
        DefaultListModel<String> modeloAtleta = new DefaultListModel<>();
        if(atleta.getEventos().size()>0){
            for (Evento eventoAtleta: atleta.getEventos()) {
                modeloAtleta.addElement(eventoAtleta.toString());
            }

        }
        historicoEventosList.setModel(modeloAtleta);
        pack();

        editarAtletaButton.addActionListener(this::editarAtletaBtnActionPerformed);
        eliminarAtletaButton.addActionListener(this::eliminarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        if(paginaprova == true){
            new ProvasIndividual(prova, evento).setVisible(true);
        }else{
            new AtletaPaginaInicial().setVisible(true);
        }
        setVisible(false);
    }

    public void editarAtletaBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode editar atletas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new EditarAtleta(atleta, paginaprova).setVisible(true);
        setVisible(false);
    }

    public void eliminarAtletaBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode eliminar atletas'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        atletas.remove(atleta);
        JOptionPane.showMessageDialog(null, "Atleta removido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
        new AtletaPaginaInicial().setVisible(true);
    }
}