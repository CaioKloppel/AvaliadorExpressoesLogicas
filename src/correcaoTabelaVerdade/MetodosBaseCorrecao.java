package correcaoTabelaVerdade;

import java.util.ArrayList;

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
