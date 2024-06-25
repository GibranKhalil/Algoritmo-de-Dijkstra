package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class MapPanel extends  JPanel{

    // Definição das cidades e suas posições
        private String origemSelecionada;
        private String destinoSelecionado;
        private final Map<String, Point> cities = new HashMap<>();
        private final Map<String, Integer> distances = new HashMap<>();
        private String[] caminho;

        public String[] getCaminho() {
            return caminho;
        }

        public void setCaminho(String[] caminho) {
            this.caminho = caminho;
            repaint();
        }

        public Map<String, Point> getCidades(){
            return cities;
        }

        public String getOrigemSelecionada() {
            return origemSelecionada;
        }

        public String getDestinoSelecionado() {
            return destinoSelecionado;
        }

        public MapPanel(String origemSelecionada, String destinoSelecionado) {

            this.origemSelecionada = origemSelecionada;
            this.destinoSelecionado = destinoSelecionado;

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

        public static String removerAcentos(String texto) {
            String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
            textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");
            return textoNormalizado;
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

        for (String[] connection : connections) {
            Point p1 = cities.get(connection[0]);
            Point p2 = cities.get(connection[1]);
            if (p1 != null && p2 != null) {
                Line2D linha = new Line2D.Double(p1.x + 25, p1.y + 25, p2.x + 25, p2.y + 25);
                if (isConnectionInPath(connection[0], connection[1])) {
                    g2d.setColor(Color.RED); // Cor vermelha para conexões no caminho
                } else {
                    g2d.setColor(Color.BLACK); // Cor preta para outras conexões
                }

                g2d.draw(linha);

                String key = connection[0] + "-" + connection[1];
                int distance = distances.getOrDefault(key, distances.getOrDefault(connection[1] + "-" + connection[0], 0));
                drawDistance(g2d, p1, p2, distance);
            }
        }
    }

    private boolean isConnectionInPath(String city1, String city2) {
        if (caminho == null || caminho.length < 2) {
            return false;
        }

        city1 = removerAcentos(city1).replace(" ", "").toLowerCase();
        city2 = removerAcentos(city2).replace(" ", "").toLowerCase();

        for (int i = 0; i < caminho.length - 1; i++) {
            String start = removerAcentos(caminho[i]).replace(" ", "").toLowerCase();
            String end = removerAcentos(caminho[i + 1]).replace(" ", "").toLowerCase();
            if ((start.equals(city1) && end.equals(city2)) || (start.equals(city2) && end.equals(city1))) {
                return true;
            }
        }
        return false;
    }


        private void drawCities(Graphics2D g2d) {
            for (Map.Entry<String, Point> entry : cities.entrySet()) {
                String city = entry.getKey();
                Point point = entry.getValue();
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x, point.y, 50, 50);

                if(city.equals(origemSelecionada)){
                    g2d.setColor(Color.red);
                } else if (city.equals(destinoSelecionado)) {
                    g2d.setColor(new Color(61, 99, 221));
                }
                else {
                    g2d.setColor(new Color(242, 242, 242));
                }
                g2d.fill(circle);
                g2d.setColor(Color.BLACK);
                g2d.draw(circle);
                g2d.drawString(city, point.x, point.y + 70);
            }
        }

        public void setOrigemSelecionada(String cidade) {
            origemSelecionada = cidade;
            repaint();
        }

        public void setDestinoSelecionado(String cidade){
            destinoSelecionado = cidade;
            repaint();
        }

        private void drawDistance(Graphics2D g2d, Point p1, Point p2, int distance) {
            int midX = (p1.x + p2.x) / 2;
            int midY = (p1.y + p2.y) / 2;
            g2d.drawString(distance + "KM", midX, midY);
        }

        public void restaurarCoresOriginais(){
            repaint();
        }
}
