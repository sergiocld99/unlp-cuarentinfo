package prog3.grafos.test;


import junit.framework.TestCase;
import prog3.grafos.*;
import prog3.grafos.util.*;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

import java.util.Arrays;


public class TestGrafoImplListAdy extends TestCase {
	GrafoImplListAdy<Integer> grafo;
	Grafo<String> mapaDirigido;
	Mapa mapa;

	protected void setUp() throws Exception {
		super.setUp();
		grafo = new GrafoImplListAdy<Integer>();

		mapaDirigido = new GrafoImplListAdy<>();
		Vertice<String> LP, G, H, B, Q, A, L, LZ, AB, FV, SFS, BUR, E, P;

		LP = new VerticeImplListAdy<>("La Plata");
		G = new VerticeImplListAdy<>("Gutiérrez");
		H = new VerticeImplListAdy<>("Hudson");
		B = new VerticeImplListAdy<>("Berazategui");
		Q = new VerticeImplListAdy<>("Quilmes");
		A = new VerticeImplListAdy<>("Avellaneda");
		L = new VerticeImplListAdy<>("Lanús");
		LZ = new VerticeImplListAdy<>("Lomas de Zamora");
		AB = new VerticeImplListAdy<>("Adrogué");
		FV = new VerticeImplListAdy<>("Florencio Varela");
		SFS = new VerticeImplListAdy<>("San Francisco Solano");
		BUR = new VerticeImplListAdy<>("Burzaco");
		E = new VerticeImplListAdy<>("Etcheverry");
		P = new VerticeImplListAdy<>("Pasco");

		mapaDirigido.agregarVertice(LP);
		mapaDirigido.agregarVertice(G);
		mapaDirigido.agregarVertice(H);
		mapaDirigido.agregarVertice(B);
		mapaDirigido.agregarVertice(Q);
		mapaDirigido.agregarVertice(A);
		mapaDirigido.agregarVertice(L);
		mapaDirigido.agregarVertice(LZ);
		mapaDirigido.agregarVertice(AB);
		mapaDirigido.agregarVertice(FV);
		mapaDirigido.agregarVertice(SFS);
		mapaDirigido.agregarVertice(BUR);
		mapaDirigido.agregarVertice(E);
		mapaDirigido.agregarVertice(P);

		mapaDirigido.conectar(LP, G, 24);
		//mapaCiudades.conectar(G, LP, 24);
		mapaDirigido.conectar(LP, H, 26);
		//mapaCiudades.conectar(H, LP, 26);
		mapaDirigido.conectar(G, H, 10);
		//mapaCiudades.conectar(H, G, 10);
		//mapaCiudades.conectar(B, H, 10);
		mapaDirigido.conectar(H, B, 10);
		mapaDirigido.conectar(B, Q, 7);
		//mapaCiudades.conectar(Q, B, 7);
		mapaDirigido.conectar(Q, A, 15);
		//mapaCiudades.conectar(A, Q, 15);
		mapaDirigido.conectar(A, L, 6);
		//mapaCiudades.conectar(L, A, 6);
		mapaDirigido.conectar(L, LZ, 8);
		//mapaCiudades.conectar(LZ, L, 8);
		//mapaCiudades.conectar(AB, LZ, 5);
		mapaDirigido.conectar(LZ, AB, 5);
		mapaDirigido.conectar(AB, BUR, 4);
		//mapaCiudades.conectar(BUR, AB, 4);
		mapaDirigido.conectar(BUR, SFS, 9);
		//mapaCiudades.conectar(SFS, BUR, 9);
		mapaDirigido.conectar(SFS, P, 6);
		//mapaCiudades.conectar(P, SFS, 6);
		mapaDirigido.conectar(P, LZ, 7);
		//mapaCiudades.conectar(LZ, P, 7);
		mapaDirigido.conectar(SFS, FV, 5);
		//mapaCiudades.conectar(FV, SFS, 5);
		//mapaCiudades.conectar(FV, B, 10);
		mapaDirigido.conectar(B, FV, 10);
		mapaDirigido.conectar(SFS, Q, 10);
		//mapaCiudades.conectar(Q, SFS, 10);
		mapaDirigido.conectar(FV, G, 14);
		//mapaCiudades.conectar(G, FV, 14);
		mapaDirigido.conectar(FV, E, 40);
		//mapaCiudades.conectar(E, FV, 40);
		mapaDirigido.conectar(E, LP, 22);
		//mapaCiudades.conectar(LP, E, 22);
		mapaDirigido.conectar(E, G, 28);
		//mapaCiudades.conectar(G, E, 28);

		mapa = new Mapa(mapaDirigido);
	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testAgregarYeliminarVertice() {
		int tamAntes = grafo.listaDeVertices().tamanio();
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		VerticeImplListAdy<Integer> vert3 = new VerticeImplListAdy<Integer>(3);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.agregarVertice(vert3);
		grafo.conectar(vert1, vert2);
		grafo.conectar(vert1, vert3);
		grafo.conectar(vert2, vert1);
		grafo.conectar(vert2, vert3);
		grafo.conectar(vert3, vert1);
		grafo.conectar(vert3, vert2);
		int tamDesp = grafo.listaDeVertices().tamanio();
		assertTrue(tamDesp == tamAntes + 3);
		grafo.eliminarVertice(vert1);
		grafo.eliminarVertice(vert2);
		grafo.eliminarVertice(vert3);
		int tamFin = grafo.listaDeVertices().tamanio();
		assertTrue(tamFin == tamAntes);
	}


	public void testConectarYdesconectar() {
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.conectar(vert1, vert2);
		boolean ok = false;

		ListaGenerica<Arista<Integer>> lista = grafo.listaDeAdyacentes(vert1);
		Arista<Integer> arista;
		lista.comenzar();
		while (!lista.fin()) {
			arista = lista.proximo();
			if (arista.verticeDestino() == vert2) {
				ok = true;
			}

		}

		assertTrue(ok);

		grafo.desConectar(vert1, vert2);
		ok = false;
		lista = grafo.listaDeAdyacentes(vert1);
		lista.comenzar();
		while (!lista.fin()) {
			arista = lista.proximo();
			if (arista.verticeDestino() == vert2) {
				ok = true;
			}

		}
		assertFalse(ok);
	}


	public void testEsAdyacente() {
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.conectar(vert1, vert2);
		assertTrue(grafo.esAdyacente(vert1, vert2));
		assertFalse(grafo.esAdyacente(vert2, vert1));
	}

	public void testEsVacio() {
		assertTrue(grafo.esVacio());
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertFalse(grafo.esVacio());
	}


	public void testListaDeVertices() {
		assertTrue(grafo.listaDeVertices().tamanio() == 0);
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertTrue(grafo.listaDeVertices().tamanio() == 1);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		grafo.agregarVertice(vert2);
		assertTrue(grafo.listaDeVertices().tamanio() == 2);
	}


	public void testPeso() {
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.conectar(vert1, vert2, 5);
		assertTrue(grafo.peso(vert1, vert2) == 5);
	}


	public void testListaDeAdyacentes() {
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertTrue(grafo.listaDeAdyacentes(vert1).tamanio() == 0);
		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(2);
		grafo.agregarVertice(vert2);
		assertTrue(grafo.listaDeAdyacentes(vert2).tamanio() == 0);
		grafo.conectar(vert1, vert2);
		assertTrue(grafo.listaDeAdyacentes(vert1).tamanio() == 1);
		assertTrue(grafo.listaDeAdyacentes(vert2).tamanio() == 0);
		grafo.conectar(vert2, vert1);
		assertTrue(grafo.listaDeAdyacentes(vert1).tamanio() == 1);
		assertTrue(grafo.listaDeAdyacentes(vert2).tamanio() == 1);
	}


	public void testVetice() {
		VerticeImplListAdy<Integer> vert1 = new VerticeImplListAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertEquals(grafo.vertice(vert1.posicion()), vert1);

		VerticeImplListAdy<Integer> vert2 = new VerticeImplListAdy<Integer>(1);
		grafo.agregarVertice(vert2);
		assertEquals(grafo.vertice(vert2.posicion()), vert2);
	}

	public void testRecorridos() {
		Vertice<Character> A, B, C, D, H, R, T;
		Grafo<Character> grafo2;
		Recorridos<Character> recorrido;
		ListaGenerica<Vertice<Character>> l1, l2, l3, l4;

		A = new VerticeImplListAdy<>('A');
		B = new VerticeImplListAdy<>('B');
		C = new VerticeImplListAdy<>('C');
		D = new VerticeImplListAdy<>('D');
		H = new VerticeImplListAdy<>('H');
		R = new VerticeImplListAdy<>('R');
		T = new VerticeImplListAdy<>('T');

		grafo2 = new GrafoImplListAdy<>();
		grafo2.agregarVertice(D);
		grafo2.agregarVertice(B);
		grafo2.agregarVertice(C);
		grafo2.conectar(D, C);
		grafo2.conectar(D, B);

		grafo2.agregarVertice(H);
		grafo2.agregarVertice(R);
		grafo2.conectar(B, H);
		grafo2.conectar(C, R);
		grafo2.conectar(R, H);
		grafo2.conectar(H, D);

		grafo2.agregarVertice(A);
		grafo2.agregarVertice(T);
		grafo2.conectar(H, T);
		grafo2.conectar(H, A);

		recorrido = new Recorridos<>();
		l1 = recorrido.dfs(grafo2);
		l2 = recorrido.bfs(grafo2);
		l3 = new ListaGenericaEnlazada<>();
		l4 = new ListaGenericaEnlazada<>();

		l3.agregarFinal(D);
		l3.agregarFinal(C);
		l3.agregarFinal(R);
		l3.agregarFinal(H);
		l3.agregarFinal(T);
		l3.agregarFinal(A);
		l3.agregarFinal(B);

		l4.agregarFinal(D);
		l4.agregarFinal(C);
		l4.agregarFinal(B);
		l4.agregarFinal(R);
		l4.agregarFinal(H);
		l4.agregarFinal(T);
		l4.agregarFinal(A);

		l1.comenzar();
		l2.comenzar();
		l3.comenzar();
		l4.comenzar();

		while (!l1.fin())
			assertEquals(l1.proximo().dato(), l3.proximo().dato());

		while (!l2.fin())
			assertEquals(l2.proximo().dato(), l4.proximo().dato());
	}

	public void testMapa1(){
		System.out.println("BUSCANDO UN CAMINO DE VARELA A LANÚS");
		System.out.println(mapa.devolverCamino("Florencio Varela", "Lanús"));

		System.out.println("\nBUSCANDO UN CAMINO DE LANÚS A ETCHEVERRY");
		System.out.println(mapa.devolverCamino("Lanús", "Etcheverry"));

		System.out.println("\nBUSCANDO UN CAMINO DE ETCHEVERRY A AVELLANEDA");
		System.out.println(mapa.devolverCamino("Etcheverry", "Avellaneda"));

		System.out.println("\nBUSCANDO UN CAMINO DE AVELLANEDA A LA PLATA");
		System.out.println(mapa.devolverCamino("Avellaneda", "La Plata"));

		System.out.println("\nBUSCANDO UN CAMINO DE LA PLATA A VARELA");
		System.out.println(mapa.devolverCamino("La Plata", "Florencio Varela"));
	}

	public void testMapa2(){
		System.out.println("AHORA NO SE PUEDE PASAR POR BERAZATEGUI, GUTIÉRREZ o VARELA");
		ListaGenerica<String> prohibidos = new ListaGenericaEnlazada<>();
		prohibidos.agregarFinal("Berazategui");
		prohibidos.agregarFinal("Florencio Varela");
		prohibidos.agregarFinal("Gutiérrez");

		System.out.println("\nBUSCANDO UN CAMINO DE HUDSON A PASCO");
		assertTrue(mapa.devolverCaminoExceptuando("Hudson", "Pasco", prohibidos).esVacia());
		System.out.println("(lista vacía)");

		System.out.println("\nBUSCANDO UN CAMINO DE QUILMES A BURZACO");
		System.out.println(mapa.devolverCaminoExceptuando("Quilmes", "Burzaco", prohibidos));

		System.out.println("\nBUSCANDO UN CAMINO DE ADROGUÉ A AVELLANEDA");
		System.out.println(mapa.devolverCaminoExceptuando("Adrogué", "Avellaneda", prohibidos));

		System.out.println("\nBUSCANDO UN CAMINO DE LANÚS A ETCHEVERRY");
		assertTrue(mapa.devolverCaminoExceptuando("Lanús", "Etcheverry", prohibidos).esVacia());
		System.out.println("(lista vacía)");
	}

	public void testMapa3(){
		System.out.println("MOSTRANDO LOS CAMINOS MÁS CORTOS");
		System.out.println("\nBUSCANDO UN CAMINO DE HUDSON A PASCO");
		System.out.println(mapa.caminoMasCorto("Hudson", "Pasco"));

		System.out.println("\nBUSCANDO UN CAMINO DE AVELLANEDA A ADROGUÉ");
		System.out.println(mapa.caminoMasCorto("Avellaneda", "Adrogué"));

		System.out.println("\nBUSCANDO UN CAMINO DE LOMAS A VARELA");
		System.out.println(mapa.caminoMasCorto("Lomas de Zamora", "Florencio Varela"));

		System.out.println("\nBUSCANDO UN CAMINO DE LOMAS A LA PLATA");
		System.out.println(mapa.caminoMasCorto("Lomas de Zamora", "La Plata"));
	}

	public void testMapa4(){
		System.out.println("MOSTRANDO CAMINOS ALCANZABLES SIN CARGAR COMBUSTIBLE");
		System.out.println("\nBUSCANDO UN CAMINO DE BERAZATEGUI A SOLANO -- CON 25 LITROS");
		System.out.println(mapa.caminoSinCargarCombustible("Berazategui", "San Francisco Solano", 25));

		System.out.println("\nBUSCANDO UN CAMINO DE SOLANO A BERAZATEGUI -- CON 14 LITROS");
		System.out.println(mapa.caminoSinCargarCombustible("San Francisco Solano", "Berazategui", 14));

		System.out.println("\nBUSCANDO UN CAMINO DE BERAZATEGUI A SOLANO -- CON 7 LITROS");
		System.out.println(mapa.caminoSinCargarCombustible("Berazategui", "San Francisco Solano", 7));

		System.out.println("\nBUSCANDO UN CAMINO DE LANÚS A GUTIERREZ -- CON 10 LITROS");
		System.out.println(mapa.caminoSinCargarCombustible("Lanús", "Gutiérrez", 10));
	}

	public void testMapa5(){
		System.out.println("MOSTRANDO CAMINOS ALCANZABLES CON MENOR CANTIDAD DE CARGAS");
		System.out.println("\nBUSCANDO UN CAMINO DE BERAZATEGUI A SOLANO -- CON 2 LITROS (CADA 14 KM)");
		System.out.println(mapa.caminoConMenosCargaDeCombustible("Berazategui", "San Francisco Solano", 2));

		System.out.println("\nBUSCANDO UN CAMINO DE SOLANO A BERAZATEGUI -- CON 2 LITROS (CADA 14 KM)");
		System.out.println(mapa.caminoConMenosCargaDeCombustible("San Francisco Solano", "Berazategui", 2));

		System.out.println("\nBUSCANDO UN CAMINO DE ETCHEVERRY A LA PLATA -- CON 4 LITROS (CADA 28 KM)");
		System.out.println(mapa.caminoConMenosCargaDeCombustible("Etcheverry", "La Plata", 4));

		System.out.println("\nBUSCANDO UN CAMINO DE LANÚS A GUTIERREZ -- CON 2 LITROS (CADA 14 KM)");
		System.out.println(mapa.caminoConMenosCargaDeCombustible("Lanús", "Gutiérrez", 2));
	}

	public void testAlgoritmos(){
		Algoritmos<String> algoritmos = new Algoritmos<>();

		assertTrue(algoritmos.subgrafoCuadrado(mapaDirigido));
		assertEquals(4, algoritmos.getGrado(mapaDirigido));
		assertTrue(algoritmos.tieneCiclo(mapaDirigido));
	}

	public void testSeparacion(){
		Grafo<String> redA = new GrafoImplListAdy<>();
		Vertice<String> jorge, ariel, juan, matias;

		jorge = new VerticeImplListAdy<>("Jorge");
		ariel = new VerticeImplListAdy<>("Ariel");
		juan = new VerticeImplListAdy<>("Juan");
		matias = new VerticeImplListAdy<>("Matias");

		redA.agregarVertice(jorge);
		redA.agregarVertice(ariel);
		redA.agregarVertice(juan);
		redA.agregarVertice(matias);

		redA.conectar(matias, ariel);
		redA.conectar(matias, juan);
		redA.conectar(matias, jorge);
		redA.conectar(ariel, matias);
		redA.conectar(ariel, juan);
		redA.conectar(juan, ariel);
		redA.conectar(juan, matias);
		redA.conectar(jorge, matias);

		GradosDeSeparacion gradosDeSeparacion = new GradosDeSeparacion();
		assertEquals(2, gradosDeSeparacion.maximoGradoDeSeparacion(redA));

		redA.desConectar(jorge, matias);
		redA.conectar(jorge, juan);
		redA.desConectar(ariel, juan);
		redA.desConectar(juan, ariel);
		redA.conectar(juan, jorge);
		redA.desConectar(juan, matias);
		redA.desConectar(matias, jorge);
		redA.desConectar(matias, juan);

		assertEquals(0, gradosDeSeparacion.maximoGradoDeSeparacion(redA));
	}

	public void testFloyd(){
		Grafo<Integer> ej4 = new GrafoImplListAdy<>();
		Vertice<Integer> v0, v1, v2, v3, v4;

		v0 = new VerticeImplListAdy<>(0);
		v1 = new VerticeImplListAdy<>(1);
		v2 = new VerticeImplListAdy<>(2);
		v3 = new VerticeImplListAdy<>(3);
		v4 = new VerticeImplListAdy<>(4);

		ej4.agregarVertice(v0);
		ej4.agregarVertice(v1);
		ej4.agregarVertice(v2);
		ej4.agregarVertice(v3);
		ej4.agregarVertice(v4);

		ej4.conectar(v1, v0, 3);
		ej4.conectar(v3, v0, 1);
		ej4.conectar(v4, v0, 6);
		ej4.conectar(v0, v2, 1);
		ej4.conectar(v1, v3, 4);
		ej4.conectar(v4, v1, 2);
		ej4.conectar(v1, v2, 2);
		ej4.conectar(v2, v3, 7);
		ej4.conectar(v2, v4, 4);
		ej4.conectar(v4, v3, 2);
		ej4.conectar(v3, v4, 3);

		// TODO: el grafo está construido correctamente (chequeado)

		Costo[][] costos = new Floyd<Integer>().floyd(ej4);
		for (int i=0; i < costos.length; i++){
			for (int j=0; j < costos.length; j++){
				System.out.println("Costo("+i+"->"+j+"): "+costos[i][j]);
			}
		}
	}

	public void testAntenas(){
		int[][] M = new int[7][7];
		M[0] = new int[]{0, 7, 2, 6, 9, -1, 8};
		M[1] = new int[]{7, 0, -1, 3, -1, -1, -1};
		M[2] = new int[]{2, -1, 0, -1, 6, -1, -1};
		M[3] = new int[]{6, 3, -1, 0, -1, -1, 3};
		M[4] = new int[]{9, -1, 6, -1, 0, 3, -1};
		M[5] = new int[]{-1, -1, -1, -1, 3, 0, 2};
		M[6] = new int[]{8, -1, -1, 3, -1, 2, 0};

		ListaGenerica<ListaGenerica<Integer>> caminosMin = new Antenas().caminosMinimos(M);
		caminosMin.comenzar();
		while (!caminosMin.fin()){
			System.out.println(caminosMin.proximo());
		}
	}
}