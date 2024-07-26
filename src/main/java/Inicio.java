import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Inicio extends JFrame{
    private JButton eventoBtn;
    private JPanel painelPrincipal;
    private JButton atletasBtn;
    private JButton arbitrosBtn;
    private JLabel inicioLabel;
    private JButton estatisticasButton;
    public Inicio(){

        DadosApp.getInstance().carregarDados();

        atletasBtn.addActionListener(this::atletasBtnActionPerformed);
        arbitrosBtn.addActionListener(this::arbitrosBtnActionPerformed);
        eventoBtn.addActionListener(this::eventoBtnActionPerformed);
        estatisticasButton.addActionListener(this::estatisticasButtonActionPerformed);

        setContentPane(painelPrincipal);
        ImageIcon icon = new ImageIcon("src/images/calendar.png");
        Image resizedImage = icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        eventoBtn.setIcon(resizedIcon);

        ImageIcon icon2 = new ImageIcon("src/images/judo.png");
        Image resizedImage2 = icon2.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(resizedImage2);
        atletasBtn.setIcon(resizedIcon2);

        ImageIcon icon3 = new ImageIcon("src/images/arbitro.png");
        Image resizedImage3 = icon3.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon3 = new ImageIcon(resizedImage3);
        arbitrosBtn.setIcon(resizedIcon3);

        ImageIcon icon4 = new ImageIcon("src/images/estatistica.png");
        Image resizedImage4 = icon4.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon4 = new ImageIcon(resizedImage4);
        estatisticasButton.setIcon(resizedIcon4);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DadosApp.getInstance().salvarDados();
                super.windowClosing(e);
            }
        });
    }

    private void estatisticasButtonActionPerformed(ActionEvent actionEvent) {
        new Estatisticas().setVisible(true);;
    }

    public void atletasBtnActionPerformed(ActionEvent actionEvent){
        new AtletaPaginaInicial().setVisible(true);;
    }

    public void eventoBtnActionPerformed(ActionEvent actionEvent){
        new EventosPaginaInicial().setVisible(true);
    }

    public void arbitrosBtnActionPerformed(ActionEvent actionEvent){
        new ArbitrosPaginaInicial().setVisible(true);
    }

}
