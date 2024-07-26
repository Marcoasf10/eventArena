import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class ArbitrosIndividual extends JFrame{
    private JLabel arbitroLabel;
    private JLabel generoLabel;
    private JLabel modalidadesLabel;
    private JButton editarArbitroButton;
    private JButton eliminarArbitroButton;
    private JPanel painelPrincipal;
    private JButton voltarButton;
    private Arbitro arbitro;
    private LinkedList<Arbitro> arbitros;
    private char sessao;

    public ArbitrosIndividual(Arbitro arbitro){
        this.arbitro = arbitro;
        sessao = IniciarSessao.getInstance().getSessao();
        arbitros = DadosApp.getInstance().getArbitros();
        setContentPane(painelPrincipal);
        arbitroLabel.setText(arbitro.getNome());
        if(arbitro.getGenero() == 'M'){
            generoLabel.setText("Masculino");
        }
        else{
            generoLabel.setText("Feminino");
        }
        if(arbitro.getModalidade() == 'J'){
            modalidadesLabel.setText("Judo");
        }else if (arbitro.getModalidade() == 'K'){
            modalidadesLabel.setText("Karate");
        }
        else{
            modalidadesLabel.setText("Judo e Karate");
        }

        pack();
        editarArbitroButton.addActionListener(this::editarArbitroBtnActionPerformed);
        eliminarArbitroButton.addActionListener(this::eliminarArbitroBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        setLocationRelativeTo(null);
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        new ArbitrosPaginaInicial().setVisible(true);
        setVisible(false);
    }

    public void editarArbitroBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode editar árbitros'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new EditarArbitro(arbitro).setVisible(true);
    }

    public void eliminarArbitroBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode remover árbitros'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        arbitros.remove(arbitro);
        JOptionPane.showMessageDialog(null, "Árbitro removido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
        new ArbitrosPaginaInicial().setVisible(true);
    }
}
