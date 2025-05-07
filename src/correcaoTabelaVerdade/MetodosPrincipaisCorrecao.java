package correcaoTabelaVerdade;

import util.Input;

import java.util.ArrayList;
import java.util.Arrays;


abstract class MetodosPrincipaisCorrecao {

    private static final ArrayList<String> listaFinal = new ArrayList<>();

    public static ArrayList<String> getListaFinal() { return listaFinal; }
    private static int posicao = 0;



    static void setQuestao(ArrayList<String> questao){
            System.out.println("Insira a questão: ");
            String pergunta = Input.getInstance().scanNextLine().toUpperCase();
            pergunta = MetodosBaseCorrecao.checkPergunta(pergunta);
            questao.addAll(Arrays.asList(pergunta.split(" ")));
    }

    static void prepareQuestao(ArrayList<String> questao){
        MetodosBaseCorrecao.addParenthesesByPrecedence(questao);
        MetodosBaseCorrecao.tratarAbsorcao(questao);
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
            prepareQuestao(lista);
        }
    }
}