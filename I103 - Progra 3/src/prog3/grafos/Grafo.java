package prog3.grafos;

import prog3.listagenerica.ListaGenerica;


public interface Grafo<T> {
	public void  agregarVertice(Vertice<T> v);
	public void eliminarVertice(Vertice<T> v) ;
	public void conectar(Vertice<T> origen, Vertice<T> destino) ;
	public void conectar(Vertice<T> origen, Vertice<T> destino, int peso);
	public void desConectar(Vertice<T> origen, Vertice<T> destino);
	public boolean esAdyacente(Vertice<T> origen, Vertice<T> destino);
	public boolean esVacio();
	public ListaGenerica<Vertice<T>> listaDeVertices();
	public int peso(Vertice<T> origen, Vertice<T> destino);
	public ListaGenerica<Arista<T>> listaDeAdyacentes(Vertice<T> v);
	public Vertice<T> vertice(int posicion); 





}
