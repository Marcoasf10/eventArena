import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.*;

public class CriarAtleta extends JFrame{
    private JTextField nomeField;
    private JTextField pesoField;
    private JButton criarAtletaButton;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JSpinner dataNascimento;
    private JPanel painelPrincipal;
    private JButton voltarButton;
    private JLabel paisLabel;
    private JComboBox paisComboBox;
    private JTextField paisTextField;
    private ButtonGroup buttonGroup;
    private LinkedList<Atleta> atletas;
    private Evento evento;
    private LinkedList<Evento> eventos;
    private boolean hasEvento;
    private Prova prova;

    public CriarAtleta(LinkedList<Atleta> atletas){
        this.atletas = atletas;
        this.hasEvento = false;
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
        String[] countryCodes = Locale.getISOCountries();
        int i = 0;
        for (String paisCodigo: countryCodes
             ) {
            Locale pais =new Locale("", paisCodigo);
            countryCodes[i] = pais.getDisplayCountry();
            i++;
        }
        Collator collator = Collator.getInstance(new Locale("pt", "PT"));
        Arrays.sort(countryCodes, collator::compare);
        for (String pais: countryCodes
             ) {
            paisComboBox.addItem(pais);
        }
        SpinnerDateModel dataNascimentoModel = new SpinnerDateModel(new Date(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataNascimento.setModel(dataNascimentoModel);
        dataNascimento.setEditor(new JSpinner.DateEditor(dataNascimento,"dd/MM/yyyy"));
        setContentPane(painelPrincipal);
        pack();

        criarAtletaButton.addActionListener(this::criarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }
    public CriarAtleta(LinkedList<Atleta> atletas, Evento evento, Prova prova){
        this.atletas = atletas;
        this.evento = evento;
        this.prova = prova;
        this.hasEvento = true;
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
        String[] countryCodes = Locale.getISOCountries();
        int i = 0;
        for (String paisCodigo: countryCodes
        ) {
            Locale pais =new Locale("", paisCodigo);
            countryCodes[i] = pais.getDisplayCountry();
            i++;
        }
        Collator collator = Collator.getInstance(new Locale("pt", "PT"));
        Arrays.sort(countryCodes, collator::compare);
        for (String pais: countryCodes
        ) {
            paisComboBox.addItem(pais);
        }
        SpinnerDateModel dataNascimentoModel = new SpinnerDateModel(new Date(), minDate, maxDate, Calendar.DAY_OF_MONTH);
        dataNascimento.setModel(dataNascimentoModel);
        dataNascimento.setEditor(new JSpinner.DateEditor(dataNascimento,"dd/MM/yyyy"));
        setContentPane(painelPrincipal);
        pack();

        criarAtletaButton.addActionListener(this::criarAtletaBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    public void criarAtletaBtnActionPerformed(ActionEvent actionEvent){
        Date data = (Date) dataNascimento.getValue();
        char genero;
        float peso;

        try {
            peso = Float.parseFloat(pesoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O peso deve ser um número válido", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(masculinoRadio.isSelected()){
            genero = 'M';
        }
        else{
            genero = 'F';
        }

        if(nomeField.getText().isEmpty() || (!masculinoRadio.isSelected() && !femininoRadio.isSelected())){
            JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Atleta atleta = new Atleta(nomeField.getText(), data, genero, peso, (String) paisComboBox.getSelectedItem());
        JOptionPane.showMessageDialog(null, "Atleta criado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);

        atletas.add(atleta);

        nomeField.setText("");
        dataNascimento.setValue(new Date());
        buttonGroup.clearSelection();
        pesoField.setText("");
        paisComboBox.setSelectedIndex(0);
    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        if (!hasEvento) {
            new AtletaPaginaInicial().setVisible(true);
        } else {
            new AtletasProva(prova, evento).setVisible(true);
        }
        setVisible(false);
    }
}
