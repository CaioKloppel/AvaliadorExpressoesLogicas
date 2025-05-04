package util;

import java.util.ArrayList;

public class Funcoes {
    public static int quantidade(ArrayList<String> lista, String argumento){
        int contador = 0;
        for (String item : lista){
            if (item.equals(argumento)){
                contador++;
            }
        }
        return contador;
    }
}
