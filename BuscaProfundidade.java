import java.util.*;

class BuscaProfundidade {
	
	private int numVertices;
	private LinkedList<Integer>[] listaAdjacencia;

	public BuscaProfundidade(int numVertices) {
		
		this.numVertices = numVertices;
		listaAdjacencia = new LinkedList[numVertices];
		for (int i = 0; i < numVertices; i++) {
			listaAdjacencia[i] = new LinkedList<>();
		}
	}

	public void adicionarArestas(int verticeOrigem, int verticeDestino) {
	
		listaAdjacencia[verticeOrigem].add(verticeDestino);
	}

	
	public LinkedHashSet<Integer> buscaProfundidadeGrafo(int verticeInicial) {
		
		LinkedHashSet<Integer> verticesVisitado = new LinkedHashSet<>();
		Stack<Integer> pilhaPercurso = new Stack<>();

		pilhaPercurso.push(verticeInicial);
		verticesVisitado.add(verticeInicial);

		while (!pilhaPercurso.isEmpty()) {
			int verticeAtual = pilhaPercurso.pop();
			verticesVisitado.add(verticeAtual);

			for (int verticeAdjacente : listaAdjacencia[verticeAtual]) {
				if (!verticesVisitado.contains(verticeAdjacente)) {
					pilhaPercurso.push(verticeAdjacente);
					verticesVisitado.add(verticeAdjacente);
				}
			}
		}
		return verticesVisitado;
	}

	public static void main(String[] args) {
		BuscaProfundidade graph = new BuscaProfundidade(9);

		graph.adicionarArestas(0, 2);
		graph.adicionarArestas(0, 1);
		graph.adicionarArestas(1, 3);
		graph.adicionarArestas(2, 3);
		graph.adicionarArestas(2, 5);
		graph.adicionarArestas(3, 4);
		graph.adicionarArestas(3, 6);
		graph.adicionarArestas(7, 3);
		graph.adicionarArestas(8, 7);
		

		System.out.println("Busca em Profundidade:");
		
		for (Integer vertice : graph.buscaProfundidadeGrafo(0)) {
			System.out.print("|");
			System.out.print(vertice);
		}

	}
}
