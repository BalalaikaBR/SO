import java.util.*;

public class SJF implements EstrategiaEscalonamento {
    @Override
    public void executar(List<Processo> processos, CPU cpu, int quantum, int tempoAtual, List<Processo> processosConcluidos) {
        processos.sort(Comparator.comparingInt(Processo::getQtdInstrucao));

        for (Processo processo : processos) {
            if (tempoAtual < processo.getTempoChegada()) {
                tempoAtual = processo.getTempoChegada();
            }

            processo.setTempoEspera(tempoAtual - processo.getTempoChegada());

            cpu.setProcessoAtual(processo);
            while (processo.getQtdInstrucao() > 0) {
                cpu.executarInstrucao();
                tempoAtual++;
            }

            processo.setTempoRetorno(tempoAtual - processo.getTempoChegada());
            processosConcluidos.add(processo);
            cpu.liberarCPU();
        }
    }
}
