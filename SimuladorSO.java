import java.util.List;

public class SimuladorSO {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        GeradorProcessos gerador = new GeradorProcessos();
        Escalonador escalonador = new Escalonador(cpu, 5);

        List<Processo> processos = gerador.gerarLoteProcessos(5, 0);
        System.out.println("Processos gerados:");
        for (Processo p : processos) {
            System.out.println(p);
        }

        escalonador.adicionarProcessos(processos);

        escalonador.executar(Escalonador.Algoritmo.FCFS);
        escalonador.executar(Escalonador.Algoritmo.SJF);
        escalonador.executar(Escalonador.Algoritmo.ROUND_ROBIN);

        System.out.println("\n\n=== Teste com mais processos ===");
        List<Processo> maisProcessos = gerador.gerarLoteProcessos(3, 10);
        escalonador.adicionarProcessos(maisProcessos);
        escalonador.executar(Escalonador.Algoritmo.ROUND_ROBIN);
    }
}
