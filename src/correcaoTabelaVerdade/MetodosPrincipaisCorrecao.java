package correcaoTabelaVerdade;

import util.Input;

import java.util.ArrayList;
import java.util.Arrays;


abstract class MetodosPrincipaisCorrecao extends MetodosBaseCorrecao {

    private static final ArrayList<String> listaFinal = new ArrayList<>();

    public static ArrayList<String> getListaFinal() { return listaFinal; }
    static private int posicao = 0;

    static public void set_questao(ArrayList<String> questao){
            System.out.println("Insira a questão: ");
            String pergunta = Input.getInstance().scanNextLine().toUpperCase();
            pergunta = MetodosBaseCorrecao.checkPergunta(pergunta);
            questao.addAll(Arrays.asList(pergunta.split(" ")));
    }

    static void get_result(ArrayList<String> lista_resultado){
        while (lista_resultado.size() != 1) {
            if (lista_resultado.contains("AND") || lista_resultado.contains("↑")){
                int index_and = lista_resultado.indexOf("AND");
                int index_noAnd = lista_resultado.indexOf("↑");

                if (index_noAnd != -1 && (index_and == -1 || index_noAnd < index_and)){
                    if (lista_resultado.get(index_noAnd - 1).equals("V") && lista_resultado.get(index_noAnd + 1).equals("V")){
                        result(lista_resultado, index_noAnd, "F");
                    } else if (lista_resultado.get(index_noAnd - 1).equals("F") || lista_resultado.get(index_noAnd + 1).equals("F")){
                        result(lista_resultado, index_noAnd, "V");
                    } else if (lista_resultado.get(index_noAnd - 1).equals("V")){
                        result(lista_resultado, index_noAnd, "~" + lista_resultado.get(index_noAnd + 1));
                    } else if (lista_resultado.get(index_noAnd + 1).equals("V")){
                        result(lista_resultado, index_noAnd, "~" + lista_resultado.get(index_noAnd - 1));
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_noAnd - 1), lista_resultado.get(index_noAnd), lista_resultado.get(index_noAnd + 1)));
                        String resultado = solveX(listaCheck);
                        if (resultado != null){
                            result(lista_resultado, index_noAnd, resultado);
                        } else if (listaFinal.contains(lista_resultado.get(index_noAnd - 1) + " " + lista_resultado.get(index_noAnd) + " " + lista_resultado.get(index_noAnd + 1))){
                            int index = listaFinal.indexOf(lista_resultado.get(index_noAnd - 1) + " " + lista_resultado.get(index_noAnd) + " " + lista_resultado.get(index_noAnd + 1));
                            result(lista_resultado, index_noAnd, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(lista_resultado.get(index_noAnd - 1) + " " + lista_resultado.get(index_noAnd) + " " + lista_resultado.get(index_noAnd + 1));
                            result(lista_resultado, index_noAnd, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }else{
                    if (lista_resultado.get(index_and - 1).equals("V") && lista_resultado.get(index_and + 1).equals("V")){
                        result(lista_resultado, index_and, "V");
                    } else if (lista_resultado.get(index_and - 1).equals("F") || lista_resultado.get(index_and + 1).equals("F")){
                        result(lista_resultado, index_and, "F");
                    } else if (lista_resultado.get(index_and - 1).equals("V")){
                        result(lista_resultado, index_and, lista_resultado.get(index_and + 1));
                    } else if (lista_resultado.get(index_and + 1).equals("V")){
                        result(lista_resultado, index_and, lista_resultado.get(index_and - 1));
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_and - 1), lista_resultado.get(index_and), lista_resultado.get(index_and + 1)));
                        String resultado = solveX(listaCheck);
                        if (resultado != null) {
                            result(lista_resultado, index_and, resultado);
                        } else if (listaFinal.contains(lista_resultado.get(index_and - 1) + " " + lista_resultado.get(index_and) + " " + lista_resultado.get(index_and + 1))){
                            int index = listaFinal.indexOf(lista_resultado.get(index_and - 1) + " " + lista_resultado.get(index_and) + " " + lista_resultado.get(index_and + 1));
                            result(lista_resultado, index_and, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(lista_resultado.get(index_and - 1) + " " + lista_resultado.get(index_and) + " " + lista_resultado.get(index_and + 1));
                            result(lista_resultado, index_and, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("OR") || lista_resultado.contains("↓")) {
                int index_or = lista_resultado.indexOf("OR");
                int index_noOr = lista_resultado.indexOf("↓");

                if (index_noOr != -1 && (index_or == -1 || index_noOr < index_or)){
                    if (lista_resultado.get(index_noOr - 1).equals("V") || lista_resultado.get(index_noOr + 1).equals("V")){
                        result(lista_resultado, index_noOr, "F");
                    } else if (lista_resultado.get(index_noOr - 1).equals("F") && lista_resultado.get(index_noOr + 1).equals("F")){
                        result(lista_resultado, index_noOr, "V");
                    } else if (lista_resultado.get(index_noOr - 1).equals("F")){
                        result(lista_resultado, index_noOr, "~" + lista_resultado.get(index_noOr + 1));
                    } else if (lista_resultado.get(index_noOr + 1).equals("F")) {
                        result(lista_resultado, index_noOr, "~" + lista_resultado.get(index_noOr - 1));
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_noOr - 1), lista_resultado.get(index_noOr), lista_resultado.get(index_noOr + 1)));
                        String resultado = solveX(listaCheck);
                        if (resultado != null) {
                            result(lista_resultado, index_noOr, resultado);
                        } else if (listaFinal.contains(lista_resultado.get(index_noOr - 1) + " " + lista_resultado.get(index_noOr) + " " + lista_resultado.get(index_noOr + 1))){
                            int index = listaFinal.indexOf(lista_resultado.get(index_noOr - 1) + " " + lista_resultado.get(index_noOr) + " " + lista_resultado.get(index_noOr + 1));
                            result(lista_resultado, index_noOr, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(lista_resultado.get(index_noOr - 1) + " " + lista_resultado.get(index_noOr) + " " + lista_resultado.get(index_noOr + 1));
                            result(lista_resultado, index_noOr, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }else{
                    if (lista_resultado.get(index_or - 1).equals("V") || lista_resultado.get(index_or + 1).equals("V")){
                        result(lista_resultado, index_or, "V");
                    } else if (lista_resultado.get(index_or - 1).equals("F") && lista_resultado.get(index_or + 1).equals("F")){
                        result(lista_resultado, index_or, "F");
                    } else if (lista_resultado.get(index_or - 1).equals("F")){
                        result(lista_resultado, index_or, lista_resultado.get(index_or + 1));
                    } else if (lista_resultado.get(index_or + 1).equals("F")) {
                        result(lista_resultado, index_or, lista_resultado.get(index_or - 1));
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_or - 1), lista_resultado.get(index_or), lista_resultado.get(index_or + 1)));
                        String resultado = solveX(listaCheck);
                        if (resultado != null) {
                            result(lista_resultado, index_or, resultado);
                        } else if (listaFinal.contains(lista_resultado.get(index_or - 1) + " " + lista_resultado.get(index_or) + " " + lista_resultado.get(index_or + 1))){
                            int index = listaFinal.indexOf(lista_resultado.get(index_or - 1) + " " + lista_resultado.get(index_or) + " " + lista_resultado.get(index_or + 1));
                            result(lista_resultado, index_or, String.valueOf((char) ('W' + index)));
                        } else {
                            listaFinal.add(lista_resultado.get(index_or - 1) + " " + lista_resultado.get(index_or) + " " + lista_resultado.get(index_or + 1));
                            result(lista_resultado, index_or, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("->")){
                int index = lista_resultado.indexOf("->");

                if (lista_resultado.get(index - 1).equals("F") || lista_resultado.get(index + 1).equals("V")){
                    result(lista_resultado, index, "V");
                } else if (lista_resultado.get(index - 1).equals("V") && lista_resultado.get(index + 1).equals("F")){
                    result(lista_resultado, index, "F");
                } else {
                    if (lista_resultado.get(index - 1).equals("V")){
                        result(lista_resultado, index, lista_resultado.get(index + 1));
                    } else if (lista_resultado.get(index + 1).equals("F")){
                        result(lista_resultado, index, "~" + lista_resultado.get(index - 1));
                    } else {
                        ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index - 1), lista_resultado.get(index), lista_resultado.get(index + 1)));
                        String resultado = solveX(listaCheck);
                        if (resultado != null) {
                            result(lista_resultado, index, resultado);
                        } else if (listaFinal.contains(lista_resultado.get(index - 1) + " " + lista_resultado.get(index) + " " + lista_resultado.get(index + 1))){
                            int indeX = listaFinal.indexOf(lista_resultado.get(index - 1) + " " + lista_resultado.get(index) + " " + lista_resultado.get(index + 1));
                            result(lista_resultado, index, String.valueOf((char) ('W' + indeX)));
                        } else {
                            listaFinal.add(lista_resultado.get(index - 1) + " " + lista_resultado.get(index) + " " + lista_resultado.get(index + 1));
                            result(lista_resultado, index, String.valueOf((char) ('W' + posicao)));
                            posicao++;
                        }
                    }
                }
            }else if (lista_resultado.contains("<->") || lista_resultado.contains("⊕")){
                int index_bi = lista_resultado.indexOf("<->");
                int index_noBi = lista_resultado.indexOf("⊕");

                if (index_noBi != -1 && (index_bi == -1 || index_noBi < index_bi)){
                    if (lista_resultado.get(index_noBi - 1).equals(lista_resultado.get(index_noBi + 1))){
                        result(lista_resultado, index_noBi, "F");
                    } else {
                        if (((lista_resultado.get(index_noBi - 1).equals("V")) && (lista_resultado.get(index_noBi + 1).equals("F"))) || ((lista_resultado.get(index_noBi - 1).equals("F")) && (lista_resultado.get(index_noBi + 1).equals("V")))){
                            result(lista_resultado, index_noBi, "V");
                        } else if (lista_resultado.get(index_noBi - 1).equals("V")){
                            result(lista_resultado, index_noBi, "~" + lista_resultado.get(index_noBi + 1));
                        } else if (lista_resultado.get(index_noBi - 1).equals("F")){
                            result(lista_resultado, index_noBi, lista_resultado.get(index_noBi + 1));
                        } else if (lista_resultado.get(index_noBi + 1).equals("V")){
                            result(lista_resultado, index_noBi, "~" + lista_resultado.get(index_noBi - 1));
                        } else if (lista_resultado.get(index_noBi + 1).equals("F")){
                            result(lista_resultado, index_noBi, lista_resultado.get(index_noBi - 1));
                        } else {
                            ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_noBi - 1), lista_resultado.get(index_noBi), lista_resultado.get(index_noBi + 1)));
                            String resultado = solveX(listaCheck);
                            if (resultado != null) {
                                result(lista_resultado, index_noBi, resultado);
                            } else if (listaFinal.contains(lista_resultado.get(index_noBi - 1) + " " + lista_resultado.get(index_noBi) + " " + lista_resultado.get(index_noBi + 1))){
                                int index = listaFinal.indexOf(lista_resultado.get(index_noBi - 1) + " " + lista_resultado.get(index_noBi) + " " + lista_resultado.get(index_noBi + 1));
                                result(lista_resultado, index_noBi, String.valueOf((char) ('W' + index)));
                            } else {
                                listaFinal.add(lista_resultado.get(index_noBi - 1) + " " + lista_resultado.get(index_noBi) + " " + lista_resultado.get(index_noBi + 1));
                                result(lista_resultado, index_noBi, String.valueOf((char) ('W' + posicao)));
                                posicao++;
                            }
                        }
                    }
                }else{
                    if (lista_resultado.get(index_bi - 1).equals(lista_resultado.get(index_bi + 1))){
                        result(lista_resultado, index_bi, "V");
                    } else {
                        if (((lista_resultado.get(index_bi - 1).equals("V")) && (lista_resultado.get(index_bi + 1).equals("F"))) || ((lista_resultado.get(index_bi - 1).equals("F")) && (lista_resultado.get(index_bi + 1).equals("V")))){
                            result(lista_resultado, index_bi, "F");
                        } else if (lista_resultado.get(index_bi - 1).equals("V")){
                            result(lista_resultado, index_bi, lista_resultado.get(index_bi + 1));
                        } else if (lista_resultado.get(index_bi - 1).equals("F")){
                            result(lista_resultado, index_bi, "~" + lista_resultado.get(index_bi + 1));
                        } else if (lista_resultado.get(index_bi + 1).equals("V")){
                            result(lista_resultado, index_bi, lista_resultado.get(index_bi - 1));
                        } else if (lista_resultado.get(index_bi + 1).equals("F")){
                            result(lista_resultado, index_bi, "~" + lista_resultado.get(index_bi - 1));
                        } else {
                            ArrayList<String> listaCheck = new ArrayList<>(Arrays.asList(lista_resultado.get(index_bi - 1), lista_resultado.get(index_bi), lista_resultado.get(index_bi + 1)));
                            String resultado = solveX(listaCheck);
                            if (resultado != null) {
                                result(lista_resultado, index_bi, resultado);
                            } else if (listaFinal.contains(lista_resultado.get(index_bi - 1) + " " + lista_resultado.get(index_bi) + " " + lista_resultado.get(index_bi + 1))){
                                int index = listaFinal.indexOf(lista_resultado.get(index_bi - 1) + " " + lista_resultado.get(index_bi) + " " + lista_resultado.get(index_bi + 1));
                                result(lista_resultado, index_bi, String.valueOf((char) ('W' + index)));
                            } else {
                                listaFinal.add(lista_resultado.get(index_bi - 1) + " " + lista_resultado.get(index_bi) + " " + lista_resultado.get(index_bi + 1));
                                result(lista_resultado, index_bi, String.valueOf((char) ('W' + posicao)));
                                posicao++;
                            }
                        }
                    }
                }
            }
        }
    }

    static public void resolver_parenteses(ArrayList<String> lista){
        while (lista.contains("(")){
            int indice_inicial = lista.lastIndexOf("(");
            int indice_final = encontrarFechamento(lista, indice_inicial);
            ArrayList<String> lista_resultado = new ArrayList<>(lista.subList(indice_inicial + 1, indice_final));

            get_result(lista_resultado);

            for (int i = 0; i < indice_final - indice_inicial + 1; i++){
                lista.remove(indice_inicial);
            }

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
        }
    }
}