package GUI;

import Models.Grafo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.Arrays;

public class ControlPanel extends JPanel {
    public ControlPanel(MapPanel mapPanel) {
        Grafo grafo = new Grafo(13);
        setLayout(new BorderLayout());
        setBackground(null);
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        setBorder(topBorder);

        // Painel para labels e comboboxes (disposição vertical)
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS)); // 2 linhas, 2 colunas
        comboBoxPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        comboBoxPanel.setBackground(null);

        JPanel originPanel = new JPanel();
        originPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));

        JLabel originLabel = new JLabel("Origem:");
        originLabel.setForeground(Color.red);
        originPanel.add(originLabel);
        JComboBox<String> originComboBox = new JComboBox<>(getCities());
        originComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setOrigemSelecionada((String) originComboBox.getSelectedItem());
            }
        });
        originPanel.add(originComboBox);

        JPanel destinationPanel = new JPanel();
        destinationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));

        JLabel destinationLabel = new JLabel("Destino:");
        destinationLabel.setForeground(new Color(61, 99, 221));

        destinationPanel.add(destinationLabel);

        JComboBox<String> destinationComboBox = new JComboBox<>(getCities());
        destinationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setDestinoSelecionado((String) destinationComboBox.getSelectedItem());
            }
        });
        destinationPanel.add(destinationComboBox);

        comboBoxPanel.add(originPanel);
        comboBoxPanel.add(destinationPanel);

        add(comboBoxPanel, BorderLayout.WEST); // Posiciona comboBoxPanel à esquerda

        // Painel para os botões (disposição horizontal)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(null);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton clearButton = new JButton("Limpar Mapa");
        clearButton.setForeground(Color.RED);
        clearButton.setBackground(null);
        clearButton.setBorderPainted(false);
        clearButton.setContentAreaFilled(false);
        buttonPanel.add(clearButton);

        JTextField caminhoMinimo = new JTextField(10);
        caminhoMinimo.setEditable(false);
        caminhoMinimo.setHorizontalAlignment(JTextField.CENTER);
        buttonPanel.add(caminhoMinimo);

        JButton searchButton = new JButton("Buscar");
        searchButton.setBackground(new Color(61, 99, 221));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = (String) originComboBox.getSelectedItem();
                String destino = (String) destinationComboBox.getSelectedItem();
                inicializarGrafo(grafo);

                origem = removerAcentos(origem.toLowerCase());
                destino = removerAcentos(destino.toLowerCase());
                int[] distancias = grafo.dijkstra(origem);
                for (int i = 0; i < 13; i++) {
                    if(destino.equals(grafo.getDadosVertices()[i])) {
                        caminhoMinimo.setText(distancias[i] + " Km");
                        System.out.println("Menor caminho de " + origem + " para " + grafo.getDadosVertices()[i] + ": " + distancias[i]);
                    }
                }
            }
        });
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.EAST); // Posiciona buttonPanel à direita

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                originComboBox.setSelectedIndex(0);
                destinationComboBox.setSelectedIndex(0);
                caminhoMinimo.setText("");
                mapPanel.restaurarCoresOriginais();
            }
        });
    }

    private String[] getCities() {
        return new String[]{
                "", "São José do Rio Preto", "Araraquara", "Ribeirão Preto",
                "Araçatuba", "Piracicaba", "Campinas", "São José dos Campos",
                "Marília", "Bauru", "Sorocaba", "Presidente Prudente",
                "São Paulo", "Santos"
        };
    }

    private static void inicializarGrafo(Grafo grafo) {
        String[] cidades = MapControl.getCidades();
        //System.out.println(Arrays.toString(cidades));
        for (int i = 0; i < cidades.length; i++){
            grafo.adicionarDadosVertice(i, removerAcentos(cidades[i].replace(" ", "").toLowerCase()));
        }

        grafo.adicionarAresta(4, 0, 168);
        grafo.adicionarAresta(11, 9, 222);
        grafo.adicionarAresta(0, 11, 185);
        grafo.adicionarAresta(6, 7, 191);
        grafo.adicionarAresta(1, 11, 71);
        grafo.adicionarAresta(11, 8, 109);
        grafo.adicionarAresta(12, 8, 78);
        grafo.adicionarAresta(3, 7, 105);
        grafo.adicionarAresta(7, 10, 244);
        grafo.adicionarAresta(2, 10, 472);
        grafo.adicionarAresta(8, 10, 100);
        grafo.adicionarAresta(8, 5, 85);

    }

    public static String removerAcentos(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");
        textoNormalizado = textoNormalizado.replaceAll("\\s", "");
        return textoNormalizado;
    }
}
