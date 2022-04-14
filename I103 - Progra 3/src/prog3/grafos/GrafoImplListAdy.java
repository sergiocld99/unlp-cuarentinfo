package prog3.grafos;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class GrafoImplListAdy<T> implements Grafo<T> {
    private ListaGenerica<Vertice<T>> vertices = new ListaGenericaEnlazada<Vertice<T>>();
	
	
	
	@Override
	public void agregarVertice(Vertice<T> v) {
		VerticeImplListAdy<T> v2 = ((VerticeImplListAdy<T>) v);
		v2.setPosicion(vertices.tamanio());
		vertices.agregarFinal(v2);
	}

	@Override
	public void eliminarVertice(Vertice<T> v) {
		ListaGenerica<Vertice<T>> verticesAux = this.listaDeVertices();
		verticesAux.comenzar();
		while (!verticesAux.fin()){
			VerticeImplListAdy<T> vert = (VerticeImplListAdy<T>) verticesAux.proximo();
			if (this.esAdyacente(vert,v)){
				this.desConectar(vert, v);
			}
		}
		this.listaDeVertices().eliminar(v);
	}

	@Override
	public void conectar(Vertice<T> origen, Vertice<T> destino) {
		((VerticeImplListAdy<T>) origen).conectar(destino);

		
	}

	@Override
	public void conectar(Vertice<T> origen, Vertice<T> destino, int peso) {
		((VerticeImplListAdy<T>) origen).conectar(destino,peso);
		
	}

	@Override
	public void desConectar(Vertice<T> origen, Vertice<T> destino) {
		((VerticeImplListAdy<T>) origen).desconectar(destino);
		
	}

	@Override
	public boolean esAdyacente(Vertice<T> origen, Vertice<T> destino) {
		if (vertices.incluye(origen) && vertices.incluye(destino)){		
		return ((VerticeImplListAdy<T>) origen).esAdyacente(destino);
	
		}
	return false;
	}
		

	@Override
	public boolean esVacio() {
		
		return vertices.esVacia();
	}

	@Override
	public ListaGenerica<Vertice<T>> listaDeVertices() {
		
		return vertices;
	}

	@Override
	public int peso(Vertice<T> origen, Vertice<T> destino) {
	    return	((VerticeImplListAdy<T>) origen).peso(destino);
	}

	@Override
	public ListaGenerica<Arista<T>> listaDeAdyacentes(Vertice<T> v) {
		return ((VerticeImplListAdy<T>)v).obtenerAdyacentes();
	}

	@Override
	public Vertice<T> vertice(int posicion) {
		return this.listaDeVertices().elemento(posicion);
	}
	

}
