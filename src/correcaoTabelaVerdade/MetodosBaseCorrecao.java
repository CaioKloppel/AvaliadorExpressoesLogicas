package correcaoTabelaVerdade;

import util.Funcoes;
import util.Input;

import java.util.ArrayList;
import java.util.Arrays;

abstract class MetodosBaseCorrecao {
    static int encontrarFechamento(ArrayList<String> lista, int indiceAbertura) {
        int contador = 1;
        for (int i = indiceAbertura + 1; i < lista.size(); i++) {
            if (lista.get(i).equals(")")) {
                contador--;
            }
            if (contador == 0) {
                return i;
            }
        }
        return -1;
    }

    static String checkPergunta(String pergunta){
        ArrayList<String> checker = new ArrayList<>(Arrays.asList(pergunta.trim().split(" ")));
        int quantidadeConectivos, quantidadeVariaveis, quantidadeParentesesAbertos, quantidadeParentesesFechados;
        quantidadeVariaveis = 0;
        for (String item : checker) {
            if (item.length() == 1 && Character.isLetter(item.charAt(0))) {
                quantidadeVariaveis++;
            } else if (item.equals("AND") || item.equals("OR") || item.equals("->") || item.equals("<->")
                    || item.equals("↑") || item.equals("↓") || item.equals("⊕")) {
                continue;
            } else if (item.length() == 1 && (item.charAt(0) == '~' || item.charAt(0) == '(' || item.charAt(0) == ')')) {
                continue;
            } else {
                System.out.println("Insira a questão novamente, ocorreu um erro de digitação, todos os itens devem estar separados por espaços: ");
                String correcao = Input.getInstance().scanNextLine().toUpperCase();
                return checkPergunta(correcao);
            }
        }

        quantidadeConectivos = Funcoes.quantidade(checker, "AND");
        quantidadeConectivos += Funcoes.quantidade(checker, "OR");
        quantidadeConectivos += Funcoes.quantidade(checker, "->");
        quantidadeConectivos += Funcoes.quantidade(checker, "<->");
        quantidadeConectivos += Funcoes.quantidade(checker, "↑");
        quantidadeConectivos += Funcoes.quantidade(checker, "↓");
        quantidadeConectivos += Funcoes.quantidade(checker, "⊕");

        quantidadeParentesesAbertos = Funcoes.quantidade(checker, "(");
        quantidadeParentesesFechados = Funcoes.quantidade(checker, ")");

        if (quantidadeConectivos + 1 != quantidadeVariaveis || quantidadeParentesesAbertos != quantidadeParentesesFechados){
            System.out.println("Insira a questão novamente, ocorreu um erro de digitação: ");
            String correcao = Input.getInstance().scanNextLine().toUpperCase();
            correcao = checkPergunta(correcao);
            return correcao;
        } else {
            return pergunta;
        }
    }

    static String solveX(ArrayList<String> lista){
        if ((lista.get(0).contains("~") && lista.get(2).contains("~") || (!lista.get(0).contains("~") && !lista.get(2).contains("~")))){
            if (lista.get(0).equals(lista.get(2))){
                switch (lista.get(1)) {
                    case "AND", "OR" -> {
                        return lista.getFirst();
                    }
                    case "↑", "↓" -> {
                        if (lista.getFirst().contains("~")) {
                            return lista.getFirst().replace("~", "");
                        } else {
                            return "~" + lista.getFirst();
                        }
                    }
                    case "->", "<->" -> {
                        return "V";
                    }
                    default -> {
                        return "F";
                    }
                }
            }
        } else if (lista.getFirst().replace("~", "").equals(lista.get(2).replace("~", ""))){
            switch (lista.get(1)){
                case "AND", "<->", "↓" -> {
                    return "F";
                }
                case "OR", "↑", "⊕" -> {
                    return "V";
                }
                default -> {
                    return lista.get(2);
                }
            }
        } return null;
    }

    static void result(ArrayList<String> lista_resultado, int index, String v_f){
        lista_resultado.set(index - 1, v_f);
        lista_resultado.remove(index + 1);
        lista_resultado.remove(index);
    }
}
