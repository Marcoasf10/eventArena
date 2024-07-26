import java.io.Serializable;
import java.util.Objects;

public class Arbitro implements Serializable {
    private String nome;
    private char modalidade;
    private char genero;

    public Arbitro(String nome, char modalidade, char genero) {
        this.nome = nome;
        this.modalidade = modalidade;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public char getModalidade() {
        return modalidade;
    }

    public char getGenero() {
        return genero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setModalidade(char modalidade) {
        this.modalidade = modalidade;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arbitro arbitro = (Arbitro) o;
        return modalidade == arbitro.modalidade && genero == arbitro.genero && Objects.equals(nome, arbitro.nome);
    }


}
