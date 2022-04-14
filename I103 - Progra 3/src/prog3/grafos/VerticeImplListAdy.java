package prog3.grafos;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class VerticeImplListAdy<T> implements Vertice<T> {
    

	private ListaGenerica<Arista<T>> adyacentes; 
    
	private T dato;
    
	private int posicion;
	
    public VerticeImplListAdy(T d){
		dato = d;
		adyacentes = new ListaGenericaEnlazada<Arista<T>>();
		
	}
    @Override
	public T dato() {
		return dato;
	}

	@Override
	public void setDato(T unDato) {
		dato = unDato;
		
	}

	@Override
	public int posicion() {
		return posicion;
	}
	
	public void conectar(Vertice<T> v){
		conectar(v, 1);

		
	}
	public void conectar(Vertice<T> v, int peso){
		Arista<T> a = new AristaImpl<T>(v, peso);
		if (!adyacentes.incluye(a)) adyacentes.agregarFinal(a);
		
	}
		

	
	public void desconectar(Vertice<T> v){
		this.obtenerAdyacentes().eliminar(this.obtenerArista(v));
		
	}
	
	public ListaGenerica<Arista<T>> obtenerAdyacentes(){
		return adyacentes;
	}
	public boolean esAdyacente(Vertice<T> v){
		this.obtenerAdyacentes().comenzar();
		while (!this.adyacentes.fin()){
			if (this.adyacentes.proximo().verticeDestino().equals(v)){
				return true;
			}
		}		
		return false;
		
	}
	public int peso(Vertice<T> v){
		return this.obtenerArista(v).peso();
	}
	
	public Arista<T> obtenerArista(Vertice<T> v){
		ListaGenerica<Arista<T>> adyAux = this.obtenerAdyacentes();
		adyAux.comenzar();
		while (!adyAux.fin()){
			AristaImpl<T> arista = (AristaImpl<T>) adyAux.proximo();
			if (arista.verticeDestino().equals(v))
				return arista;
		}	
		return null;
	}
	public void setPosicion(int pos){
		posicion = pos; 
	}

}
