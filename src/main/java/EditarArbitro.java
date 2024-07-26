import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class EditarArbitro extends JFrame{
    private JPanel painelPrincipal;
    private JTextField nomeField;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JCheckBox judoCheckBox;
    private JCheckBox karateCheckBox;
    private JButton editarArbitroButton;
    private JButton voltarButton;

    private ButtonGroup buttonGroup;

    private Arbitro arbitro;

    public EditarArbitro(Arbitro arbitro){
        this.arbitro = arbitro;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(masculinoRadio);
        buttonGroup.add(femininoRadio);
        nomeField.setText(arbitro.getNome());
        if(arbitro.getGenero() == 'M'){
            masculinoRadio.setSelected(true);
        }else{
            femininoRadio.setSelected(true);
        }
        if(arbitro.getModalidade() == 'A'){
            judoCheckBox.setSelected(true);
            karateCheckBox.setSelected(true);
        } else if (arbitro.getModalidade() == 'J') {
            judoCheckBox.setSelected(true);
        }else {
            karateCheckBox.setSelected(true);
        }
        setContentPane(painelPrincipal);
        pack();
        editarArbitroButton.addActionListener(this::editarArbitroBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    public void editarArbitroBtnActionPerformed(ActionEvent actionEvent){
        String nome = nomeField.getText();
        char modalidade = 'A';
        char genero;

        if (masculinoRadio.isSelected()) {
            genero = 'M';
        } else {
            genero = 'F';
        }

        if(judoCheckBox.isSelected() && karateCheckBox.isSelected()){
            modalidade = 'A';
        } else if (judoCheckBox.isSelected()) {
            modalidade = 'J';
        } else if (karateCheckBox.isSelected()) {
            modalidade = 'K';
        }

        if (nome.isEmpty() || (!masculinoRadio.isSelected() && !femininoRadio.isSelected()) || (!judoCheckBox.isSelected() && !karateCheckBox.isSelected())) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        arbitro.setNome(nome);
        arbitro.setModalidade(modalidade);
        arbitro.setGenero(genero);

        JOptionPane.showMessageDialog(null, "Arbitro editado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        new ArbitrosIndividual(arbitro).setVisible(true);
        setVisible(false);
    }
}
