public class Dijkstra {

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

		public int[] dijkstra(String dadosVerticeInicial) {
			int verticeInicial = encontrarIndice(dadosVerticeInicial);
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

		private int encontrarIndice(String dados) {
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

			for (int i = 0; i < tamanho; i++) {
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
	}

	public static void main(String[] args) {
		Grafo grafo = new Grafo(7);

		inicializarGrafo(grafo);

		System.out.println("Dijkstra - iniciando do vértice D: \n");
		int[] distancias = grafo.dijkstra("D");
		for (int i = 0; i < distancias.length; i++) {
			System.out.println("Menor caminho de D para " + grafo.dadosVertices[i] + ": " + distancias[i]);
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
