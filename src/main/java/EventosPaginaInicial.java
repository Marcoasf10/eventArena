import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class EventosPaginaInicial extends JFrame {
    private JButton criarEventoButton;
    private JPanel painelPrincipal;
    private JButton importarEventosButton;
    private JTable tabelaEvento;
    private JButton voltarButton;
    private LinkedList<Evento> eventos;
    private LinkedList<Atleta> atletas;
    private LinkedList<Evento> eventosTxt;
    private char sessao;
    public EventosPaginaInicial(){
        this.sessao = IniciarSessao.getInstance().getSessao();;
        this.eventos = DadosApp.getInstance().getEventos();
        this.atletas = DadosApp.getInstance().getAtletas();
        eventosTxt = new LinkedList<>();
        setContentPane(painelPrincipal);
        pack();
        criarEventoButton.addActionListener(this::criarEventoBtnActionPerformed);
        importarEventosButton.addActionListener(this::importarEventoBtnActionPerformed);
        voltarButton.addActionListener(this::voltarButtonActionPerformed);
        DefaultTableModel modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Tipo Evento");
        modeloTabela.addColumn("Local");
        modeloTabela.addColumn("Data Inicio");
        modeloTabela.addColumn("Data Fim");
        modeloTabela.addColumn("Provas no Evento");
        modeloTabela.addColumn("Estado");
        modeloTabela.addRow(new Object[]{"Nome", "Tipo Evento", "Local", "Data Inicio", "Data Fim", "Provas no Evento","Estado"});
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(eventos != null && eventos.size() > 0){
            for (Evento evento: eventos
                 ) {
                modeloTabela.addRow(new Object[]{evento.getNome(), evento.getTipoEvento(), evento.getLocal(), dateFormat.format(evento.getDataInicio()), dateFormat.format(evento.getDataFim()), evento.getProvas().size(),evento.getEstado()});
            }
        }
        tabelaEvento.setModel(modeloTabela);
        tabelaEvento.setDefaultRenderer(Object.class, new CustomRenderer());
        tabelaEvento.setRowSelectionAllowed(false);
        tabelaEvento.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tabelaEvento.getSelectedRow() != 0) {
                    int selectedRow = tabelaEvento.getSelectedRow();
                    System.out.println("Selected row: " + selectedRow);
                    Evento evento = eventos.get(selectedRow-1);
                    new EventoIndividual(evento).setVisible(true);
                    setVisible(false);
                }
            }
        });
        setLocationRelativeTo(null);
    }

    public void criarEventoBtnActionPerformed(ActionEvent actionEvent){
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode criar eventos'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new CriarEvento(eventos).setVisible(true);
        setVisible(false);
    }

    private void voltarButtonActionPerformed(ActionEvent actionEvent) {
        setVisible(false);
    }
    public void importarEventoBtnActionPerformed(ActionEvent actionEvent) {
        if(sessao == 'A'){
            JOptionPane.showMessageDialog(this, "Apenas o Gestor de Eventos pode importar eventos'", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] eventoData = line.split(",");
                    if (eventoData.length == 5) {
                        String nome = eventoData[0].trim();
                        String tipoEvento = eventoData[1].trim();
                        String local = eventoData[2].trim();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataInicio = dateFormat.parse(eventoData[3].trim());
                        Date dataFim = dateFormat.parse(eventoData[4].trim());

                        Evento evento = new Evento(nome, tipoEvento, local, dataInicio, dataFim);
                        if(!eventos.contains(evento)){
                            eventosTxt.add(evento);
                        }
                    } else {
                        System.err.println("Invalid data format for evento: " + line);
                    }
                }
                reader.close();
                if(eventosTxt.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Não há novos eventos a importar", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    eventos.addAll(eventosTxt);
                    JOptionPane.showMessageDialog(this, "Eventos importados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro na formatação. Exemplo: 'Evento,TipoEvento,Local,09/05/2023,12/05/2023'", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        new EventosPaginaInicial().setVisible(true);
        setVisible(false);
    }
}



