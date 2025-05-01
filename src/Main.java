import correcaoTabelaVerdade.Correcao;
import util.Input;

public class Main {
    public static void main(String[] args) {
        Correcao correcao = new Correcao();
        correcao.set();
        correcao.corrigirQuantidadeParenteses();
        correcao.printQuestao();
        correcao.resolverQuestao();
        correcao.printResultado();
        Input.getInstance().closeScan();
    }
}