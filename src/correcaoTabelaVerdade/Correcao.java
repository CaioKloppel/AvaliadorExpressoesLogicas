package correcaoTabelaVerdade;

import util.Funcoes;

import java.util.ArrayList;

public final class Correcao {
    private ArrayList<String> questao;

    public void set() {
        questao = new ArrayList<>();

        MetodosPrincipaisCorrecao.set_questao(questao);
    }

    public void corrigirQuantidadeParenteses() {
        int qntdAbertura, qntdFechamento;
        qntdAbertura = Funcoes.quantidade(questao, "(");
        qntdFechamento = Funcoes.quantidade(questao, ")");

        if (qntdAbertura > qntdFechamento) {
            while (qntdAbertura > qntdFechamento) {
                questao.add(")");
                qntdFechamento = Funcoes.quantidade(questao, ")");
            }
        }
    }

    public void printQuestao() {
        System.out.println(String.join(" ", questao));
    }

    public void resolverQuestao() {
        if (questao.contains("(")) {
            MetodosPrincipaisCorrecao.resolver_parenteses(questao);
        }

        MetodosPrincipaisCorrecao.get_result(questao);
    }

    public void printResultado() {
        if (questao.size() == 1) {
            if (questao.getFirst().chars().filter(c -> c == '~').count() % 2 == 0){
                System.out.println("A resposta da questão é: " + questao.getFirst().replace("~", ""));
            } else {
                System.out.println("A resposta da questão é: " + "~" + questao.getFirst().replace("~", ""));
            }
        }
        if (!(questao.getFirst().equals("V") || questao.getFirst().equals("~V") || questao.getFirst().equals("F") || questao.getFirst().equals("~F"))) {
            for (int i = 0; i < MetodosPrincipaisCorrecao.getListaFinal().size(); i++) {
                if (MetodosPrincipaisCorrecao.getListaFinal().get(i).chars().filter(c -> c == '~').count() % 2 == 0) {
                    System.out.println(((char) ('W' + i)) + " = " + MetodosPrincipaisCorrecao.getListaFinal().get(i).replace("~", ""));
                } else {
                    System.out.println(((char) ('W' + i)) + " = " + "~" + MetodosPrincipaisCorrecao.getListaFinal().get(i).replace("~", ""));
                }
            }
        }
    }
}
