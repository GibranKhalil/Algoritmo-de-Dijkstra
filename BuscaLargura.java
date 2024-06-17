import java.util.*;

public class BuscaLargura {

	private int numVertices;
	private LinkedList<Integer>[] listaAdjacencia;

	public BuscaLargura(int numVertices) {

		this.numVertices = numVertices;
		listaAdjacencia = new LinkedList[numVertices];
		for (int i = 0; i < numVertices; i++) {
			listaAdjacencia[i] = new LinkedList<>();
		}
	}

	public void adicionarArestas(int verticeOrigem, int verticeDestino) {
		listaAdjacencia[verticeOrigem].add(verticeDestino);
	}

	public LinkedHashSet<Integer> buscaLarguraGrafo(int verticeInicial) {

		LinkedHashSet<Integer> verticesVisitado = new LinkedHashSet<>();
		Queue<Integer> filaPercurso = new LinkedList<>();

		verticesVisitado.add(verticeInicial);
		filaPercurso.add(verticeInicial);

		while (!filaPercurso.isEmpty()) {

			int verticeAtual = filaPercurso.poll();
			verticesVisitado.add(verticeInicial);

			for (int verticeAdjacente : listaAdjacencia[verticeAtual]) {
				if (!verticesVisitado.contains(verticeAdjacente)) {
					verticesVisitado.add(verticeAdjacente);
					filaPercurso.add(verticeAdjacente);
				}
			}
		}
		return verticesVisitado;
	}

	public static void main(String[] args) {
		BuscaLargura graph = new BuscaLargura(9);

		graph.adicionarArestas(0, 2);
		graph.adicionarArestas(0, 1);
		graph.adicionarArestas(1, 3);
		graph.adicionarArestas(2, 3);
		graph.adicionarArestas(2, 5);
		graph.adicionarArestas(3, 4);
		graph.adicionarArestas(3, 6);
		graph.adicionarArestas(7, 3);
		graph.adicionarArestas(8, 7);

		System.out.println("Busca em Largura:");

		for (Integer vertice : graph.buscaLarguraGrafo(0)) {
			System.out.print("|");
			System.out.print(vertice);
		}
	}
}
