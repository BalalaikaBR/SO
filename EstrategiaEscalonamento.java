import java.util.List;

public interface EstrategiaEscalonamento {
    void executar(List<Processo> processos, CPU cpu, int quantum,
                  EscalonadorContexto contexto, List<Processo> processosConcluidos);
}
