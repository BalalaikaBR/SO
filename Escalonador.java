import java.util.*;

public class Escalonador {
    private List<Processo> processos;
    private CPU cpu;
    private int quantum;
    private int tempoOcioso;
    private int tempoAtual;
    private List<Processo> processosConcluidos;
    private Map<Algoritmo, EstrategiaEscalonamento> estrategias;

    public enum Algoritmo {
        FCFS, SJF, ROUND_ROBIN
    }

    public Escalonador(CPU cpu, int quantum, int tempoOcioso){
        this.cpu = cpu;
        this.quantum = quantum;
        this.tempoAtual = 0;
        this.tempoOcioso = 0;
        this.processos = new ArrayList<>();
        this.processosConcluidos = new ArrayList<>();

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
        if (estrategia == null) {
            System.out.println("Algoritmo não suportado.");
            return;
        }

        estrategia.executar(processos, cpu, quantum, tempoAtual, processosConcluidos);
        exibirEstatisticas();
        reiniciarSimulacao();
    }

    private void exibirEstatisticas() {
        System.out.println("\n--- Estatísticas ---");
        System.out.println("Total de processos: " + processosConcluidos.size());
        System.out.println("Tempo total de execução: " + cpu.getTempoTotal());
        System.out.println("Tempo ocioso da CPU: " + cpu.getTempoOcioso());

        double tempoEsperaMedio = processosConcluidos.stream()
                .mapToInt(Processo::getTempoEspera)
                .average().orElse(0);

        double tempoRetornoMedio = processosConcluidos.stream()
                .mapToInt(Processo::getTempoRetorno)
                .average().orElse(0);

        System.out.printf("Tempo de espera médio: %.2f\n", tempoEsperaMedio);
        System.out.printf("Tempo de retorno médio: %.2f\n", tempoRetornoMedio);

        System.out.println("\nProcessos concluídos:");
        for (Processo p : processosConcluidos) {
            System.out.printf("P%d - Espera: %d, Retorno: %d\n",
                    p.getId(), p.getTempoEspera(), p.getTempoRetorno());
        }
    }

    private void reiniciarSimulacao() {
        for (Processo p : processos) {
            p.setTempoEspera(0);
            p.setTempoRetorno(0);
        }
        processosConcluidos.clear();
        tempoAtual = 0;
    }
}
