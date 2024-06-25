package Models;

import java.text.Normalizer;
import java.util.Arrays;

public class Grafo {
        private int[][] matrizAdjacencia;

        private String[] dadosVertices;
        private int tamanho;

        public String[] getDadosVertices() {
            return dadosVertices;
        }

        public Grafo(int tamanho) {
            this.tamanho = tamanho;
            this.matrizAdjacencia = new int[tamanho][tamanho];
            this.dadosVertices = new String[tamanho];
        }

        public void adicionarAresta(int origem, int destino, int peso) {
            if (verticeValido(origem) && verticeValido(destino)) {
                matrizAdjacencia[origem][destino] = peso;
                matrizAdjacencia[destino][origem] = peso;
            }
        }

        public void adicionarDadosVertice(int vertice, String dados) {
            if (verticeValido(vertice)) {
                dadosVertices[vertice] = dados;
            }
        }

    public static String removerAcentos(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");
        return textoNormalizado;
    }

        public int[] dijkstra(String dadosVerticeInicial) {
            String novoDado =  removerAcentos(dadosVerticeInicial.replace(" ", "").toLowerCase());
            int verticeInicial = encontrarIndice(novoDado);
            int[] distancias = new int[tamanho];
            boolean[] visitados = new boolean[tamanho];

            inicializarDistancias(distancias, verticeInicial);

            for (int i = 0; i < tamanho; i++) {
                int verticeAtual = encontrarMenorDistancia(distancias, visitados);
                if (verticeAtual == -1)
                    break;

                visitados[verticeAtual] = true;
                atualizarDistancias(verticeAtual, distancias, visitados);
            }
            return distancias;
        }


        private boolean verticeValido(int vertice) {
            return vertice >= 0 && vertice < tamanho;
        }

        private void inicializarDistancias(int[] distancias, int verticeInicial) {
            for (int i = 0; i < tamanho; i++) {
                distancias[i] = Integer.MAX_VALUE;
            }
            distancias[verticeInicial] = 0;
        }

        public int encontrarIndice(String dados) {
            for (int i = 0; i < tamanho; i++) {
                if (dadosVertices[i].equals(dados)) {
                    return i;
                }
            }
            return -1;
        }

        private int encontrarMenorDistancia(int[] distancias, boolean[] visitados) {
            int menorDistancia = Integer.MAX_VALUE;
            int indiceMenorDistancia = -1;

            for (int i = 0; i < tamanho; i++) { // distancias[0] = 3
                if (!visitados[i] && distancias[i] <= menorDistancia) {
                    menorDistancia = distancias[i];
                    indiceMenorDistancia = i;
                }
            }
            return indiceMenorDistancia;
        }

        private void atualizarDistancias(int verticeAtual, int[] distancias, boolean[] visitados) {
            for (int i = 0; i < tamanho; i++) {
                if (!visitados[i] && matrizAdjacencia[verticeAtual][i] != 0
                        && distancias[verticeAtual] != Integer.MAX_VALUE) {
                    int novaDistancia = distancias[verticeAtual] + matrizAdjacencia[verticeAtual][i];
                    if (novaDistancia < distancias[i]) {
                        distancias[i] = novaDistancia;
                    }
                }
            }
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

    private String construirCaminho(int verticeFinal, int[] predecessores, int[] distancias) {
        StringBuilder caminho = new StringBuilder();
        for (int vertice = verticeFinal; vertice != -1; vertice = predecessores[vertice]) {
            if (caminho.length() > 0) {
                caminho.insert(0, "->");
            }
            caminho.insert(0, dadosVertices[vertice]);
        }
        return caminho.toString();
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
    }

