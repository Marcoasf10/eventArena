import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class CriarArbitro extends JFrame{
    private JPanel painelPrincipal;
    private JTextField nomeField;
    private JRadioButton masculinoRadio;
    private JRadioButton femininoRadio;
    private JCheckBox judoCheckBox;
    private JCheckBox karateCheckBox;
    private JButton criarArbitroButton;
    private JButton voltarButton;
    private ButtonGroup buttonGroup;
    private LinkedList<Arbitro> arbitros;

    public CriarArbitro(LinkedList<Arbitro> arbitros){
        this.arbitros = arbitros;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(masculinoRadio);
        buttonGroup.add(femininoRadio);

        setContentPane(painelPrincipal);
        pack();
        criarArbitroButton.addActionListener(this::criarArbitroBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    public void criarArbitroBtnActionPerformed(ActionEvent actionEvent){
        char modalidade = 'N';
        char genero = 'N';
        if(judoCheckBox.isSelected() && karateCheckBox.isSelected()){
            modalidade = 'A';
        } else if (judoCheckBox.isSelected()) {
            modalidade = 'J';
        } else if (karateCheckBox.isSelected()) {
            modalidade = 'K';
        }
        else{
            JOptionPane.showMessageDialog(null, "Tem de selecionar pelo menos um tipo de evento", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
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

        if (nomeField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tem de escrever o nome do Árbitro", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Arbitro arbitro = new Arbitro(nomeField.getText(),modalidade,genero);
        arbitros.add(arbitro);
        JOptionPane.showMessageDialog(null, "Arbitro criado com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);

        nomeField.setText("");
        judoCheckBox.setSelected(false);
        buttonGroup.clearSelection();
        karateCheckBox.setSelected(false);

    }

    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        setVisible(false);
        new ArbitrosPaginaInicial().setVisible(true);
    }
}
