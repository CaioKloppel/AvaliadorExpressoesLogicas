package tabelaVerdade;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tabela {
    private final ArrayList<String> conectivos = new ArrayList<>();
    private final ArrayList<String> v_f = new ArrayList<>();

    public Tabela(){
        conectivos.addAll(Arrays.asList("AND", "OR", "->", "<->", "↑", "↓", "⊕"));
        v_f.addAll(Arrays.asList("V", "F"));
    }

    public ArrayList<String> getConectivos() {
        return conectivos;
    }

    public ArrayList<String> getV_f() {
        return v_f;
    }
}