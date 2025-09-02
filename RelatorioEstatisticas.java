import java.util.List;
public class RelatorioEstatisticas {
    public static void exibir(List<Processo> processos, int tempoTotal, int tempoOcioso) {
        System.out.println("\n--- Estatísticas ---");
        System.out.println("Total de processos: " + processos.size());
        System.out.println("Tempo total de execução: " + tempoTotal);
        System.out.println("Tempo ocioso da CPU: " + tempoOcioso);

        double tempoEsperaMedio = processos.stream()
            .mapToInt(Processo::getTempoEspera)
            .average().orElse(0);

        double tempoRetornoMedio = processos.stream()
            .mapToInt(Processo::getTempoRetorno)
            .average().orElse(0);

        System.out.printf("Tempo de espera médio: %.2f\n", tempoEsperaMedio);
        System.out.printf("Tempo de retorno médio: %.2f\n", tempoRetornoMedio);

        System.out.println("\nProcessos concluídos:");
        for (Processo p : processos) {
            System.out.printf("P%d - Espera: %d, Retorno: %d\n",
                p.getId(), p.getTempoEspera(), p.getTempoRetorno());
        }
    }
}
