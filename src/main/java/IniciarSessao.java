import javax.swing.*;
import java.awt.event.ActionEvent;

public class IniciarSessao extends JFrame{
    private static IniciarSessao instance = null;
    private JPanel painelPrincipal;
    private JButton gestorDeEventosButton;
    private JButton auxiliarButton;
    private char sessao;
    public IniciarSessao() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(painelPrincipal);
        pack();
        gestorDeEventosButton.addActionListener(this::GestorDeEventosBtnActionPerformed);
        auxiliarButton.addActionListener(this::AuxiliarBtnActionPerformed);
    }

    private void AuxiliarBtnActionPerformed(ActionEvent actionEvent) {
        sessao = 'A';
        setVisible(false);
        new Inicio().setVisible(true);
    }

    private void GestorDeEventosBtnActionPerformed(ActionEvent actionEvent) {
        sessao = 'G';
        setVisible(false);
        new Inicio().setVisible(true);
    }

    public char getSessao() {
        return sessao;
    }
    public static IniciarSessao getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new IniciarSessao();
        instance.setVisible(true);
    }
}
