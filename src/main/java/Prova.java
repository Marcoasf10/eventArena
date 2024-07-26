import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Prova implements Serializable{
    private String nome;
    private String faixaEtaria;
    private String categoriaPeso;
    private char genero;
    private LinkedList<Atleta> atletas;
    private boolean termidado;
    private char modalidade;
    private Object[][] tabelaEstado;
    private Atleta vencedor;
    private Atleta segundo;

    public Prova(String nome, String faixaEtaria, String categoriaPeso, char genero, char modalidade) {
        this.nome = nome;
        this.faixaEtaria = faixaEtaria;
        this.categoriaPeso = categoriaPeso;
        this.genero = genero;
        this.modalidade = modalidade;
        atletas = new LinkedList<Atleta>();
        termidado = false;
        tabelaEstado = null;
        vencedor = null;
        segundo = null;
    }

    public String getNome() {
        return nome;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public String getCategoriaPeso() {
        return categoriaPeso;
    }

    public char getGenero() {
        return genero;
    }

    public LinkedList<Atleta> getAtletas() {
        return atletas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public void setCategoriaPeso(String categoriaPeso) {
        this.categoriaPeso = categoriaPeso;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setAtletas(LinkedList<Atleta> atletas) {
        this.atletas = atletas;
    }

    public void adicionar(Atleta atleta){
        atletas.add(atleta);
    }

    public void setTermidado(boolean termidado) {
        this.termidado = termidado;
    }

    public boolean isTermidado() {
        return termidado;
    }

    public void remover(Atleta atleta){
        atletas.remove(atleta);
    }

    public char getModalidade() {
        return modalidade;
    }

    public void setModalidade(char modalidade) {
        this.modalidade = modalidade;
    }
    public void setTabelaEstado(Object[][] tabelaEstado) {
        this.tabelaEstado = tabelaEstado;
    }

    public Atleta getVencedor() {
        return vencedor;
    }

    public Atleta getSegundo() {
        return segundo;
    }

    public void setVencedor(Atleta vencedor) {
        this.vencedor = vencedor;
    }

    public void setSegundo(Atleta segundo) {
        this.segundo = segundo;
    }

    public Object[][] getTabelaEstado() {
        return tabelaEstado;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prova prova = (Prova) o;
        return genero == prova.genero && termidado == prova.termidado && modalidade == prova.modalidade && Objects.equals(nome, prova.nome) && Objects.equals(faixaEtaria, prova.faixaEtaria) && Objects.equals(categoriaPeso, prova.categoriaPeso) && Objects.equals(atletas, prova.atletas) && Arrays.equals(tabelaEstado, prova.tabelaEstado) && Objects.equals(vencedor, prova.vencedor) && Objects.equals(segundo, prova.segundo);
    }
}
