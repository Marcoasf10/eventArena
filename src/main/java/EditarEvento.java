import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class EditarEvento extends JFrame{
    private JPanel painelPrincipal;
    private JLabel nomeEventoLabel;
    private JTextField nomeEventoField;
    private JLabel tipoEventoLabel;
    private JCheckBox judoCheckBox;
    private JTextField localField;
    private JLabel dataInicioLabel;
    private JLabel localButton;
    private JSpinner dataInicio;
    private JLabel dataFimLabel;
    private JSpinner dataFim;
    private JButton editarEventoButton;
    private JCheckBox karateCheckBox;
    private JButton voltarButton;
    private Evento evento;

    public EditarEvento(Evento evento) {
        this.evento = evento;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2023);
        Date minDate = calendar.getTime();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.YEAR, 2100);
        Date maxDate = calendar.getTime();

        SpinnerDateModel dataInicioModel = new SpinnerDateModel(evento.getDataInicio(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataInicio.setModel(dataInicioModel);
        dataInicio.setEditor(new JSpinner.DateEditor(dataInicio,"dd/MM/yyyy"));

        SpinnerDateModel dataFimModel = new SpinnerDateModel(evento.getDataFim(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataFim.setModel(dataFimModel);
        dataFim.setEditor(new JSpinner.DateEditor(dataFim,"dd/MM/yyyy"));

        nomeEventoField.setText(evento.getNome());
        String tipoEvento = evento.getTipoEvento();
        if(tipoEvento.equals("Judo/Karate")){
            judoCheckBox.setSelected(true);
            karateCheckBox.setSelected(true);
        }else if(tipoEvento.equals("Judo")){
            judoCheckBox.setSelected(true);
        }else if(tipoEvento.equals("Karate")){
            karateCheckBox.setSelected(true);
        }
        localField.setText(evento.getLocal());
        setContentPane(painelPrincipal);
        pack();

        editarEventoButton.addActionListener(this::editarEventoBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }
    public void editarEventoBtnActionPerformed(ActionEvent actionEvent) {
        String nomeEvento = nomeEventoField.getText();
        String tipoEvento = "null";
        String local = localField.getText();

        if (nomeEvento.isEmpty() || local.isEmpty() || (!judoCheckBox.isSelected() && !karateCheckBox.isSelected())) {
            JOptionPane.showMessageDialog(null, "Erro - Todos os campos devem estar preenchidos! ", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (judoCheckBox.isSelected() && karateCheckBox.isSelected()) {
            tipoEvento = "Judo/Karate";
        } else if (judoCheckBox.isSelected()) {
            tipoEvento = "Judo";
        } else if (karateCheckBox.isSelected()) {
            tipoEvento = "Karate";
        }
        boolean removidos = false;
        if(tipoEvento != "Judo/Karate"){
            for (Prova prova: evento.getProvas()
            ) {
                if(prova.getModalidade() != tipoEvento.charAt(0)){
                    evento.remover(prova);
                    removidos = true;
                }
                if(removidos == true){
                    JOptionPane.showMessageDialog(null, "As provas que deixaram de ser legiveis para este evento foram removidas.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        evento.setNome(nomeEvento);
        evento.setTipoEvento(tipoEvento);
        evento.setLocal(local);
        evento.setDataInicio((Date) dataInicio.getValue());
        evento.setDataFim((Date) dataFim.getValue());


        JOptionPane.showMessageDialog(null, "Evento editado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        setVisible(false);
        new EventoIndividual(evento).setVisible(true);
    }
}
