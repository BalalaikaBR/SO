public class CPU {
    private Processo processoAtual;
    private int tempoOcioso;
    private int tempoTotalExecucao;
    
    public CPU() {
        this.processoAtual = null;
        this.tempoOcioso = 0;
        this.tempoTotalExecucao = 0;
    }
    
    public boolean estaOciosa() {
        return processoAtual == null;
    }
    
    public void executarInstrucao() {
        if (processoAtual != null) {
            processoAtual.setQtdInstrucao(processoAtual.getQtdInstrucao() - 1);
            tempoTotalExecucao++;
        } else {
            tempoOcioso++;
        }
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
    
    public int getTempoOcioso() {
        return tempoOcioso;
    }
    
    public int getTempoTotalExecucao() {
        return tempoTotalExecucao;
    }
    
    public boolean processoConcluido() {
        return processoAtual != null && processoAtual.getQtdInstrucao() <= 0;
    }
}