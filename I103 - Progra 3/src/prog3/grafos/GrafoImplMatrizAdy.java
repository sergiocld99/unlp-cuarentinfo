package prog3.grafos;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class GrafoImplMatrizAdy<T> implements Grafo<T> {
	private int maxVertices;
	private ListaGenerica<Vertice<T>> vertices;
	private int [][] matrizAdy;
	
	public GrafoImplMatrizAdy(int maxVert){
		this.maxVertices = maxVert;
		this.matrizAdy = new int [maxVert][maxVert];
		this.vertices = new ListaGenericaEnlazada<Vertice<T>>();
		
	}
	public void agregarVertice(Vertice<T> v) {
		VerticeImplMatrizAdy<T> v2 = ((VerticeImplMatrizAdy<T>) v);
		v2.setPosicion(vertices.tamanio());
		vertices.agregarFinal(v2);
	}
	
	@Override
	public void eliminarVertice(Vertice<T> v) {
		ListaGenerica<Vertice<T>> verticesAux = this.listaDeVertices();
		verticesAux.comenzar();
		while (!verticesAux.fin()){
			VerticeImplMatrizAdy<T> vert = (VerticeImplMatrizAdy<T>) verticesAux.proximo();
			this.desConectar(vert, v);
		}
		this.listaDeVertices().eliminar(v);
	}
	
	public void conectar(Vertice<T> origen, Vertice<T> destino) {
		conectar(origen,destino,1);
	}
	
	public void conectar(Vertice<T> origen, Vertice<T> destino, int peso) {
		 matrizAdy[origen.posicion()][destino.posicion()] = peso;
	}
	
	public void desConectar(Vertice<T> origen, Vertice<T> destino) {
		if (vertices.incluye(origen) && vertices.incluye(destino)){
		    matrizAdy[origen.posicion()][destino.posicion()] = 0;
		    }
		}
	public boolean esAdyacente(Vertice<T> origen, Vertice<T> destino) {
		if (vertices.incluye(origen) && vertices.incluye(destino)){
			return (!(matrizAdy[origen.posicion()][destino.posicion()]==0));
		}
		return false;
	}
	public boolean esVacio() {
		return vertices.esVacia();
	}
	public ListaGenerica<Vertice<T>> listaDeVertices() {
		return vertices;
	}
	
	public int peso(Vertice<T> origen, Vertice<T> destino) {
		if (vertices.incluye(origen) && vertices.incluye(destino)){
			return matrizAdy[origen.posicion()][destino.posicion()];
		}
		return 0;
	}
	
	public ListaGenerica<Arista<T>> listaDeAdyacentes(Vertice<T> v) {
		ListaGenerica<Arista<T>> aristas = new ListaGenericaEnlazada<Arista<T>>();
		for (int i=0; i<this.listaDeVertices().tamanio(); i++){
			if (matrizAdy[v.posicion()][i] != 0){
				AristaImpl<T> arista = new AristaImpl<T>(this.vertices.elemento(i), matrizAdy[v.posicion()][i]);
				aristas.agregarFinal(arista);
			}
		}
				
		return aristas;
	}
	
	public Vertice<T> vertice(int posicion) {
		return vertices.elemento(posicion);
	} 


}
