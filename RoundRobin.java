import java.util.*;

public class RoundRobin implements EstrategiaEscalonamento {
    @Override
    public void executar(List<Processo> processos, CPU cpu, int quantum, int tempoAtual, List<Processo> processosConcluidos) {
        Queue<Processo> fila = new LinkedList<>(processos);
        Map<Processo, Integer> ultimaVezExecutado = new HashMap<>();

        for (Processo p : processos) {
            ultimaVezExecutado.put(p, p.getTempoChegada());
        }

        while (!fila.isEmpty()) {
            Processo processo = fila.poll();
            cpu.setProcessoAtual(processo);

            if (tempoAtual < processo.getTempoChegada()) {
                cpu.liberarCPU();
                while (tempoAtual < processo.getTempoChegada()) {
                    tempoAtual++;
                    cpu.executarInstrucao();  // CPU ociosa
                }
                cpu.setProcessoAtual(processo);
            }

            processo.setTempoEspera(processo.getTempoEspera() + (tempoAtual - ultimaVezExecutado.get(processo)));

            int tempoExecutado = 0;
            while (processo.getQtdInstrucao() > 0 && tempoExecutado < quantum) {
                if (cpu.executarInstrucao()) {
                    tempoExecutado++;
                       }
                    tempoAtual++;

            }

            if (processo.getQtdInstrucao() == 0) {
                processo.setTempoRetorno(tempoAtual - processo.getTempoChegada());
                processosConcluidos.add(processo);
            } else {
                ultimaVezExecutado.put(processo, tempoAtual);
                fila.add(processo);
            }

            cpu.liberarCPU();
        }
    }
}
