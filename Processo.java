public class Processo {
    private int id;
    private int qtdInstrucao;
    private int tempoChegada;
    private int tempoEspera;
    private int tempoRetorno;

    public Processo(int id, int qtdInstrucao, int tempoChegada) {
        this.id = id;
        this.qtdInstrucao = qtdInstrucao;
        this.tempoChegada = tempoChegada;
        this.tempoEspera = 0;
        this.tempoRetorno = 0;
    }

    public int getId() { return id; }
    public int getQtdInstrucao() { return qtdInstrucao; }
    public void setQtdInstrucao(int qtdInstrucao) { this.qtdInstrucao = qtdInstrucao; }
    public int getTempoChegada() { return tempoChegada; }
    public int getTempoEspera() { return tempoEspera; }
    public void setTempoEspera(int tempoEspera) { this.tempoEspera = tempoEspera; }
    public int getTempoRetorno() { return tempoRetorno; }
    public void setTempoRetorno(int tempoRetorno) { this.tempoRetorno = tempoRetorno; }

    @Override
    public String toString() {
        return "Processo{" +
                "id=" + id +
                ", instrucoes=" + qtdInstrucao +
                ", chegada=" + tempoChegada +
                '}';
    }
}
