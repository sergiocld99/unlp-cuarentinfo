package prog3.grafos.test;


import junit.framework.TestCase;
import prog3.grafos.*;
import prog3.grafos.util.Algoritmos;
import prog3.grafos.util.Mapa;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;


public class TestGrafoImplMatrizAdy extends TestCase {
	GrafoImplMatrizAdy<Integer> grafo;
	Grafo<String> mapaCiudades;
	Mapa mapa;

	protected void setUp() throws Exception {
		super.setUp();
		grafo = new GrafoImplMatrizAdy<Integer>(10);

		mapaCiudades = new GrafoImplMatrizAdy<>(15);
		Vertice<String> LP, G, H, B, Q, A, L, LZ, AB, FV, SFS, BUR, E, P;

		LP = new VerticeImplMatrizAdy<>("La Plata");
		G = new VerticeImplMatrizAdy<>("Gutiérrez");
		H = new VerticeImplMatrizAdy<>("Hudson");
		B = new VerticeImplMatrizAdy<>("Berazategui");
		Q = new VerticeImplMatrizAdy<>("Quilmes");
		A = new VerticeImplMatrizAdy<>("Avellaneda");
		L = new VerticeImplMatrizAdy<>("Lanús");
		LZ = new VerticeImplMatrizAdy<>("Lomas de Zamora");
		AB = new VerticeImplMatrizAdy<>("Adrogué");
		FV = new VerticeImplMatrizAdy<>("Florencio Varela");
		SFS = new VerticeImplMatrizAdy<>("San Francisco Solano");
		BUR = new VerticeImplMatrizAdy<>("Burzaco");
		E = new VerticeImplMatrizAdy<>("Etcheverry");
		P = new VerticeImplMatrizAdy<>("Pasco");

		mapaCiudades.agregarVertice(LP);
		mapaCiudades.agregarVertice(G);
		mapaCiudades.agregarVertice(H);
		mapaCiudades.agregarVertice(B);
		mapaCiudades.agregarVertice(Q);
		mapaCiudades.agregarVertice(A);
		mapaCiudades.agregarVertice(L);
		mapaCiudades.agregarVertice(LZ);
		mapaCiudades.agregarVertice(AB);
		mapaCiudades.agregarVertice(FV);
		mapaCiudades.agregarVertice(SFS);
		mapaCiudades.agregarVertice(BUR);
		mapaCiudades.agregarVertice(E);
		mapaCiudades.agregarVertice(P);

		mapaCiudades.conectar(LP, G, 24);
		mapaCiudades.conectar(G, LP, 24);
		mapaCiudades.conectar(LP, H, 26);
		mapaCiudades.conectar(H, LP, 26);
		mapaCiudades.conectar(G, H, 10);
		mapaCiudades.conectar(H, G, 10);
		mapaCiudades.conectar(B, H, 10);
		mapaCiudades.conectar(H, B, 10);
		mapaCiudades.conectar(B, Q, 7);
		mapaCiudades.conectar(Q, B, 7);
		mapaCiudades.conectar(Q, A, 15);
		mapaCiudades.conectar(A, Q, 15);
		mapaCiudades.conectar(A, L, 6);
		mapaCiudades.conectar(L, A, 6);
		mapaCiudades.conectar(L, LZ, 8);
		mapaCiudades.conectar(LZ, L, 8);
		mapaCiudades.conectar(AB, LZ, 5);
		mapaCiudades.conectar(LZ, AB, 5);
		mapaCiudades.conectar(AB, BUR, 4);
		mapaCiudades.conectar(BUR, AB, 4);
		mapaCiudades.conectar(BUR, SFS, 9);
		mapaCiudades.conectar(SFS, BUR, 9);
		mapaCiudades.conectar(SFS, P, 6);
		mapaCiudades.conectar(P, SFS, 6);
		mapaCiudades.conectar(P, LZ, 7);
		mapaCiudades.conectar(LZ, P, 7);
		mapaCiudades.conectar(SFS, FV, 5);
		mapaCiudades.conectar(FV, SFS, 5);
		mapaCiudades.conectar(FV, B, 10);
		mapaCiudades.conectar(B, FV, 10);
		mapaCiudades.conectar(SFS, Q, 10);
		mapaCiudades.conectar(Q, SFS, 10);
		mapaCiudades.conectar(FV, G, 14);
		mapaCiudades.conectar(G, FV, 14);
		mapaCiudades.conectar(FV, E, 40);
		mapaCiudades.conectar(E, FV, 40);
		mapaCiudades.conectar(E, LP, 22);
		mapaCiudades.conectar(LP, E, 22);
		mapaCiudades.conectar(E, G, 28);
		mapaCiudades.conectar(G, E, 28);

		mapa = new Mapa(mapaCiudades);
	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void testAgregarYeliminarVertice() {
		int tamAntes = grafo.listaDeVertices().tamanio();
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
		VerticeImplMatrizAdy<Integer> vert3 = new VerticeImplMatrizAdy<Integer>(3);
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
		assertTrue(tamDesp == tamAntes+3);
		
		grafo.eliminarVertice(vert1);
		grafo.eliminarVertice(vert2);
		grafo.eliminarVertice(vert3);
		int tamFin = grafo.listaDeVertices().tamanio();
		assertTrue(tamFin == tamAntes);
	}


	public void testConectarYdesconectar() {
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		
		grafo.conectar(vert1, vert2);
		boolean ok = false;
		ListaGenerica<Arista<Integer>> lista = grafo.listaDeAdyacentes(vert1);
		AristaImpl<Integer> arista;
		lista.comenzar();
		while(!lista.fin()){			
			arista = (AristaImpl<Integer>) lista.proximo();
			if (arista.verticeDestino()==vert2){				
				ok= true;
			}
			
		}
		assertTrue(ok);
		
		grafo.desConectar(vert1, vert2);
		ok=false;
		lista = grafo.listaDeAdyacentes(vert1);
		lista.comenzar();
		while(!lista.fin()){
			arista = (AristaImpl<Integer>)lista.proximo();
			if (arista.verticeDestino() == vert2){
				ok= true;
			}
			
		}
		assertFalse(ok);
	}


	public void testEsAdyacente() {
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.conectar(vert1, vert2);
		assertTrue(grafo.esAdyacente(vert1,vert2));
		assertFalse(grafo.esAdyacente(vert2,vert1));
	}


	public void testEsVacio() {
		assertTrue(grafo.esVacio());
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertFalse(grafo.esVacio());
	}

	public void testListaDeVertices() {
		assertTrue(grafo.listaDeVertices().tamanio()==0);
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertTrue(grafo.listaDeVertices().tamanio()==1);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
		grafo.agregarVertice(vert2);
		assertTrue(grafo.listaDeVertices().tamanio()==2);
	}


	public void testPeso() {
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
		grafo.agregarVertice(vert1);
		grafo.agregarVertice(vert2);
		grafo.conectar(vert1, vert2,5);
		assertTrue(grafo.peso(vert1, vert2) == 5);
	}


	public void testListaDeAdyacentes() {
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertTrue(grafo.listaDeAdyacentes(vert1).tamanio() == 0);
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(2);
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
		VerticeImplMatrizAdy<Integer> vert1 = new VerticeImplMatrizAdy<Integer>(1);
		grafo.agregarVertice(vert1);
		assertEquals(grafo.vertice(vert1.posicion()),vert1);
		
		VerticeImplMatrizAdy<Integer> vert2 = new VerticeImplMatrizAdy<Integer>(1);
		grafo.agregarVertice(vert2);
		assertEquals(grafo.vertice(vert2.posicion()),vert2);
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

		assertTrue(algoritmos.subgrafoCuadrado(mapaCiudades));
	}
}

