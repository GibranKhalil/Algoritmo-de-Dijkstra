package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

public class MapControl extends JFrame {
    public Color background = Color.WHITE;
    public MapControl() {
        super.setTitle("Algoritmo de Dijkstra");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Configura layout do JFrame
        setLayout(new BorderLayout());

        // Adiciona o painel de desenho ao centro do JFrame
        add(new MapPanel(), BorderLayout.CENTER);

        // Adiciona o painel de controle ao sul do JFrame
        add(new ControlPanel(), BorderLayout.SOUTH);
        super.setBackground(background);
        setVisible(true);
    }

    public static void main(String[] args) {
        MapControl mapControl = new MapControl();
    }

    class MapPanel extends JPanel {
        // Definição das cidades e suas posições
        private final Map<String, Point> cities = new HashMap<>();
        private final Map<String, Integer> distances = new HashMap<>();

        public MapPanel() {
            cities.put("São José do Rio Preto", new Point(128, 80));
            cities.put("Araraquara", new Point(420, 144));
            cities.put("Ribeirão Preto", new Point(824, 160));
            cities.put("Araçatuba", new Point(100, 240));
            cities.put("Piracicaba", new Point(300, 240));
            cities.put("Campinas", new Point(520, 280));
            cities.put("São José dos Campos", new Point(848, 300));
            cities.put("Marília", new Point(50, 380));
            cities.put("Bauru", new Point(280, 400));
            cities.put("Sorocaba", new Point(440, 480));
            cities.put("Presidente Prudente", new Point(120, 520));
            cities.put("São Paulo", new Point(700, 460));
            cities.put("Santos", new Point(860, 520));

            // Definição das distâncias entre as cidades
            distances.put("São José do Rio Preto-Araraquara", 168);
            distances.put("Campinas-Ribeirão Preto", 222);
            distances.put("Araraquara-Campinas", 185);
            distances.put("Araçatuba-Bauru", 191);
            distances.put("Piracicaba-Campinas", 71);
            distances.put("Campinas-São Paulo", 109);
            distances.put("São José dos Campos-São Paulo", 78);
            distances.put("Marília-Bauru", 105);
            distances.put("Bauru-Sorocaba", 244);
            distances.put("Presidente Prudente-Sorocaba", 472);
            distances.put("São Paulo-Sorocaba", 100);
            distances.put("São Paulo-Santos", 85);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            super.setBackground(null);

            // Antialiasing para melhorar a qualidade do desenho
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Desenhar as conexões entre as cidades
            drawConnections(g2d);

            // Desenhar as cidades
            drawCities(g2d);
        }

        private void drawConnections(Graphics2D g2d) {
            // Definição das conexões entre as cidades
            String[][] connections = {
                    {"São José do Rio Preto", "Araraquara"},
                    {"Araraquara", "Campinas"},
                    {"Araçatuba", "Bauru"},
                    {"Piracicaba", "Campinas"},
                    {"Campinas", "São Paulo"},
                    {"Campinas", "Ribeirão Preto"},
                    {"São José dos Campos", "São Paulo"},
                    {"Marília", "Bauru"},
                    {"Bauru", "Sorocaba"},
                    {"Presidente Prudente", "Sorocaba"},
                    {"São Paulo", "Sorocaba"},
                    {"São Paulo", "Santos"}
            };

            g2d.setColor(Color.BLACK);
            for (String[] connection : connections) {
                Point p1 = cities.get(connection[0]);
                Point p2 = cities.get(connection[1]);
                if (p1 != null && p2 != null) {
                    g2d.draw(new Line2D.Double(p1.x + 25, p1.y + 25, p2.x + 25, p2.y + 25));
                    // Desenha as distâncias
                    String key = connection[0] + "-" + connection[1];
                    int distance = distances.getOrDefault(key, 0);
                    drawDistance(g2d, p1, p2, distance);
                }
            }
        }

        private void drawCities(Graphics2D g2d) {
            for (Map.Entry<String, Point> entry : cities.entrySet()) {
                String city = entry.getKey();
                Point point = entry.getValue();
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x, point.y, 50, 50);
                g2d.setColor(new Color(242, 242, 242));
                g2d.fill(circle);
                g2d.setColor(Color.BLACK);
                g2d.draw(circle);
                g2d.drawString(city, point.x, point.y + 70);
            }
        }

        private void drawDistance(Graphics2D g2d, Point p1, Point p2, int distance) {
            int midX = (p1.x + p2.x) / 2;
            int midY = (p1.y + p2.y) / 2;
            g2d.drawString(distance + "KM", midX, midY);
        }
    }

    class ControlPanel extends JPanel {
        public ControlPanel() {
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
            originPanel.add(originLabel);
            JComboBox<String> originComboBox = new JComboBox<>(getCities());
            originPanel.add(originComboBox);

            JPanel destinationPanel = new JPanel();
            destinationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
            JLabel destinationLabel = new JLabel("Destino:");
            destinationPanel.add(destinationLabel);

            JComboBox<String> destinationComboBox = new JComboBox<>(getCities());
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


            JButton searchButton = new JButton("Buscar");
            searchButton.setBackground(new Color(61, 99, 221));
            searchButton.setForeground(Color.WHITE);
            buttonPanel.add(searchButton);

            add(buttonPanel, BorderLayout.EAST); // Posiciona buttonPanel à direita
        }

        private String[] getCities() {
            return new String[] {
                    "São José do Rio Preto", "Araraquara", "Ribeirão Preto",
                    "Araçatuba", "Piracicaba", "Campinas", "São José dos Campos",
                    "Marília", "Bauru", "Sorocaba", "Presidente Prudente",
                    "São Paulo", "Santos"
            };
        }
    }
}
