import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class CriarEvento extends JFrame{
    private JLabel title;
    private JTextField nomeEventoField;
    private JLabel nomeEventoLabel;
    private JLabel tipoEventoLabel;
    private JLabel localButton;
    private JTextField localField;
    private JLabel dataInicioLabel;
    private JSpinner dataInicio;
    private JSpinner dataFim;
    private JPanel painelPrincipal;
    private JCheckBox judoCheckBox;
    private JCheckBox karateCheckBox;
    private JLabel dataFimLabel;
    private JButton criarEventoButton;
    private JButton voltarButton;
    private LinkedList<Evento> eventos;
    public CriarEvento(LinkedList<Evento> eventos) {
        this.eventos = eventos;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2023);
        Date minDate = calendar.getTime();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.YEAR, 2100);
        Date maxDate = calendar.getTime();

        SpinnerDateModel dataInicioModel = new SpinnerDateModel(new Date(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataInicio.setModel(dataInicioModel);
        dataInicio.setEditor(new JSpinner.DateEditor(dataInicio,"dd/MM/yyyy"));

        SpinnerDateModel dataFimModel = new SpinnerDateModel(new Date(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataFim.setModel(dataFimModel);
        dataFim.setEditor(new JSpinner.DateEditor(dataFim,"dd/MM/yyyy"));

        setContentPane(painelPrincipal);
        pack();

        criarEventoButton.addActionListener(this::criarEventoBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }
    public void criarEventoBtnActionPerformed(ActionEvent actionEvent){

        String tipoEvento = "null";
        if(judoCheckBox.isSelected() && karateCheckBox.isSelected()){
            tipoEvento = "Judo/Karate";
        } else if (judoCheckBox.isSelected()) {
            tipoEvento = "Judo";
        } else if (karateCheckBox.isSelected()) {
            tipoEvento = "Karate";
        }

        if(nomeEventoField.getText().isEmpty() || localField.getText().isEmpty() || (!judoCheckBox.isSelected() && !karateCheckBox.isSelected())) {
            JOptionPane.showMessageDialog(null, "Erro - Todos os campos devem estar preenchidos! ", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dataInicioValue = (Date) dataInicio.getValue();
        Date dataFimValue = (Date) dataFim.getValue();


        if(dataInicioValue.after(dataFimValue)){
            JOptionPane.showMessageDialog(null, "Erro - A data de fim tem de ser posterior à de início.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Evento criado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
        Evento evento = new Evento(nomeEventoField.getText(),tipoEvento,localField.getText(),(Date) dataInicio.getValue(), (Date) dataFim.getValue());
        eventos.add(evento);

        nomeEventoField.setText("");
        localField.setText("");
        judoCheckBox.setSelected(false);
        karateCheckBox.setSelected(false);
        dataInicio.setValue(new Date());
        dataFim.setValue(new Date());

    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        new EventosPaginaInicial().setVisible(true);
        setVisible(false);
    }
}
