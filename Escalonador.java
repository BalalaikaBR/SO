
import java.util.*;

public class Escalonador {

    private List<Processo> processos;
    private CPU cpu;
    private int quantum;
    private Map<Algoritmo, EstrategiaEscalonamento> estrategias;

    public enum Algoritmo {
        FCFS, SJF, ROUND_ROBIN
    }

    public Escalonador(CPU cpu, int quantum) {
        this.cpu = cpu;
        this.quantum = quantum;
        this.processos = new ArrayList<>();

        estrategias = new HashMap<>();
        estrategias.put(Algoritmo.FCFS, new FCFS());
        estrategias.put(Algoritmo.SJF, new SJF());
        estrategias.put(Algoritmo.ROUND_ROBIN, new RoundRobin());
    }

    public void adicionarProcessos(List<Processo> novosProcessos) {
        processos.addAll(novosProcessos);
    }

    public void adicionarProcesso(Processo processo) {
        processos.add(processo);
    }

    public void executar(Algoritmo algoritmo) {
        System.out.println("\n=== Executando " + algoritmo + " ===");

        EstrategiaEscalonamento estrategia = estrategias.get(algoritmo);
        EscalonadorContexto contexto = new EscalonadorContexto();
        cpu.resetarTempos();

        List<Processo> concluidos = new ArrayList<>();
        estrategia.executar(new ArrayList<>(processos), cpu, quantum, contexto, concluidos);

        exibirEstatisticas(concluidos, contexto);
        reiniciarSimulacao();
    }

    private void exibirEstatisticas(List<Processo> concluidos, EscalonadorContexto contexto) {
        System.out.println("\n--- Estatísticas ---");
        System.out.println("Total de processos: " + concluidos.size());
        System.out.println("Tempo total de execução: " + contexto.tempoAtual);
        System.out.println("Tempo ocioso da CPU: " + contexto.tempoOcioso);

        double tempoEsperaMedio = concluidos.stream()
                .mapToInt(Processo::getTempoEspera)
                .average().orElse(0);

        double tempoRetornoMedio = concluidos.stream()
                .mapToInt(Processo::getTempoRetorno)
                .average().orElse(0);

        System.out.printf("Tempo de espera médio: %.2f\n", tempoEsperaMedio);
        System.out.printf("Tempo de retorno médio: %.2f\n", tempoRetornoMedio);

        System.out.println("\nProcessos concluídos:");
        for (Processo p : concluidos) {
            System.out.printf("P%d - Espera: %d, Retorno: %d\n",
                    p.getId(), p.getTempoEspera(), p.getTempoRetorno());
        }
    }

    private void reiniciarSimulacao() {
        for (Processo p : processos) {
            p.resetar();
        }
    }
}
