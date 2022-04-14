package prog3.grafos;

public class VerticeImplMatrizAdy<T> implements Vertice<T> {
    private T dato;
    private int posicion;
    
    public VerticeImplMatrizAdy(T d){
    	dato = d;
    	
    }
    
	@Override
	public T dato() {
		return dato;
	}

	

	@Override
	public int posicion() {
		return posicion;
	}

	

	@Override
	public void setDato(T unDato) {
		dato = unDato;
		
	}
	
	public void setPosicion(int posicion) {
		this.posicion = posicion;
		
	}

	
	

}
