package prog3.arbol.general.test;

import org.junit.Before;
import org.junit.Test;

import parciales.Ejercicios;
import prog3.arbol.general.ArbolGeneral;
import prog3.arbol.general.usos.ProcesadorDeArbol;

import static org.junit.Assert.*;

public class ArbolGeneralTest {
	ArbolGeneral<Integer> ag;
	ArbolGeneral<Integer> a2;
	ArbolGeneral<Integer> a3;
	ArbolGeneral<Integer> a4;
	ArbolGeneral<Integer> a5;
	ArbolGeneral<Integer> a6;
	ArbolGeneral<Integer> a7;
	ArbolGeneral<Integer> a8;
	ArbolGeneral<Integer> a9;
	ArbolGeneral<Integer> a10;
	ArbolGeneral<Integer> n8;
	
	@Before
	public void setUp() throws Exception {
		
		ag = new ArbolGeneral<Integer>(1);
		
		a2 = new ArbolGeneral<Integer>(2);
		a3 = new ArbolGeneral<Integer>(3);
		a4 = new ArbolGeneral<Integer>(4);
		a5 = new ArbolGeneral<Integer>(5);
		a6 = new ArbolGeneral<Integer>(6);
		a7 = new ArbolGeneral<Integer>(7);
		a8 = new ArbolGeneral<Integer>(8);
		a9 = new ArbolGeneral<Integer>(9);
		a10= new ArbolGeneral<Integer>(10);
		ag.agregarHijo(a2);
		ag.agregarHijo(a3);
		ag.agregarHijo(a4);
		a2.agregarHijo(a5);
		a3.agregarHijo(a6);
		a3.agregarHijo(a7);
		a3.agregarHijo(a8);
		a4.agregarHijo(a9);
		
		
/*      Arbol usado en las pruebas
 					1
		2			3			4
		5		6	7	8		9
		
*/

		ArbolGeneral<Integer> n1 = new ArbolGeneral<>(1);
		n1.agregarHijo(new ArbolGeneral<>(5));
		n1.agregarHijo(new ArbolGeneral<>(1));

		ArbolGeneral<Integer> n2 = new ArbolGeneral<>(2);
		n2.agregarHijo(n1);
		n2.agregarHijo(new ArbolGeneral<>(2));

		ArbolGeneral<Integer> n7 = new ArbolGeneral<>(7);
		n7.agregarHijo(new ArbolGeneral<>(4));
		n7.agregarHijo(new ArbolGeneral<>(1));

		ArbolGeneral<Integer> n10 = new ArbolGeneral<>(10);
		n10.agregarHijo(n7);
		n10.agregarHijo(new ArbolGeneral<>(6));
		n10.agregarHijo(new ArbolGeneral<>(4));

		ArbolGeneral<Integer> n9 = new ArbolGeneral<>(9);
		n9.agregarHijo(new ArbolGeneral<>(7));
		n9.agregarHijo(new ArbolGeneral<>(6));

		n8 = new ArbolGeneral<>(8);
		n8.agregarHijo(n2);
		n8.agregarHijo(n10);
		n8.agregarHijo(n9);

	}

	@Test
	public void testAltura() {
		assertEquals(new Integer(2), ag.altura());
		assertEquals(new Integer(1), a2.altura());
		assertEquals(new Integer(0), a5.altura());
		assertEquals(Integer.valueOf(0), a7.altura());
		assertEquals(Integer.valueOf(1), a4.altura());
	}

	@Test
	public void testInclude(){
		assertTrue(ag.include(1));
		assertTrue(ag.include(3));
		assertTrue(ag.include(7));
		assertFalse(ag.include(11));
	}

	@Test
	public void testNivel() {
		//Prueba para un arbol que es solo una hoja
		assertEquals(new Integer(0), a5.nivel(5));
		
		//Prueba para distintos elementos del arbol ag
		assertEquals(new Integer(0), ag.nivel(1));
		assertEquals(new Integer(1), ag.nivel(2));
		assertEquals(new Integer(2), ag.nivel(7));
		assertEquals(new Integer(2), ag.nivel(9));
		assertEquals(new Integer(-1), ag.nivel(10));
	}

	@Test
	public void testAncho() {
		assertEquals(new Integer(5), ag.ancho());
		assertEquals(new Integer(3), a3.ancho());
		assertEquals(new Integer(1), a4.ancho());
		assertEquals(Integer.valueOf(1), a2.ancho());
		assertEquals(Integer.valueOf(-1), new ArbolGeneral<>().ancho());
	}

	@Test
	public void testProcesador(){
		int[] resultados = new ProcesadorDeArbol(n8).procesar();
		assertEquals(7, resultados[0]);
		assertEquals(1, resultados[1]);
	}

	@Test
	public void testCaminoMasLargo(){
		System.out.println(new Ejercicios<Integer>().caminoMasLargo(n8));
	}
}
