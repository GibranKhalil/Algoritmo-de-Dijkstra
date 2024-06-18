package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class MapControl extends JFrame {
    public static String getOrigemSelecionada() {
        return origemSelecionada;
    }

    public static String getDestinoSelecionado() {
        return destinoSelecionado;
    }

    private static String origemSelecionada;
    private static String destinoSelecionado;

    private static String[] cidades;

    public static String[] getCidades() {
        return cidades;
    }

    public MapControl() {
        super.setTitle("Algoritmo de Dijkstra");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(1000, 800);
        super.setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        MapPanel painelMapa = new MapPanel(origemSelecionada, destinoSelecionado);
        cidades = painelMapa.getCidades().keySet().toArray(new String[0]);
        origemSelecionada = painelMapa.getOrigemSelecionada();
        destinoSelecionado = painelMapa.getDestinoSelecionado();
        ControlPanel painelControle = new ControlPanel(painelMapa);

        add(painelMapa, BorderLayout.CENTER);
        add(painelControle, BorderLayout.SOUTH);
        setVisible(true);
    }

}
