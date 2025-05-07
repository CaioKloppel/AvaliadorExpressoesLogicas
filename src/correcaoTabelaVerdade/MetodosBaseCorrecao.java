package correcaoTabelaVerdade;

import tabelaVerdade.Tabela;
import util.Funcoes;
import util.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

abstract class MetodosBaseCorrecao {
    private static final Tabela tabela = new Tabela();
    private static final List<List<String>> PRECEDENCIA = List.of(
            List.of("AND", "↑"),        // 1º
            List.of("OR" , "↓"),        // 2º
            List.of("->"),              // 3º
            List.of("<->", "⊕")         // 4º
    );
    static int encontrarFechamento(ArrayList<String> lista, int indiceAbertura) {
        int contador = 1;
        for (int i = indiceAbertura + 1; i < lista.size(); i++) {
            String token = lista.get(i);
            if (token.equals("(")) {
                contador++;
            } else if (token.equals(")")) {
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
            }else if (tabela.getConectivos().contains(item)) {}
             else if (item.length() == 1 && (item.charAt(0) == '~' || item.charAt(0) == '(' || item.charAt(0) == ')')){}
             else {
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

        if (quantidadeConectivos + 1 != quantidadeVariaveis || quantidadeParentesesAbertos < quantidadeParentesesFechados){
            System.out.println("Insira a questão novamente, ocorreu um erro de digitação: ");
            String correcao = Input.getInstance().scanNextLine().toUpperCase();
            correcao = checkPergunta(correcao);
            return correcao;
        } else {
            return pergunta;
        }
    }

    static void addParenthesesByPrecedence(ArrayList<String> tokens) {

        for (List<String> nivel : PRECEDENCIA) {

            boolean mudou;

            do {
                mudou = false;

                for (int i = 0; i < tokens.size(); i++) {

                    String op = tokens.get(i);
                    if (!nivel.contains(op)) continue;

                    int leftStart  = findLeftOperandStart(tokens, i - 1);
                    int rightEnd   = findRightOperandEnd (tokens, i + 1);

                    if (leftStart > 0
                            && tokens.get(leftStart - 1).equals("(")
                            && rightEnd + 1 < tokens.size()
                            && tokens.get(rightEnd + 1).equals(")")
                            && matching(tokens, leftStart - 1) == rightEnd + 1)
                        continue;

                    tokens.add(leftStart, "(");
                    tokens.add(rightEnd + 2, ")");
                    mudou = true;
                    break;
                }
            } while (mudou);
        }
    }

    private static int findLeftOperandStart(ArrayList<String> t, int idx) {
        if (idx < 0) return 0;


        if (t.get(idx).equals(")")) {
            int depth = 1;
            idx--;
            while (idx >= 0) {
                if (t.get(idx).equals(")")) depth++;
                else if (t.get(idx).equals("(")) depth--;
                if (depth == 0) break;
                idx--;
            }
            return idx;
        }


        while (idx > 0 && t.get(idx - 1).equals("~")) idx--;
        return idx;
    }


    private static int findRightOperandEnd(ArrayList<String> t, int idx) {
        if (idx >= t.size()) return t.size() - 1;

        if (t.get(idx).equals("(")) {
            int depth = 1;
            idx++;
            while (idx < t.size()) {
                if (t.get(idx).equals("(")) depth++;
                else if (t.get(idx).equals(")")) depth--;
                if (depth == 0) break;
                idx++;
            }
            return idx;
        }
        return idx;
    }


    private static int matching(ArrayList<String> l, int openIdx) {
        int depth = 1;
        for (int i = openIdx + 1; i < l.size(); i++) {
            if (l.get(i).equals("(")) depth++;
            else if (l.get(i).equals(")")) depth--;
            if (depth == 0) return i;
        }
        return -1;
    }

    public static void tratarAbsorcao(ArrayList<String> lista) {

        boolean mudou;
        do {
            mudou = false;

            for (int i = 0; i + 2 < lista.size(); i++) {

                String A = lista.get(i);
                String opExt = lista.get(i + 1);
                if (!(opExt.equals("AND") || opExt.equals("OR"))) continue;
                if (!lista.get(i + 2).equals("(")) continue;

                int abre  = i + 2;
                int fecha = encontrarFechamento(lista, abre);
                if (fecha == -1) continue;

                ArrayList<String> dentro = new ArrayList<>(lista.subList(abre + 1, fecha));
                if (!dentro.contains(A)) continue;

                HashSet<String> opsInside = topLevelOps(dentro);


                if ((opExt.equals("OR")  && opsInside.size() == 1 && opsInside.contains("AND")) || (opExt.equals("AND") && opsInside.size() == 1 && opsInside.contains("OR"))) {
                    lista.subList(i, fecha + 1).clear();
                    lista.add(i, A);
                    mudou = true;
                    break;
                }


                if ((opExt.equals("AND") && opsInside.size() == 1 && opsInside.contains("AND")) ||
                        (opExt.equals("OR")  && opsInside.size() == 1 && opsInside.contains("OR"))) {


                    dentro.removeIf(tok -> tok.equals(A));


                    while (!dentro.isEmpty() && dentro.getFirst().equals(opExt)) dentro.removeFirst();
                    while (!dentro.isEmpty() && dentro.getLast().equals(opExt)) dentro.removeLast();
                    for (int j = 1; j < dentro.size(); ) {
                        if (dentro.get(j).equals(opExt) && dentro.get(j - 1).equals(opExt))
                            dentro.remove(j);
                        else
                            j++;
                    }

                    lista.subList(i, fecha + 1).clear();

                    if (dentro.isEmpty()) {
                        lista.add(i, A);
                    } else {
                        lista.add(i, A);
                        lista.add(i + 1, opExt);
                        if (dentro.size() == 1) {
                            lista.add(i + 2, dentro.getFirst());
                        } else {
                            lista.add(i + 2, "(");
                            lista.addAll(i + 3, dentro);
                            lista.add(i + 3 + dentro.size(), ")");
                        }
                    }
                    mudou = true;
                    break;
                }
            }

            if (mudou) continue;

            for (int abre = 0; abre < lista.size(); abre++) {

                if (!lista.get(abre).equals("(")) continue;
                int fecha = encontrarFechamento(lista, abre);
                if (fecha == -1 || fecha + 2 >= lista.size()) continue;

                String opExt = lista.get(fecha + 1);
                if (!(opExt.equals("AND") || opExt.equals("OR"))) continue;
                String A = lista.get(fecha + 2);

                ArrayList<String> dentro = new ArrayList<>(lista.subList(abre + 1, fecha));
                if (!dentro.contains(A)) continue;

                HashSet<String> opsInside = topLevelOps(dentro);

                if ((opExt.equals("OR")  && opsInside.size() == 1 && opsInside.contains("AND")) ||
                        (opExt.equals("AND") && opsInside.size() == 1 && opsInside.contains("OR"))) {

                    lista.subList(abre, fecha + 3).clear();
                    lista.add(abre, A);
                    mudou = true;
                    break;
                }

                if ((opExt.equals("AND") && opsInside.size() == 1 && opsInside.contains("AND")) ||
                        (opExt.equals("OR")  && opsInside.size() == 1 && opsInside.contains("OR"))) {

                    dentro.removeIf(tok -> tok.equals(A));

                    while (!dentro.isEmpty() && dentro.getFirst().equals(opExt))
                        dentro.removeFirst();
                    while (!dentro.isEmpty() && dentro.getLast().equals(opExt))
                        dentro.removeLast();
                    for (int j = 1; j < dentro.size(); ) {
                        if (dentro.get(j).equals(opExt) && dentro.get(j - 1).equals(opExt))
                            dentro.remove(j);
                        else
                            j++;
                    }

                    lista.subList(abre, fecha + 3).clear();

                    if (dentro.isEmpty()) {
                        lista.add(abre, A);
                    } else {
                        lista.add(abre, A);
                        lista.add(abre + 1, opExt);
                        if (dentro.size() == 1) {
                            lista.add(abre + 2, dentro.getFirst());
                        } else {
                            lista.add(abre + 2, "(");
                            lista.addAll(abre + 3, dentro);
                            lista.add(abre + 3 + dentro.size(), ")");
                        }
                    }
                    mudou = true;
                    break;
                }
            }

        } while (mudou);
    }
    
    private static HashSet<String> topLevelOps(List<String> tokens) {
        HashSet<String> ops = new java.util.HashSet<>();
        int depth = 0;
        for (String t : tokens) {
            if (t.equals("("))       depth++;
            else if (t.equals(")"))  depth--;
            else if (depth == 0 && (t.equals("AND") || t.equals("OR")))
                ops.add(t);
        }
        return ops;
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
