import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class EsquemaProva extends JFrame{
    private JPanel painelPrincipal;
    private JLabel provaNome;
    private JButton voltarButton;
    private JTable table1;
    private JPanel classificacoesPanel;
    private JLabel lugar1;
    private JLabel lugar2;
    private Prova prova;
    private Evento evento;
    private Atleta vencedor;
    private Atleta atleta1;
    private Atleta atleta2;
    private Atleta derrotado;
    private String atleta1nome;
    private String atleta2nome;
    private int jogos;
    private DefaultTableModel modeloTabela;
    private LinkedList<Arbitro> arbitros;
    private  Object[][] tabelaEstado;
    private char sessao;

    public EsquemaProva(Prova prova, Evento evento) {
        this.prova = prova;
        this.evento = evento;
        this.arbitros = DadosApp.getInstance().getArbitros();
        tabelaEstado = prova.getTabelaEstado();
        sessao = IniciarSessao.getInstance().getSessao();

        esquemaProva(prova);

        setContentPane(painelPrincipal);
        voltarButton.addActionListener(this::voltarBtnActionPerformed);

        pack();
        setLocationRelativeTo(null);
    }

    public void esquemaProva(Prova prova) {
        LinkedList<Atleta> atletas = prova.getAtletas();
        Collections.shuffle(atletas);
        int meio = atletas.size() / 2;
        int combate = 1;
        double rondas = Math.log(atletas.size()) / Math.log(2);
        LinkedList<Arbitro> arbitrosLegiveis = new LinkedList<>();

        for (Arbitro arbitro : arbitros) {
            if(arbitro.getModalidade() == 'A' || arbitro.getModalidade() == prova.getModalidade()){
                arbitrosLegiveis.add(arbitro);
            }
        }

        jogos = atletas.size() - 1;

        List<Atleta> parte1 = atletas.subList(0, meio);
        List<Atleta> parte2 = atletas.subList(meio, atletas.size());

        provaNome.setText(prova.getNome());

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("NrCombate");
        modeloTabela.addColumn("Atleta1");
        modeloTabela.addColumn("Atleta2");
        modeloTabela.addColumn("Árbitro");
        modeloTabela.addColumn("Vencedor");
        modeloTabela.addRow(new Object[]{"Nrº Combate", "Atleta 1", "Atleta 2", "Árbitro", "Vencedor"});



        Random random = new Random();
        String nomeArbitro = null;
        for (int i = 0; i < meio ; i++) {
            if (!arbitrosLegiveis.isEmpty()) {
                int indiceAleatorio = random.nextInt(arbitrosLegiveis.size());
                Arbitro arbitroAleatorio = arbitrosLegiveis.get(indiceAleatorio);
                nomeArbitro = arbitroAleatorio.getNome();
            } else {

                new ProvasIndividual(prova, evento).setVisible(true);
                setVisible(false);
            }
            modeloTabela.addRow(new Object[]{combate, parte1.get(i).getNome(), parte2.get(i).getNome(), nomeArbitro, ""});
            combate++;
        }

        for (int i = 1; i < (jogos-meio)*2; i+=2) {

            int indiceAleatorio = random.nextInt(arbitrosLegiveis.size());
            Arbitro arbitroAleatorio = arbitrosLegiveis.get(indiceAleatorio);
            nomeArbitro = arbitroAleatorio.getNome();
            modeloTabela.addRow(new Object[]{combate, "(W) combate "+i+"", "(W) combate "+(i+1)+"", nomeArbitro, ""});
            combate++;
        }

        table1.setModel(modeloTabela);
        table1.setDefaultRenderer(Object.class, new CustomRenderer());
        table1.setRowSelectionAllowed(false);

        if(sessao == 'A'){
            table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    if(prova.isTermidado()){
                        return;
                    }
                    if (!event.getValueIsAdjusting() && table1.getSelectedRow() != 0) {
                        int selectedRow = table1.getSelectedRow();
                        System.out.println("Selected row: " + selectedRow);
                        if (selectedRow <= parte1.size()){
                            atleta1 = parte1.get(selectedRow - 1);
                            atleta2 = parte2.get(selectedRow - 1);
                        }else{
                            atleta1 = (Atleta) table1.getValueAt(selectedRow,1);
                            atleta2 = (Atleta) table1.getValueAt(selectedRow,2);
                        }
                        vencedor = new Luta(atleta1,  atleta2,  modeloTabela,table1,  selectedRow,  meio,  jogos,  lugar1,  lugar2,  prova).showWinnerSelectionWindow();

                    }
                }
            });
        }
        if (tabelaEstado != null && tabelaEstado.length == modeloTabela.getRowCount() && tabelaEstado[0].length == modeloTabela.getColumnCount()) {
            for (int i = 0; i < tabelaEstado.length; i++) {
                for (int j = 0; j < tabelaEstado[i].length; j++) {
                    modeloTabela.setValueAt(tabelaEstado[i][j], i, j);
                    lugar1.setText("1º Lugar: "+prova.getVencedor().getNome());
                    lugar2.setText("2º Lugar: "+prova.getSegundo().getNome());
                }
            }
        }
    }
    public void voltarBtnActionPerformed(ActionEvent actionEvent){
        System.out.println(tabelaEstado);

        new ProvasIndividual(prova, evento).setVisible(true);
        setVisible(false);
    }
}