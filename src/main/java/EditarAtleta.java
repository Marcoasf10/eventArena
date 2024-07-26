import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class EditarAtleta extends JFrame{
    private JTextField nomeField;
    private JSpinner dataNascimento;
    private JTextField pesoField;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JButton editarAtletaButton;
    private JButton voltarButton;
    private JPanel painelPrincipal;
    private LinkedList<Atleta> atletas;
    private ButtonGroup buttonGroup;
    private Atleta atleta;
    private boolean paginaProva;
    private Prova prova;
    private Evento evento;

    public EditarAtleta(Atleta atleta, Boolean paginaProva) {
        this.atleta = atleta;
        this.paginaProva = paginaProva;
        this.atletas = DadosApp.getInstance().getAtletas();

        buttonGroup = new ButtonGroup();
        buttonGroup.add(masculinoRadio);
        buttonGroup.add(femininoRadio);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 1900);
        Date minDate = calendar.getTime();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.YEAR, 2023);
        Date maxDate = calendar.getTime();

        SpinnerDateModel dataNascimentoModel = new SpinnerDateModel(atleta.getDataNascimento(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataNascimento.setModel(dataNascimentoModel);
        dataNascimento.setEditor(new JSpinner.DateEditor(dataNascimento,"dd/MM/yyyy"));
        nomeField.setText(atleta.getNome());
        pesoField.setText("75");
        masculinoRadio.setSelected(true);

        nomeField.setText(atleta.getNome());
        SpinnerDateModel dataInicioModel = new SpinnerDateModel(atleta.getDataNascimento(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataNascimento.setModel(dataInicioModel);
        dataNascimento.setEditor(new JSpinner.DateEditor(dataNascimento,"dd/MM/yyyy"));
        pesoField.setText(Float.toString(atleta.getPeso()));
        if (atleta.getGenero() == 'F') {
            femininoRadio.setSelected(true);
        } else if (atleta.getGenero() == 'M') {
            masculinoRadio.setSelected(true);
        }
        setContentPane(painelPrincipal);
        pack();

        editarAtletaButton.addActionListener(this::editarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    public void editarAtletaBtnActionPerformed(ActionEvent actionEvent){
        String nome = nomeField.getText();
        Date data = (Date) dataNascimento.getValue();
        float peso;

        try {
            peso = Float.parseFloat(pesoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O peso deve ser um número válido", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        char genero;
        if (masculinoRadio.isSelected()) {
            genero = 'M';
        } else {
            genero = 'F';
        }

        if (nome.isEmpty() || (!masculinoRadio.isSelected() && !femininoRadio.isSelected())) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        atleta.setNome(nome);
        atleta.setDataNascimento(data);
        atleta.setPeso(peso);
        atleta.setGenero(genero);

        JOptionPane.showMessageDialog(null, "Atleta editado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);


    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        if (paginaProva == false) {
            new AtletasIndividual(atleta, atletas).setVisible(true);
        } else {
            new AtletasIndividual(atleta, prova, evento, atletas).setVisible(true);
        }
        setVisible(false);
    }
}
