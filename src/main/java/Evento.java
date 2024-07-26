import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class Evento implements Serializable {
    private String nome;
    private LinkedList<Prova> provas;
    private String tipoEvento;
    private String local;
    private Date dataInicio;
    private Date dataFim;

    private String estado;
    public Evento(String nome, String tipoEvento,String local, Date dataInicio, Date dataFim){
        this.nome = nome;
        this.tipoEvento = tipoEvento;
        this.local = local;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.provas = new LinkedList<Prova>();
        this.estado = "A decorrer";
    }

    public String getNome() {
        return nome;
    }

    public LinkedList<Prova> getProvas() {
        return provas;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProvas(LinkedList<Prova> provas) {
        this.provas = provas;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void adicionar(Prova prova){
        provas.add(prova);
    }

    public void remover(Prova prova){
        provas.remove(prova);
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getLocal() {
        return local;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataInicioString = simpleDateFormat.format(this.dataInicio);
        String dataFimString = simpleDateFormat.format(this.dataFim);
        return nome + " realizado de " + dataInicioString + " a " + dataFimString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(nome, evento.nome) && Objects.equals(provas, evento.provas) && Objects.equals(tipoEvento, evento.tipoEvento) && Objects.equals(local, evento.local) && Objects.equals(dataInicio, evento.dataInicio) && Objects.equals(dataFim, evento.dataFim) && Objects.equals(estado, evento.estado);
    }

}
