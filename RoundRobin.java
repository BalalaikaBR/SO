import java.util.*;

public class RoundRobin implements EstrategiaEscalonamento {
    @Override
    public void executar(List<Processo> processos, CPU cpu, int quantum,
                         EscalonadorContexto contexto, List<Processo> processosConcluidos) {

        Queue<Processo> fila = new LinkedList<>(processos);
        Map<Processo, Integer> ultimaExecucao = new HashMap<>();

        for (Processo p : processos) {
            ultimaExecucao.put(p, p.getTempoChegada());
        }

        while (!fila.isEmpty()) {
            Processo processo = fila.poll();

            if (contexto.tempoAtual < processo.getTempoChegada()) {
                contexto.tempoOcioso += processo.getTempoChegada() - contexto.tempoAtual;
                contexto.tempoAtual = processo.getTempoChegada();
            }

            processo.setTempoEspera(processo.getTempoEspera() + (contexto.tempoAtual - ultimaExecucao.get(processo)));
            cpu.setProcessoAtual(processo);

            int tempoExecutado = 0;
            while (tempoExecutado < quantum && processo.getQtdInstrucao() > 0) {
                cpu.executarInstrucao();
                contexto.tempoAtual++;
                tempoExecutado++;
            }

            if (processo.getQtdInstrucao() <= 0) {
                processo.setTempoRetorno(contexto.tempoAtual - processo.getTempoChegada());
                processosConcluidos.add(processo);
            } else {
                ultimaExecucao.put(processo, contexto.tempoAtual);
                fila.add(processo);
            }

            cpu.liberarCPU();
        }
    }
}
