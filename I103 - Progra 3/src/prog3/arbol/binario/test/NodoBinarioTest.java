package prog3.arbol.binario.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import prog3.arbol.binario.NodoBinario;



public class NodoBinarioTest {
	
	NodoBinario<Integer> nodo1;
	NodoBinario<Integer> nodo2;
	NodoBinario<Integer> nodo3;
	
	
	@Before
	public void setUp() throws Exception {
		nodo1 = new NodoBinario<Integer>(1);
	    nodo2 = new NodoBinario<Integer>(2);
		nodo3 = new NodoBinario<Integer>(3);
		
	}
	
	@Test
	public void testGetDato(){
		assertEquals(new Integer(1),nodo1.getDato());
		assertEquals(new Integer(2),nodo2.getDato());
		assertEquals(new Integer(3),nodo3.getDato());		
	}
	
	@Test
	public void testSetDato(){
		nodo1.setDato(4);
		nodo2.setDato(5);
		nodo3.setDato(6);
		assertEquals(new Integer(4),nodo1.getDato());
		assertEquals(new Integer(5),nodo2.getDato());
		assertEquals(new Integer(6),nodo3.getDato());		
	}
	
	@Test
	public void testSetHijoIzquierdo(){
		nodo1.setHijoIzquierdo(nodo2);				
		assertEquals(null,nodo3.getHijoIzquierdo());
		assertEquals(nodo2,nodo1.getHijoIzquierdo());		
	}
	
	@Test
	public void testSetHijoDerecho(){
		nodo1.setHijoDerecho(nodo2);				
		assertEquals(null,nodo3.getHijoDerecho());
		assertEquals(nodo2,nodo1.getHijoDerecho());
	}
	
	
	
	

}
