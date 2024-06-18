import GUI.MapControl;
import Models.Grafo;

import java.text.Normalizer;
import java.util.Arrays;

public class Dijkstra {
	public static void main(String[] args) {
		MapControl mapControl = new MapControl();
		Grafo grafo = new Grafo(13);
		inicializarGrafo(grafo);


		int[] distancias = grafo.dijkstra("Ribeirão Preto");
		for (int i = 0; i < 13; i++) {
			System.out.println("Menor caminho de Bauru para " + grafo.getDadosVertices()[i] + ": " + distancias[i]);
		}

	}

	private static void inicializarGrafo(Grafo grafo) {
		String[] cidades = MapControl.getCidades();
		System.out.println(Arrays.toString(cidades));
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
		return textoNormalizado;
	}

//	{
//		"0": "Araraquara",
//			"1": "Piracicaba",
//			"2": "Presidente Prudente",
//			"3": "Marília",
//			"4": "São José do Rio Preto",
//			"5": "Santos",
//			"6": "Araçatuba",
//			"7": "Bauru",
//			"8": "São Paulo",
//			"9": "Ribeirão Preto",
//			"10": "Sorocaba",
//			"11": "Campinas",
//			"12": "São José dos Campos"
//	}
}
