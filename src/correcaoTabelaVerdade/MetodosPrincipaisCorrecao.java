package correcaoTabelaVerdade;

import util.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


abstract class MetodosPrincipaisCorrecao {

    private static final ArrayList<String> listaFinal = new ArrayList<>();

    public static ArrayList<String> getListaFinal() { return listaFinal; }
    static private int posicao = 0;

    static public void setQuestao(ArrayList<String> questao){
            System.out.println("Insira a questão: ");
            String pergunta = Input.getInstance().scanNextLine().toUpperCase();
            pergunta = MetodosBaseCorrecao.checkPergunta(pergunta);
            questao.addAll(Arrays.asList(pergunta.split(" ")));
    }

    static public void tratarAbsorcao(ArrayList<String> lista) {

        boolean mudou;
        do {
            mudou = false;

            for (int i = 0; i + 2 < lista.size(); i++) {

                String A = lista.get(i);
                String opExt = lista.get(i + 1);
                if (!(opExt.equals("AND") || opExt.equals("OR"))) continue;
                if (!lista.get(i + 2).equals("(")) continue;

                int abre  = i + 2;
                int fecha = MetodosBaseCorrecao.encontrarFechamento(lista, abre);
                if (fecha == -1) continue;

                ArrayList<String> dentro = new ArrayList<>(lista.subList(abre + 1, fecha));
                if (!dentro.contains(A)) continue;

                HashSet<String> opsInside = MetodosBaseCorrecao.topLevelOps(dentro);


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
                int fecha = MetodosBaseCorrecao.encontrarFechamento(lista, abre);
                if (fecha == -1 || fecha + 2 >= lista.size()) continue;

                String opExt = lista.get(fecha + 1);
                if (!(opExt.equals("AND") || opExt.equals("OR"))) continue;
                String A = lista.get(fecha + 2);

                ArrayList<String> dentro = new ArrayList<>(lista.subList(abre + 1, fecha));
                if (!dentro.contains(A)) continue;

                HashSet<String> opsInside = MetodosBaseCorrecao.topLevelOps(dentro);

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

    static void getResult(ArrayList<String> lista_resultado){
        while (lista_resultado.size() != 1) {
            if (lista_resultado.contains("AND") || lista_resultado.contains("↑")){
                int idxAnd = lista_resultado.indexOf("AND");
                int idxNand = lista_resultado.indexOf("↑");

                if (idxNand != -1 && (idxAnd == -1 || idxNand < idxAnd)){
                    String L = lista_resultado.get(idxNand - 1);
                    String R = lista_resultado.get(idxNand + 1);
                    if (L.equals("V") && R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNand, "F");
                    } else if (L.equals("F") || R.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNand, "V");
                    } else if (L.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNand, "~" + R);
                    } else if (R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNand, "~" + L);
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxNand), R));
                        String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                        if (resultado != null){
                            MetodosBaseCorrecao.result(lista_resultado, idxNand, resultado);
                        } else if (listaFinal.contains(L + " " + lista_resultado.get(idxNand) + " " + R)){
                            int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxNand) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxNand, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(L + " " + lista_resultado.get(idxNand) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxNand, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }else{
                    String L = lista_resultado.get(idxAnd - 1);
                    String R = lista_resultado.get(idxAnd + 1);
                    if (L.equals("V") && R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxAnd, "V");
                    } else if (L.equals("F") || R.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxAnd, "F");
                    } else if (L.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxAnd, R);
                    } else if (R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxAnd, L);
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxAnd), R));
                        String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                        if (resultado != null) {
                            MetodosBaseCorrecao.result(lista_resultado, idxAnd, resultado);
                        } else if (listaFinal.contains(L + " " + lista_resultado.get(idxAnd) + " " + R)){
                            int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxAnd) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxAnd, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(L + " " + lista_resultado.get(idxAnd) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxAnd, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("OR") || lista_resultado.contains("↓")) {
                int idxOr = lista_resultado.indexOf("OR");
                int idxNor = lista_resultado.indexOf("↓");

                if (idxNor != -1 && (idxOr == -1 || idxNor < idxOr)){
                    String L = lista_resultado.get(idxNor - 1);
                    String R = lista_resultado.get(idxNor + 1);
                    if (L.equals("V") || R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNor, "F");
                    } else if (L.equals("F") && R.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNor, "V");
                    } else if (L.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxNor, "~" + R);
                    } else if (R.equals("F")) {
                        MetodosBaseCorrecao.result(lista_resultado, idxNor, "~" + L);
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxNor), R));
                        String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                        if (resultado != null) {
                            MetodosBaseCorrecao.result(lista_resultado, idxNor, resultado);
                        } else if (listaFinal.contains(L + " " + lista_resultado.get(idxNor) + " " + R)){
                            int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxNor) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxNor, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(L + " " + lista_resultado.get(idxNor) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxNor, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }else{
                    String L = lista_resultado.get(idxOr - 1);
                    String R = lista_resultado.get(idxOr + 1);
                    if (L.equals("V") || R.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, idxOr, "V");
                    } else if (L.equals("F") && R.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxOr, "F");
                    } else if (L.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, idxOr, R);
                    } else if (R.equals("F")) {
                        MetodosBaseCorrecao.result(lista_resultado, idxOr, L);
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxOr), R));
                        String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                        if (resultado != null) {
                            MetodosBaseCorrecao.result(lista_resultado, idxOr, resultado);
                        } else if (listaFinal.contains(L + " " + lista_resultado.get(idxOr) + " " + R)){
                            int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxOr) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxOr, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(L + " " + lista_resultado.get(idxOr) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, idxOr, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("->")){
                int index = lista_resultado.indexOf("->");
                String L = lista_resultado.get(index - 1);
                String R = lista_resultado.get(index + 1);
                if (L.equals("F") || R.equals("V")){
                    MetodosBaseCorrecao.result(lista_resultado, index, "V");
                } else if (L.equals("V") && R.equals("F")){
                    MetodosBaseCorrecao.result(lista_resultado, index, "F");
                } else {
                    if (L.equals("V")){
                        MetodosBaseCorrecao.result(lista_resultado, index, R);
                    } else if (R.equals("F")){
                        MetodosBaseCorrecao.result(lista_resultado, index, "~" + L);
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(index), R));
                        String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                        if (resultado != null) {
                            MetodosBaseCorrecao.result(lista_resultado, index, resultado);
                        } else if (listaFinal.contains(L + " " + lista_resultado.get(index) + " " + R)){
                            int indeX = listaFinal.indexOf(L + " " + lista_resultado.get(index) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, index, String.valueOf((char) ('W' + indeX)));
                        } else {
                            listaFinal.add(L + " " + lista_resultado.get(index) + " " + R);
                            MetodosBaseCorrecao.result(lista_resultado, index, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("<->") || lista_resultado.contains("⊕")){
                int idxBi = lista_resultado.indexOf("<->");
                int idxNobi = lista_resultado.indexOf("⊕");

                if (idxNobi != -1 && (idxBi == -1 || idxNobi < idxBi)){
                    String L = lista_resultado.get(idxNobi - 1);
                    String R = lista_resultado.get(idxNobi + 1);
                    if (L.equals(R)){
                        MetodosBaseCorrecao.result(lista_resultado, idxNobi, "F");
                    } else {
                        if (((L.equals("V")) && (R.equals("F"))) || ((L.equals("F")) && (R.equals("V")))){
                            MetodosBaseCorrecao.result(lista_resultado, idxNobi, "V");
                        } else if (L.equals("V")){
                            MetodosBaseCorrecao.result(lista_resultado, idxNobi, "~" + R);
                        } else if (L.equals("F")){
                            MetodosBaseCorrecao.result(lista_resultado, idxNobi, R);
                        } else if (R.equals("V")){
                            MetodosBaseCorrecao.result(lista_resultado, idxNobi, "~" + L);
                        } else if (R.equals("F")){
                            MetodosBaseCorrecao.result(lista_resultado, idxNobi, L);
                        } else {
                            ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxNobi), R));
                            String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                            if (resultado != null) {
                                MetodosBaseCorrecao.result(lista_resultado, idxNobi, resultado);
                            } else if (listaFinal.contains(L + " " + lista_resultado.get(idxNobi) + " " + R)){
                                int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxNobi) + " " + R);
                                MetodosBaseCorrecao.result(lista_resultado, idxNobi, String.valueOf((char) ('W' + index)));
                            } else {
                                listaFinal.add(L + " " + lista_resultado.get(idxNobi) + " " + R);
                                MetodosBaseCorrecao.result(lista_resultado, idxNobi, String.valueOf((char) ('W' + posicao)));
                                posicao++;
                            }
                        }
                    }
                }else{
                    String L = lista_resultado.get(idxBi - 1);
                    String R = lista_resultado.get(idxBi + 1);
                    if (L.equals(R)){
                        MetodosBaseCorrecao.result(lista_resultado, idxBi, "V");
                    } else {
                        if (((L.equals("V")) && (R.equals("F"))) || ((L.equals("F")) && (R.equals("V")))){
                            MetodosBaseCorrecao.result(lista_resultado, idxBi, "F");
                        } else if (L.equals("V")){
                            MetodosBaseCorrecao.result(lista_resultado, idxBi, R);
                        } else if (L.equals("F")){
                            MetodosBaseCorrecao.result(lista_resultado, idxBi, "~" + R);
                        } else if (R.equals("V")){
                            MetodosBaseCorrecao.result(lista_resultado, idxBi, L);
                        } else if (R.equals("F")){
                            MetodosBaseCorrecao.result(lista_resultado, idxBi, "~" + L);
                        } else {
                            ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(L, lista_resultado.get(idxBi), R));
                            String resultado = MetodosBaseCorrecao.solveX(listaCheck);
                            if (resultado != null) {
                                MetodosBaseCorrecao.result(lista_resultado, idxBi, resultado);
                            } else if (listaFinal.contains(L + " " + lista_resultado.get(idxBi) + " " + R)){
                                int index = listaFinal.indexOf(L + " " + lista_resultado.get(idxBi) + " " + R);
                                MetodosBaseCorrecao.result(lista_resultado, idxBi, String.valueOf((char) ('W' + index)));
                            } else {
                                listaFinal.add(L + " " + lista_resultado.get(idxBi) + " " + R);
                                MetodosBaseCorrecao.result(lista_resultado, idxBi, String.valueOf((char) ('W' + posicao)));
                                posicao++;
                            }
                        }
                    }
                }
            }
        }
    }

    static public void resolverParenteses(ArrayList<String> lista){
        while (lista.contains("(")){
            int indice_inicial = lista.lastIndexOf("(");
            int indice_final = MetodosBaseCorrecao.encontrarFechamento(lista, indice_inicial);
            ArrayList<String> lista_resultado = new ArrayList<>(lista.subList(indice_inicial + 1, indice_final));

            getResult(lista_resultado);

            for (int i = 0; i < indice_final - indice_inicial + 1; i++) lista.remove(indice_inicial);


            lista.add(indice_inicial, lista_resultado.getFirst());

            if (indice_inicial - 1 >= 0) {
                if (lista.get(indice_inicial - 1).equals("~")) {
                    if (lista.get(indice_inicial).equals("V")) {
                        lista.set(indice_inicial, "F");
                        lista.remove(indice_inicial - 1);
                    } else if (lista.get(indice_inicial).equals("F")) {
                        lista.set(indice_inicial, "V");
                        lista.remove(indice_inicial - 1);
                    } else {
                        lista.set(indice_inicial, "~" + lista_resultado.getFirst());
                        lista.remove(indice_inicial - 1);
                    }
                }
            }
            tratarAbsorcao(lista);
        }
    }
}