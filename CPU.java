public class CPU {
    private Processo processoAtual;
    private int tempoTotal;
    private int tempoOcioso;

    public CPU() {
        this.processoAtual = null;
        this.tempoTotal = 0;
        this.tempoOcioso = 0;
    }

    public boolean executarInstrucao() {
        if (processoAtual != null && processoAtual.getQtdInstrucao() > 0) {
            processoAtual.setQtdInstrucao(processoAtual.getQtdInstrucao() - 1);
        } else {
            tempoOcioso++;
        }
        tempoTotal++;
        return true;
    }

    public boolean estaOciosa() {
        return processoAtual == null;
    }

    public Processo getProcessoAtual() {
        return processoAtual;
    }

    public void setProcessoAtual(Processo processo) {
        this.processoAtual = processo;
    }

    public void liberarCPU() {
        this.processoAtual = null;
    }

    public boolean processoConcluido() {
        return processoAtual != null && processoAtual.getQtdInstrucao() <= 0;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public int getTempoOcioso() {
        return tempoOcioso;
    }

    public void resetarTempos() {
        this.tempoTotal = 0;
        this.tempoOcioso = 0;
    }
}
