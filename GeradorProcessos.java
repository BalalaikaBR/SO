import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorProcessos { 
    private Random random;
    private int ultimoId;
    private int tempoAtual;
    
    public GeradorProcessos() {
        this.random = new Random();
        this.ultimoId = 0;
        this.tempoAtual = 0;
    }
    
    public List<Processo> gerarLoteProcessos(int quantidade, int tempoAtual) {
        List<Processo> processos = new ArrayList<>();
        this.tempoAtual = tempoAtual;
        
        for (int i = 0; i < quantidade; i++) {
            int qtdInstrucoes = random.nextInt(41) + 10; 
            processos.add(new Processo(++ultimoId, qtdInstrucoes, tempoAtual));
        }
        
        return processos;
    }
    
    public Processo gerarProcesso(int tempoAtual) {
        this.tempoAtual = tempoAtual;
        int qtdInstrucoes = random.nextInt(41) + 10;
        return new Processo(++ultimoId, qtdInstrucoes, tempoAtual);
    }
    
    public int getTempoAtual() {
        return tempoAtual;
    }
}