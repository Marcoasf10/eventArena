import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class Atleta implements Serializable {
    private String nome;
    private Date dataNascimento;
    private char genero;
    private float peso;
    private String pais;
    private int medalhas;
    private LinkedList<Evento> eventos;

    public Atleta(String nome, Date dataNascimento, char genero, float peso, String pais) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.peso = peso;
        this.pais = pais;
        this.medalhas = 0;
        this.eventos = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public char getGenero() {
        return genero;
    }

    public float getPeso() {
        return peso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getPais() {
        return pais;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getMedalhas() {
        return medalhas;
    }

    public LinkedList<Evento> getEventos() {
        return eventos;
    }

    public void adicionar(Evento evento){
        eventos.add(evento);
    }

    public void setMedalhas(int medalhas) {
        this.medalhas = medalhas;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atleta atleta = (Atleta) o;
        return genero == atleta.genero && Float.compare(atleta.peso, peso) == 0 && medalhas == atleta.medalhas && Objects.equals(nome, atleta.nome) && Objects.equals(dataNascimento, atleta.dataNascimento) && Objects.equals(pais, atleta.pais) && Objects.equals(eventos, atleta.eventos);
    }
}
