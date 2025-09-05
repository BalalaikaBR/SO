import java.util.List;
import java.util.Comparator;

public class FCFS implements EstrategiaEscalonamento {
    @Override
    public void executar(List<Processo> processos, CPU cpu, int quantum,
                         EscalonadorContexto contexto, List<Processo> processosConcluidos) {
        processos.sort(Comparator.comparingInt(Processo::getTempoChegada));

        for (Processo processo : processos) {
            if (contexto.tempoAtual < processo.getTempoChegada()) {
                contexto.tempoOcioso += processo.getTempoChegada() - contexto.tempoAtual;
                contexto.tempoAtual = processo.getTempoChegada();
            }

            processo.setTempoEspera(contexto.tempoAtual - processo.getTempoChegada());
            cpu.setProcessoAtual(processo);

            while (processo.getQtdInstrucao() > 0) {
                cpu.executarInstrucao();
                contexto.tempoAtual++;
            }

            processo.setTempoRetorno(contexto.tempoAtual - processo.getTempoChegada());
            processosConcluidos.add(processo);
            cpu.liberarCPU();
        }
    }
}
