package prog3.arbol.general.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import prog3.arbol.general.NodoGeneral;

public class NodoGeneralTest {

	NodoGeneral<Integer> nodo1;
	NodoGeneral<Integer> nodo2;
	NodoGeneral<Integer> nodo3;
	NodoGeneral<Integer> nodo4;
	
	@Before
	public void setUp() throws Exception {
		nodo1 = new NodoGeneral <Integer>(1);
		nodo2 = new NodoGeneral <Integer>(2);
		nodo3 = new NodoGeneral <Integer>(3);
		nodo4 = new NodoGeneral <Integer>(4);
	}
	@Test
	public void testGetDato(){
		assertEquals(new Integer(1),nodo1.getDato());
		assertEquals(new Integer(2),nodo2.getDato());
		assertEquals(new Integer(3),nodo3.getDato());
		assertEquals(new Integer(4),nodo4.getDato());
		
	}
	
	@Test
	public void testSetDato(){
		nodo1.setDato(5);
		nodo2.setDato(5);
		nodo3.setDato(5);
		nodo4.setDato(5);
		assertEquals(new Integer(5),nodo1.getDato());
		assertEquals(new Integer(5),nodo2.getDato());
		assertEquals(new Integer(5),nodo3.getDato());
		assertEquals(new Integer(5),nodo4.getDato());
		
	}
	@Test
	public void test(){
		
	}
}
