import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class    EventoIndividual extends JFrame{
    private JLabel eventoTitulo;
    private JButton eliminarEventoButton;
    private JLabel localLabel;
    private JLabel dataInicioLabel;
    private JLabel dataFimLabel;
    private JLabel ProvasLabel;
    private JButton adicionarProvaButton;
    private JButton editarEventoButton;
    private JPanel painelPrincipal;
    private JButton estatisticasButton;
    private JButton voltarButton;
    private JButton importarProvaButton;
    private JList provasList;
    private char sessao;
    private LinkedList<Evento> eventos;
    private Evento evento;

    public EventoIndividual(Evento evento){
        this.evento = evento;
        this.eventos = DadosApp.getInstance().getEventos();
        sessao = IniciarSessao.getInstance().getSessao();
        eventoTitulo.setText(evento.getNome());
        localLabel.setText(evento.getLocal());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataInicioLabel.setText(dateFormat.format(evento.getDataInicio()));
        dataFimLabel.setText(dateFormat.format(evento.getDataFim()));

        DefaultListModel<String> modeloProva = new DefaultListModel<>();

        for (Prova prova : evento.getProvas()) {
            modeloProva.addElement(prova.toString());
        }

        provasList.setModel(modeloProva);

        adicionarProvaButton.addActionListener(this::adicionarProvaBtnActionPerformed);
        editarEventoButton.addActionListener(this::editarEventoBtnActionPerformed);
        eliminarEventoButton.addActionListener(this::eliminarEventoBtnActionPerformed);
        estatisticasButton.addActionListener(this::estatisticasBtnActionPerformed);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);
        importarProvaButton.addActionListener(this::importarProvaBtnActionPerformed);

        provasList.addListSelectionListener(e -> {
            // Verifica se a seleção ainda está em andamento
            if (!e.getValueIsAdjusting()) {
                // Obtém o índice do elemento selecionado na lista
                int selectedIndex = provasList.getSelectedIndex();

                // Verifica se há um item selecionado
                if (selectedIndex != -1) {
                    // Obtém o elemento selecionado do modelo de prova
                    Prova selectedProva = evento.getProvas().get(selectedIndex);

                    // Imprime as informações do elemento selecionado no console
                    System.out.println("Elemento selecionado: " + selectedProva);
                    setVisible(false);
                    new ProvasIndividual(selectedProva, evento).setVisible(true);
                }
            }
        });

        setContentPane(painelPrincipal);
        pack();
        setLocationRelativeTo(null);
    }

    private void importarProvaBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode impotar provas", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 5) {
                        String nome = data[0].trim();
                        String faixaEtaria = data[1].trim();
                        String categoriaPeso = data[2].trim();
                        char genero = data[3].trim().charAt(0);
                        char modalidade = data[4].trim().charAt(0);

                        Prova prova = new Prova(nome, faixaEtaria, categoriaPeso, genero, modalidade);
                        if(!evento.getProvas().contains(prova)){
                            evento.getProvas().add(prova);
                            i++;
                        }
                    } else {
                        System.err.println("Invalid data format: " + line);
                    }
                }
                if(i==0){
                    JOptionPane.showMessageDialog(this, "Não há novas provas a importar", "Erro", JOptionPane.ERROR_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(this, "Provas importados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                new EventoIndividual(evento).setVisible(true);
                setVisible(false);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void voltarBtnActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        new EventosPaginaInicial().setVisible(true);
    }

    private void estatisticasBtnActionPerformed(ActionEvent actionEvent) {
        new EstatisticaEvento().setVisible(true);
    }

    public void adicionarProvaBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode criar provas", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new CriarProva(evento).setVisible(true);
        setVisible(false);
    }

    public void editarEventoBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode editar eventos'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setVisible(false);
        new EditarEvento(evento).setVisible(true);
    }

    public void eliminarEventoBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode eliminar eventos'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        eventos.remove(evento);
        JOptionPane.showMessageDialog(null, "Evento removido com sucesso", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
        new EventosPaginaInicial().setVisible(true);
    }
}
