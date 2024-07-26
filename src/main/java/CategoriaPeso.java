import java.io.Serializable;

public enum CategoriaPeso implements Serializable {
    MENOS55("<55Kg"),
    MENOS65("<65Kg"),
    MENOS75("<75Kg"),
    MENOS85("<85Kg"),
    MENOS95("<95Kg"),
    MAISIGUAL95(">=95Kg");

    private String descricao;

    CategoriaPeso(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}