import java.io.Serializable;

public enum FaixaEtaria implements Serializable {
    SUB12("Sub-12"),
    SUB14("Sub-14"),
    SUB16("Sub-16"),
    SUB18("Sub-18"),
    SUB21("Sub-21"),
    SENIORES("Seniores");

    private String descricao;

    FaixaEtaria(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
