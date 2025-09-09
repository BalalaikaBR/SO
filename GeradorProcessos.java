import java.util.*;

public class GeradorProcessos {
    private Random random;
    private int ultimoId;

    public GeradorProcessos() {
        this.random = new Random();
        this.ultimoId = 0;
    }

    public List<Processo> gerarLoteProcessos(int quantidade, int tempoAtual) {
        List<Processo> processos = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            int qtdInstrucoes = random.nextInt(41) + 10;
            processos.add(new Processo(++ultimoId, qtdInstrucoes, tempoAtual));
        }
        return processos;
    }

    public Processo gerarProcesso(int tempoAtual) {
        int qtdInstrucoes = random.nextInt(41) + 10;
        return new Processo(++ultimoId, qtdInstrucoes, tempoAtual);
    }
}
