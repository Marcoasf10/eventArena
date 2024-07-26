import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;

public class EditarProva extends JFrame{
    private JPanel painelPrincipal;
    private JLabel nomeProvaLabel;
    private JTextField nomeProvaField;
    private JLabel modalidadeDaProvaLabel;
    private JComboBox modalidadeProvaDropdown;
    private JLabel categoriaPesoLabel;
    private JComboBox categoriaPesoDropdown;
    private JLabel escalaoEtarioLabel;
    private JComboBox escalaoEtarioDropdown;
    private JLabel generoLabel;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JButton editarProvaButton;
    private JButton voltarButton;
    private ButtonGroup buttonGroup;

    private Prova prova;
    private Evento evento;

    public EditarProva(Prova prova, Evento evento) {
        this.prova = prova;
        this.evento = evento;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(masculinoRadio);
        buttonGroup.add(femininoRadio);
        setContentPane(painelPrincipal);
        modalidadeProvaDropdown.addItem("Judo");
        modalidadeProvaDropdown.addItem("Karate");
        escalaoEtarioDropdown.addItem("Sub-12");
        escalaoEtarioDropdown.addItem("Sub-14");
        escalaoEtarioDropdown.addItem("Sub-16");
        escalaoEtarioDropdown.addItem("Sub-18");
        escalaoEtarioDropdown.addItem("Sub-21");
        escalaoEtarioDropdown.addItem("Seniores");
        categoriaPesoDropdown.addItem("<55Kg");
        categoriaPesoDropdown.addItem("<65Kg");
        categoriaPesoDropdown.addItem("<75Kg");
        categoriaPesoDropdown.addItem("<85Kg");
        categoriaPesoDropdown.addItem("<95Kg");
        categoriaPesoDropdown.addItem(">95Kg");

        nomeProvaField.setText(prova.getNome());
        if (prova.getGenero() == 'F') {
            femininoRadio.setSelected(true);
        } else if (prova.getGenero() == 'M') {
            masculinoRadio.setSelected(true);
        }

        if (prova.getModalidade() == 'J') {
            modalidadeProvaDropdown.setSelectedItem("Judo");
        } else if (prova.getModalidade() == 'K') {
            modalidadeProvaDropdown.setSelectedItem("Karate");
        }

        categoriaPesoDropdown.setSelectedItem(prova.getCategoriaPeso());
        escalaoEtarioDropdown.setSelectedItem(prova.getFaixaEtaria());
        pack();

        editarProvaButton.addActionListener(this::editarProvaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        new ProvasIndividual(prova, evento).setVisible(true);
        setVisible(false);
    }

    private void editarProvaBtnActionPerformed(ActionEvent actionEvent) {
        String nomeProva = nomeProvaField.getText();
        char genero = masculinoRadio.isSelected() ? 'M' : 'F';
        String selectedModalidade = (String) modalidadeProvaDropdown.getSelectedItem();
        String categoriaPeso = (String) categoriaPesoDropdown.getSelectedItem();
        String faixaEtaria = (String) escalaoEtarioDropdown.getSelectedItem();

        if (nomeProva.isEmpty() || (!masculinoRadio.isSelected() && !femininoRadio.isSelected()) || selectedModalidade.isEmpty() || categoriaPeso.isEmpty() || faixaEtaria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro - Todos os campos devem estar preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        prova.setNome(nomeProva);
        prova.setGenero(genero);

        if (selectedModalidade.equals("Judo")) {
            prova.setModalidade('J');
        } else if (selectedModalidade.equals("Karate")) {
            prova.setModalidade('K');
        }

        prova.setCategoriaPeso(categoriaPeso);
        prova.setFaixaEtaria(faixaEtaria);
        JOptionPane.showMessageDialog(null, "Prova editada com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);

        boolean removidos = false;
        for (Atleta atleta: prova.getAtletas()) {
            LocalDate dataDeNascimentoLocalDate = atleta.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataAtual = LocalDate.now();
            int idadeAtleta = Period.between(dataDeNascimentoLocalDate, dataAtual).getYears();
            if ((atleta.getGenero() == 'M' && prova.getGenero() == 'F' || atleta.getGenero() == 'F' && prova.getGenero() == 'M') || (prova.getFaixaEtaria().equals("Sub-12") && idadeAtleta > 12 || prova.getFaixaEtaria().equals("Sub-14") && idadeAtleta > 14 || prova.getFaixaEtaria().equals("Sub-16") && idadeAtleta > 16 || prova.getFaixaEtaria().equals("Sub-18") && idadeAtleta > 18 || prova.getFaixaEtaria().equals("Sub-21") && idadeAtleta > 21) || (prova.getCategoriaPeso().equals("<55Kg") && atleta.getPeso() >= 55 || prova.getCategoriaPeso().equals("<65Kg") && atleta.getPeso() >= 65 || prova.getCategoriaPeso().equals("<75Kg") && atleta.getPeso() >= 75 || prova.getCategoriaPeso().equals("<85Kg") && atleta.getPeso() >= 85 || prova.getCategoriaPeso().equals("<95Kg") && atleta.getPeso() >= 95 || prova.getCategoriaPeso().equals(">95Kg") && atleta.getPeso() < 95)) {
                prova.remover(atleta);
                removidos = true;
            }
            if(removidos == true){
                JOptionPane.showMessageDialog(null, "Os atletas que deixaram de ser legiveis para esta prova foram removidos.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        new ProvasIndividual(prova, evento).setVisible(true);
        setVisible(false);

    }
}
