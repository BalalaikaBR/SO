import java.util.*;

public class Escalonador {      
    private List<Processo> processos;
    private CPU cpu;
    private int quantum;
    private int tempoAtual;
    private List<Processo> processosConcluidos;
    
    public enum Algoritmo {
        FCFS, SJF, ROUND_ROBIN
    }
    
    public Escalonador(CPU cpu, int quantum) {
        this.processos = new ArrayList<>();
        this.cpu = cpu;
        this.quantum = quantum;
        this.tempoAtual = 0;
        this.processosConcluidos = new ArrayList<>();
    }
    
    public void adicionarProcessos(List<Processo> novosProcessos) {
        processos.addAll(novosProcessos);
    }
    
    public void adicionarProcesso(Processo processo) {
        processos.add(processo);
    }
    
    public void executar(Algoritmo algoritmo) {
        System.out.println("\n=== Executando " + algoritmo + " ===");
        
        switch (algoritmo) {
            case FCFS:
                executarFCFS();
                break;
            case SJF:
                executarSJF();
                break;
            case ROUND_ROBIN:
                executarRoundRobin();
                break;
        }
        
        exibirEstatisticas();
        reiniciarSimulacao();
    }
    
    private void executarFCFS() {
        
        processos.sort(Comparator.comparingInt(Processo::getTempoChegada));
        executarFilaSimples();
    }
    
    private void executarSJF() {
        
        processos.sort(Comparator.comparingInt(Processo::getQtdInstrucao));
        executarFilaSimples();
    }
    
    private void executarFilaSimples() {
        tempoAtual = 0;
        
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
    
    private void executarRoundRobin() {
    tempoAtual = 0;
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
                cpu.executarInstrucao(); 
            }
            cpu.setProcessoAtual(processo);
        }

        
        processo.setTempoEspera(
            processo.getTempoEspera() + (tempoAtual - ultimaVezExecutado.get(processo))
        );

        int tempoExecutado = 0;
        
        while (processo.getQtdInstrucao() > 0 && tempoExecutado < quantum) {
            cpu.executarInstrucao();
            tempoExecutado++;
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
    
    private void exibirEstatisticas() {
        System.out.println("\n--- Estatísticas ---");
        System.out.println("Total de processos: " + processosConcluidos.size());
        System.out.println("Tempo total de execução: " + tempoAtual);
        System.out.println("Tempo ocioso da CPU: " + cpu.getTempoOcioso());
        
        double tempoEsperaMedio = processosConcluidos.stream()
                .mapToInt(Processo::getTempoEspera)
                .average()
                .orElse(0);
        
        double tempoRetornoMedio = processosConcluidos.stream()
                .mapToInt(Processo::getTempoRetorno)
                .average()
                .orElse(0);
        
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