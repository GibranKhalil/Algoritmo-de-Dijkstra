public class DijkstraPath {

    static class Grafo {
        private int[][] matrizAdjacencia;
        private String[] dadosVertices;
        private int tamanho;

        public Grafo(int tamanho) {
            this.tamanho = tamanho;
            this.matrizAdjacencia = new int[tamanho][tamanho];
            this.dadosVertices = new String[tamanho];
        }

        public void adicionarAresta(int origem, int destino, int peso) {
            if (isVerticeValido(origem) && isVerticeValido(destino)) {
                matrizAdjacencia[origem][destino] = peso;
                matrizAdjacencia[destino][origem] = peso;  
            }
        }

        public void adicionarDadosVertice(int vertice, String dados) {
            if (isVerticeValido(vertice)) {
                dadosVertices[vertice] = dados;
            }
        }

        public String obterCaminhoMaisCurto(int verticeInicial, int verticeFinal) {
            int[] distancias = new int[tamanho];
            int[] predecessores = new int[tamanho];
            boolean[] visitados = new boolean[tamanho];

            inicializarDistanciasPredecessores(distancias, predecessores, verticeInicial);

            for (int i = 0; i < tamanho; i++) {
                int verticeMaisProximo = obterVerticeMaisProximo(distancias, visitados);
                if (verticeMaisProximo == -1) break;
                visitados[verticeMaisProximo] = true;
                atualizarDistancias(verticeMaisProximo, distancias, predecessores, visitados);
            }

            return construirCaminho(verticeFinal, predecessores, distancias);
        }

        private boolean isVerticeValido(int vertice) {
            return 0 <= vertice && vertice < tamanho;
        }

        private void inicializarDistanciasPredecessores(int[] distancias, int[] predecessores, int verticeInicial) {
            for (int i = 0; i < tamanho; i++) {
                distancias[i] = Integer.MAX_VALUE;
                predecessores[i] = -1;
            }
            distancias[verticeInicial] = 0;
        }

        private int obterVerticeMaisProximo(int[] distancias, boolean[] visitados) {
            int verticeMaisProximo = -1;
            for (int i = 0; i < tamanho; i++) {
                if (!visitados[i] && (verticeMaisProximo == -1 || distancias[i] < distancias[verticeMaisProximo])) {
                    verticeMaisProximo = i;
                }
            }
            return verticeMaisProximo;
        }

        private void atualizarDistancias(int vertice, int[] distancias, int[] predecessores, boolean[] visitados) {
            for (int i = 0; i < tamanho; i++) {
                if (matrizAdjacencia[vertice][i] != 0 && !visitados[i]) {
                    int novaDistancia = distancias[vertice] + matrizAdjacencia[vertice][i];
                    if (novaDistancia < distancias[i]) {
                        distancias[i] = novaDistancia;
                        predecessores[i] = vertice;
                    }
                }
            }
        }

        private String construirCaminho(int verticeFinal, int[] predecessores, int[] distancias) {
            StringBuilder caminho = new StringBuilder();
            for (int vertice = verticeFinal; vertice != -1; vertice = predecessores[vertice]) {
                if (caminho.length() > 0) {
                    caminho.insert(0, "->");
                }
                caminho.insert(0, dadosVertices[vertice]);
            }
            return caminho.toString() + ", Distância: " + distancias[verticeFinal];
        }
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(7);
        inicializarGrafo(grafo);
        
        System.out.println("Dijkstra - iniciando do vértice D: \n");
        for (int i = 0; i < grafo.tamanho; i++) {
            System.out.println(grafo.obterCaminhoMaisCurto(3, i));
        }
    }

    private static void inicializarGrafo(Grafo grafo) {
        grafo.adicionarDadosVertice(0, "A");
        grafo.adicionarDadosVertice(1, "B");
        grafo.adicionarDadosVertice(2, "C");
        grafo.adicionarDadosVertice(3, "D");
        grafo.adicionarDadosVertice(4, "E");
        grafo.adicionarDadosVertice(5, "F");
        grafo.adicionarDadosVertice(6, "G");

        grafo.adicionarAresta(3, 0, 4); // D - A, peso 4
        grafo.adicionarAresta(3, 4, 2); // D - E, peso 2
        grafo.adicionarAresta(0, 2, 3); // A - C, peso 3
        grafo.adicionarAresta(0, 4, 4); // A - E, peso 4
        grafo.adicionarAresta(4, 2, 4); // E - C, peso 4
        grafo.adicionarAresta(4, 6, 5); // E - G, peso 5
        grafo.adicionarAresta(2, 5, 5); // C - F, peso 5
        grafo.adicionarAresta(2, 1, 2); // C - B, peso 2
        grafo.adicionarAresta(1, 5, 2); // B - F, peso 2
        grafo.adicionarAresta(6, 5, 5); // G - F, peso 5
    }
}
