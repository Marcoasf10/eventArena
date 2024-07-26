import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DadosApp {
    private static DadosApp instance = null;
    private LinkedList<Evento> eventos;
    private LinkedList<Atleta> atletas;
    private LinkedList<Arbitro> arbitros;


    private DadosApp(){
        eventos = new LinkedList<>();
        atletas = new LinkedList<>();
        arbitros = new LinkedList<>();
    }


    public static DadosApp getInstance() {
        if (instance == null) {
            instance = new DadosApp();
        }
        return instance;
    }

    public void salvarDados() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("dados.dat"));
            outputStream.writeObject(atletas);
            outputStream.writeObject(arbitros);
            outputStream.writeObject(eventos);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("dados.dat"));
            atletas = (LinkedList<Atleta>) inputStream.readObject();
            arbitros = (LinkedList<Arbitro>) inputStream.readObject();
            eventos = (LinkedList<Evento>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    public LinkedList<Evento> getEventos() {
        return eventos;
    }

    public LinkedList<Atleta> getAtletas() {
        return atletas;
    }

    public LinkedList<Arbitro> getArbitros() {
        return arbitros;
    }
}
