package prog3.arbol.avl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import prog3.arbol.avl.ArbolAVL;


public class AVLTest {

	ArbolAVL<String> arbol;
	ArbolAVL<String> arbolVacio;
	@Before
	public void setUp(){
		arbolVacio = new ArbolAVL<String>(null);
	}
	
	@Test
	public void creacion() {
		assertTrue(arbolVacio.esVacio());
	}
	
	@Test
	public void creacionConElemento(){
		arbol = new ArbolAVL<String>("Hola");

		assertFalse(arbol.esVacio());
		assertTrue(arbol.getHijoDerecho().esVacio());
		assertTrue(arbol.getHijoIzquierdo().esVacio());
	}
	
	@Test
	public void carga(){
		arbol = new ArbolAVL<String>("hola");

		arbol.agregar("mundo");
		
		assertEquals("mundo", arbol.getHijoDerecho().getDatoRaiz());
		
		arbol.agregar("otro");
		//mantiene la posicion del anterior
		assertEquals("otro", arbol.getHijoDerecho().getDatoRaiz());
		//agrego el nuevo al final de la rama derecha
		assertEquals("mundo", arbol.getDatoRaiz());
		assertEquals("hola", arbol.getHijoIzquierdo().getDatoRaiz());
		arbol.agregar("al comienzo");
		assertEquals("al comienzo", arbol.getHijoIzquierdo().getHijoIzquierdo().getDatoRaiz());
		
		arbol.printTree();
	}
	
	@Test
	public void busqueda(){
		arbol = new ArbolAVL<String>("hola");
		arbol.agregar("otro");
		arbol.agregar("mundo");

		assertTrue(arbol.incluye("hola"));
		assertTrue(arbol.incluye("otro"));
		assertTrue(arbol.incluye("mundo"));
		
		assertEquals("mundo", arbol.buscar("mundo"));
		assertNull(arbol.buscar("no esta"));
		
		assertFalse(arbol.incluye("al comienzo"));
		assertFalse(arbol.incluye("zz fin"));
	}
	
	
	
	
}
