public class CPU {
    private Processo processoAtual;

    public CPU() {
        this.processoAtual = null;
    }

    public boolean executarInstrucao() {
        if (processoAtual != null && processoAtual.getQtdInstrucao() > 0) {
            processoAtual.setQtdInstrucao(processoAtual.getQtdInstrucao() - 1);
            return true;
        }
        return false;
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
}
