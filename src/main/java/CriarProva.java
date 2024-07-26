import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.LinkedList;

public class CriarProva extends JFrame{
    private JLabel title;
    private JTextField nomeProvaField;
    private JLabel nomeProvaLabel;
    private JComboBox modalidadeProvaDropdown;
    private JLabel modalidadeDaProvaLabel;
    private JLabel categoriaPesoLabel;
    private JComboBox categoriaPesoDropdown;
    private JLabel escalaoEtarioLabel;
    private JComboBox escalaoEtarioDropdown;
    private JLabel generoLabel;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JPanel painelPrincipal;
    private JButton criarProvaButton;
    private JButton voltarButton;

    private ButtonGroup buttonGroup;

    private Evento evento;

    private LinkedList<Evento> eventos;

    public CriarProva(Evento evento){
        this.evento = evento;
        this.eventos = DadosApp.getInstance().getEventos();
        buttonGroup = new ButtonGroup();
        buttonGroup.add(masculinoRadio);
        buttonGroup.add(femininoRadio);
        setContentPane(painelPrincipal);
        modalidadeProvaDropdown.addItem("Judo");
        modalidadeProvaDropdown.addItem("Karate");
        escalaoEtarioDropdown.addItem(FaixaEtaria.SUB12.getDescricao());
        escalaoEtarioDropdown.addItem(FaixaEtaria.SUB14.getDescricao());
        escalaoEtarioDropdown.addItem(FaixaEtaria.SUB16.getDescricao());
        escalaoEtarioDropdown.addItem(FaixaEtaria.SUB18.getDescricao());
        escalaoEtarioDropdown.addItem(FaixaEtaria.SUB21.getDescricao());
        escalaoEtarioDropdown.addItem(FaixaEtaria.SENIORES.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MENOS55.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MENOS65.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MENOS75.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MENOS85.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MENOS95.getDescricao());
        categoriaPesoDropdown.addItem(CategoriaPeso.MAISIGUAL95.getDescricao());
        pack();

        criarProvaButton.addActionListener(this::criarProvaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    public void criarProvaBtnActionPerformed(ActionEvent actionEvent){
        char genero = 'N';
        String selectedModalidade = (String) modalidadeProvaDropdown.getSelectedItem();
        char modalidade = 'N';


        if(masculinoRadio.isSelected()){
            genero = 'M';
        }
        else if(femininoRadio.isSelected()){
            genero = 'F';
        }
        else{
            JOptionPane.showMessageDialog(null, "Tem de selecionar 1 genero", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(selectedModalidade.equals("Judo")){
            modalidade = 'J';
        }
        else if(selectedModalidade.equals("Karate")){
            modalidade = 'K';
        }
        else{
            JOptionPane.showMessageDialog(null, "Tem de selecionar 1 modalidade", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nomeProvaField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tem de escrever o nome da Prova", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(modalidade == 'J' && evento.getTipoEvento().equals("Karate") || modalidade == 'K' && evento.getTipoEvento().equals("Judo")){
            JOptionPane.showMessageDialog(null, "Erro - A modalidade da prova nao coincide com a modalidade do evento! ", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Prova criada com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
        Prova prova = new Prova(nomeProvaField.getText(),(String) escalaoEtarioDropdown.getSelectedItem(), (String) categoriaPesoDropdown.getSelectedItem(), genero ,modalidade);
        evento.getProvas().add(prova);

        nomeProvaField.setText("");
        escalaoEtarioDropdown.setSelectedIndex(0);
        categoriaPesoDropdown.setSelectedIndex(0);
        modalidadeProvaDropdown.setSelectedIndex(0);
        buttonGroup.clearSelection();

    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        new EventoIndividual(evento).setVisible(true);
        setVisible(false);
    }

}
