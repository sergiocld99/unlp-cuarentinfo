package prog3.listaenteros.test;

import org.junit.Before;
import org.junit.Test;
import prog3.listaenteros.ListaDeEnterosConArreglos;

import static org.junit.Assert.*;

/**
 * Clase que permite testear la ListaDeEnterosConArreglos. Recordar que la
 * ListaDeEnterosConArreglos tiene capacidad para 10 elementos, por tanto
 * empieza en la posicion 0 y termina en la posicion 9.
 */
public class ListaDeEnterosConArreglosJUnitTest {

	ListaDeEnterosConArreglos l;

	@Before
	public void setUp() throws Exception {
		l = new ListaDeEnterosConArreglos();
	}

	@Test
	public void testComenzar() {
		// Probando con un elemento
		// agrego en numero 3 en la posicion 0 del arreglo
		l.agregarEn(3, 0);
		l.comenzar();
		assertEquals(new Integer(3), l.proximo());

		// Probando con dos elementos
		// agrego en numero 2 en la posicion 1 del arreglo
		l.agregarEn(2, 1);
		l.comenzar();
		assertEquals(new Integer(3), l.proximo());
		assertEquals(new Integer(2), l.proximo());

		// Probando de agregarEn un elemento al principio
		// agrego en numero 1 en la posicion 0 del arreglo
		// por tanto los elementos existentes deben correrse de lugar
		l.agregarEn(1, 0);
		l.comenzar();
		assertEquals(new Integer(1), l.proximo());
	}

	@Test
	public void testProximo() {
		// ingreso a la lista los elementos 0..9 en esas mismas posiciones
		for (int i = 0; i < 10; i++)
			l.agregarEn(i, i);

		// recorro la lista verificando que los elementos 0..9 se ubican en las
		// posiciones 0..9
		l.comenzar();
		for (int i = 0; i < 10; i++) {
			assertEquals(new Integer(i), l.proximo());
		}
		assertTrue(l.fin());

	}

	@Test
	public void testAgregarEn() {
		// No se puede agregarEn en indices negativos
		assertFalse(l.agregarEn(1, -1));
		assertEquals(0, l.tamanio());

		// AgregarEn al principio cuando no hay nada
		assertTrue(l.agregarEn(3, 0));
		assertEquals(new Integer(3), l.elemento(0));
		assertEquals(1, l.tamanio());

		// prog3.arbol.Test de agregarEn al principio cuando hay algo
		assertTrue(l.agregarEn(1, 0));
		assertEquals(new Integer(1), l.elemento(0));
		assertEquals(new Integer(3), l.elemento(1));
		assertEquals(2, l.tamanio());

		// prog3.arbol.Test de agregarEn entremedio
		assertTrue(l.agregarEn(2, 1));
		assertEquals(new Integer(1), l.elemento(0));
		assertEquals(new Integer(2), l.elemento(1));
		assertEquals(new Integer(3), l.elemento(2));
		assertEquals(3, l.tamanio());

		// prog3.arbol.Test de agregarEn al final
		assertTrue(l.agregarEn(4, 3));
		assertEquals(new Integer(1), l.elemento(0));
		assertEquals(new Integer(2), l.elemento(1));
		assertEquals(new Integer(3), l.elemento(2));
		assertEquals(new Integer(4), l.elemento(3));
		assertEquals(4, l.tamanio());

	}

	@Test
	public void testEliminarEn() {
		// No se puede borrar indices <1
		assertFalse(l.eliminarEn(0));
		assertEquals(0, l.tamanio());
		assertFalse(l.eliminarEn(-1));
		assertEquals(0, l.tamanio());

		for (int i = 1; i <= 10; i++)
			l.agregarEn(i, i - 1);

		// prog3.arbol.Test eliminar el primer elemento cuando hay mas
		assertTrue(l.eliminarEn(0));
		assertEquals(new Integer(2), l.elemento(0));

		// prog3.arbol.Test eliminar un elemento del medio (el que esta en la posicion 3)
		assertTrue(l.eliminarEn(3));
		assertEquals(new Integer(9), l.elemento(l.tamanio() - 1));
		assertEquals(8, l.tamanio());

		assertTrue(l.agregarEn(1, 0)); // completo la lista
		assertTrue(l.agregarEn(5, 4));

		assertTrue(l.eliminarEn(9));
		assertEquals(9, l.tamanio());
		for (int i = 1; i < l.tamanio(); i++)
			assertEquals(new Integer(i), l.elemento(i - 1));

		// No se puede eliminar un indice >= tamano.
		assertFalse(l.eliminarEn(10));
		assertEquals(9, l.tamanio());
		for (int i = 1; i < l.tamanio(); i++)
			assertEquals(new Integer(i), l.elemento(i - 1));
	}

	@Test
	public void testIncluye() {
		// No se tiene que poder encontrar algo cuando la lista esta vacia
		assertFalse(l.incluye(1));

		// prog3.arbol.Test de busqueda sobre el primer elemento
		l.agregarEn(1, 0);
		assertTrue(l.incluye(1));
		assertFalse(l.incluye(2));

		// prog3.arbol.Test con mas elementos
		l.agregarEn(2, 1);
		l.agregarEn(3, 2);
		assertTrue(l.incluye(2));
		assertFalse(l.incluye(4));
	}

	@Test
	public void testAgregarFinal() {
		// prog3.arbol.Test de agregarEn al principio cuando no hay nada
		assertTrue(l.agregarFinal(1));
		assertEquals(1, l.tamanio());
		assertTrue(l.agregarFinal(2));
		assertEquals(2, l.tamanio());
		assertTrue(l.agregarFinal(3));
		assertEquals(3, l.tamanio());
	}

	@Test
	public void testAgregarInicio() {
		// prog3.arbol.Test de agregarEn al principio cuando no hay nada
		assertTrue(l.agregarInicio(3));
		assertEquals(1, l.tamanio());
		assertTrue(l.agregarInicio(2));
		assertEquals(2, l.tamanio());
		assertTrue(l.agregarInicio(1));
		assertEquals(3, l.tamanio());
	}


	
}
