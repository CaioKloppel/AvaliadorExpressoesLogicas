package util;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Funcoes {
    public static int get_int(String pergunta)
    {
        int numero = 0;
        boolean entrada_valida = false;
        while (!entrada_valida){
            System.out.print(pergunta);
            try {
                numero = Input.getInstance().scanNextInt();
                Input.getInstance().scanNextLine();
                entrada_valida = true;
            } catch (InputMismatchException e){
                System.out.println("Entrada inválida, aceita apenas números inteiros!");
                Input.getInstance().scanNextLine();
            }
        }

        return numero;
    }

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
