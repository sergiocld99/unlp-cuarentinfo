package prog3.arbol.binario.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import parciales.Par;
import prog3.arbol.binario.ArbolBinario;
import prog3.arbol.binario.util.Expresion;
import prog3.arbol.binario.util.Utiles;
import prog3.listaenteros.ListaDeEnteros;
import prog3.listaenteros.ListaDeEnterosEnlazada;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class ArbolBinarioTest {
	ArbolBinario<Integer> arbolBinarioA;
	ArbolBinario<Integer> arbolBinarioB;
	ArbolBinario<Integer> arbolBinarioC;
	ArbolBinario<Integer> arbolBinarioD;
	ArbolBinario<Integer> arbolBinarioE;
	ArbolBinario<Integer> arbolBinarioF;
	ArbolBinario<Integer> arbolBinarioG;
	ArbolBinario<Integer> arbolBinarioH;
	ArbolBinario<Integer> arbolBinarioI;
	ArbolBinario<Integer> n7;
	
	@Before
	public void setUp() throws Exception {
		// ----- arbolBinarioA -----
		arbolBinarioA=new ArbolBinario<Integer>(1);		
		ArbolBinario<Integer> hijoIzquierdo=new ArbolBinario<Integer>(2);
		hijoIzquierdo.agregarHijoIzquierdo(new ArbolBinario<Integer>(3));
		hijoIzquierdo.agregarHijoDerecho(new ArbolBinario<Integer>(4));		
		ArbolBinario<Integer> hijoDerecho=new ArbolBinario<Integer>(5);
		hijoDerecho.agregarHijoIzquierdo(new ArbolBinario<Integer>(6));
		hijoDerecho.agregarHijoDerecho(new ArbolBinario<Integer>(7));
		arbolBinarioA.agregarHijoIzquierdo(hijoIzquierdo);
		arbolBinarioA.agregarHijoDerecho(hijoDerecho);

		// ----- arbolBinarioB -----
		arbolBinarioB=new ArbolBinario<Integer>(1);		
		ArbolBinario<Integer> hijoIzquierdoB=new ArbolBinario<Integer>(2);
		hijoIzquierdoB.agregarHijoIzquierdo(new ArbolBinario<Integer>(3));
		hijoIzquierdoB.agregarHijoDerecho(new ArbolBinario<Integer>(4));		
		ArbolBinario<Integer> hijoDerechoB=new ArbolBinario<Integer>(6);
		hijoDerechoB.agregarHijoIzquierdo(new ArbolBinario<Integer>(7));
		hijoDerechoB.agregarHijoDerecho(new ArbolBinario<Integer>(8));		
		arbolBinarioB.agregarHijoIzquierdo(hijoIzquierdoB);
		arbolBinarioB.agregarHijoDerecho(hijoDerechoB);
		
		// ----- arbolBinarioC -----				
		arbolBinarioC=new ArbolBinario<Integer>(11);		
		ArbolBinario<Integer> hijoIzquierdoC=new ArbolBinario<Integer>(12);
		hijoIzquierdoC.agregarHijoIzquierdo(new ArbolBinario<Integer>(13));
		hijoIzquierdoC.agregarHijoDerecho(new ArbolBinario<Integer>(14));		
		ArbolBinario<Integer> hijoDerechoC=new ArbolBinario<Integer>(15);
		hijoDerechoC.agregarHijoDerecho(new ArbolBinario<Integer>(8));		
		arbolBinarioC.agregarHijoIzquierdo(hijoIzquierdoC);
		arbolBinarioC.agregarHijoDerecho(hijoDerechoC);
		
		// ----- arbolBinarioD -----				
		arbolBinarioD=new ArbolBinario<Integer>(11);		
		ArbolBinario<Integer> hijoIzquierdoD=new ArbolBinario<Integer>(12);
		hijoIzquierdoD.agregarHijoIzquierdo(new ArbolBinario<Integer>(13));
		hijoIzquierdoD.agregarHijoDerecho(new ArbolBinario<Integer>(14));		
		ArbolBinario<Integer> hijoDerechoD=new ArbolBinario<Integer>(15);
		hijoDerechoD.agregarHijoIzquierdo(new ArbolBinario<Integer>(8));		
		arbolBinarioD.agregarHijoIzquierdo(hijoIzquierdoD);
		arbolBinarioD.agregarHijoDerecho(hijoDerechoD);
		
		// ----- arbolBinarioE -----				
		arbolBinarioE=new ArbolBinario<Integer>(1);		
		ArbolBinario<Integer> hijoIzquierdoE=new ArbolBinario<Integer>(2);
		hijoIzquierdoE.agregarHijoIzquierdo(new ArbolBinario<Integer>(4));
		hijoIzquierdoE.agregarHijoDerecho(new ArbolBinario<Integer>(5));		
		ArbolBinario<Integer> hijoDerechoE=new ArbolBinario<Integer>(3);
		hijoDerechoE.agregarHijoIzquierdo(new ArbolBinario<Integer>(6));	
		arbolBinarioE.agregarHijoIzquierdo(hijoIzquierdoE);
		arbolBinarioE.agregarHijoDerecho(hijoDerechoE);
		
		// ----- arbolBinarioF -----				
		arbolBinarioF=new ArbolBinario<Integer>(1);		
		ArbolBinario<Integer> hijoIzquierdoF=new ArbolBinario<Integer>(3);
		hijoIzquierdoF.agregarHijoDerecho(new ArbolBinario<Integer>(6));		
		ArbolBinario<Integer> hijoDerechoF=new ArbolBinario<Integer>(2);
		hijoDerechoF.agregarHijoIzquierdo(new ArbolBinario<Integer>(5));
		hijoDerechoF.agregarHijoDerecho(new ArbolBinario<Integer>(4));		
		arbolBinarioF.agregarHijoIzquierdo(hijoIzquierdoF);
		arbolBinarioF.agregarHijoDerecho(hijoDerechoF);
		
		// ----- arbolBinarioG -----
		arbolBinarioG=new ArbolBinario<Integer>();

		// ----- arbolBinarioH -----
		arbolBinarioH=new ArbolBinario<Integer>(1);
		ArbolBinario<Integer> hijoIzquierdoH=new ArbolBinario<Integer>(3);
		hijoIzquierdoH.agregarHijoDerecho(new ArbolBinario<Integer>(6));
		ArbolBinario<Integer> hijoDerechoH=new ArbolBinario<Integer>(2);
		hijoDerechoH.agregarHijoIzquierdo(new ArbolBinario<Integer>(5));
		arbolBinarioH.agregarHijoIzquierdo(hijoIzquierdoH);
		arbolBinarioH.agregarHijoDerecho(hijoDerechoH);

		// ----- arbolBinarioI -----
		arbolBinarioI = new ArbolBinario<>(7);
		ArbolBinario<Integer> leftSonI = new ArbolBinario<>(1);
		ArbolBinario<Integer> rightSonI = new ArbolBinario<>(3);
		leftSonI.agregarHijoIzquierdo(new ArbolBinario<>(4));
		rightSonI.agregarHijoIzquierdo(new ArbolBinario<>(2));
		rightSonI.agregarHijoDerecho(new ArbolBinario<>(1));
		arbolBinarioI.agregarHijoIzquierdo(leftSonI);
		arbolBinarioI.agregarHijoDerecho(rightSonI);

		// podar y pares
		ArbolBinario<Integer> n1, n2, n3, n4, n5, n6;
		n1 = new ArbolBinario<>(45);
		n1.agregarHijoIzquierdo(new ArbolBinario<>(68));
		n1.agregarHijoDerecho(new ArbolBinario<>(21));

		n2 = new ArbolBinario<>(19);
		n2.agregarHijoIzquierdo(new ArbolBinario<>(83));

		n3 = new ArbolBinario<>(26);
		n3.agregarHijoIzquierdo(n2);
		n3.agregarHijoDerecho(new ArbolBinario<>(41));

		n4 = new ArbolBinario<>(52);
		n4.agregarHijoIzquierdo(n1);
		n4.agregarHijoDerecho(n3);

		n5 = new ArbolBinario<>(96);
		n5.agregarHijoIzquierdo(new ArbolBinario<>(34));

		n6 = new ArbolBinario<>(13);
		n6.agregarHijoIzquierdo(new ArbolBinario<>(78));
		n6.agregarHijoDerecho(n5);

		n7 = new ArbolBinario<>(60);
		n7.agregarHijoIzquierdo(n4);
		n7.agregarHijoDerecho(n6);
	}
	
	@Test
	public void testGetDatoRaiz(){
		assertEquals(new Integer(1),arbolBinarioA.getDatoRaiz());
		assertEquals(new Integer(1),arbolBinarioB.getDatoRaiz());
		assertEquals(new Integer(11),arbolBinarioC.getDatoRaiz());		
		assertEquals(null,arbolBinarioG.getDatoRaiz());
		arbolBinarioG = new ArbolBinario<Integer>(33);
		assertEquals(new Integer(33),arbolBinarioG.getDatoRaiz());
	}
	
	@Test
	public void testGetHijoIzquierdo(){
		assertEquals(new Integer (2),arbolBinarioA.getHijoIzquierdo().getDatoRaiz());
		
	}
	
	@Test
	public void testGetHijoDerecho(){
		assertEquals(new Integer (5),arbolBinarioA.getHijoDerecho().getDatoRaiz());
	}
	
	@Test
	public void testAgregarHijoIzquierdo(){
		ArbolBinario<Integer> hijo = new ArbolBinario<Integer>(25);	
		arbolBinarioG = new ArbolBinario<Integer>(33);
		arbolBinarioG.agregarHijoIzquierdo(hijo);
		assertEquals(hijo.getDatoRaiz(),arbolBinarioG.getHijoIzquierdo().getDatoRaiz());
	}
	
	@Test
	public void testAgregarHijoDerecho(){
		ArbolBinario<Integer> hijo = new ArbolBinario<Integer>(105);	
		arbolBinarioG = new ArbolBinario<Integer>(33);
		arbolBinarioG.agregarHijoDerecho(hijo);
		assertEquals(hijo.getDatoRaiz(),arbolBinarioG.getHijoDerecho().getDatoRaiz());		
	}
	
	@Test
	public void testEliminarHijoIzquierdo(){
		ArbolBinario<Integer> hijo = new ArbolBinario<Integer>(25);	
		arbolBinarioG = new ArbolBinario<Integer>(33);
		arbolBinarioG.agregarHijoIzquierdo(hijo);
		assertEquals(hijo.getDatoRaiz(),arbolBinarioG.getHijoIzquierdo().getDatoRaiz());
		arbolBinarioG.eliminarHijoIzquierdo();
		assertEquals(null,arbolBinarioG.getHijoIzquierdo().getDatoRaiz());		
	}
	
	@Test
	public void testEliminarHijoDerecho(){
		ArbolBinario<Integer> hijo = new ArbolBinario<Integer>(25);	
		arbolBinarioG = new ArbolBinario<Integer>(33);
		arbolBinarioG.agregarHijoDerecho(hijo);
		assertEquals(hijo.getDatoRaiz(),arbolBinarioG.getHijoDerecho().getDatoRaiz());
		arbolBinarioG.eliminarHijoDerecho();
		assertEquals(null,arbolBinarioG.getHijoDerecho().getDatoRaiz());
	}	
	@Test
	public void testFrontera() {
		
		//CASO arbolBinarioA
		ListaGenericaEnlazada<Integer> listaFronteraEsperada=new ListaGenericaEnlazada<Integer>();
		listaFronteraEsperada.agregarFinal(3);
		listaFronteraEsperada.agregarFinal(4);
		listaFronteraEsperada.agregarFinal(6);
		listaFronteraEsperada.agregarFinal(7);
		
		ListaGenerica<Integer> listaFronteraDeArbolBinario=arbolBinarioA.frontera();
		assertEquals(listaFronteraEsperada.elemento(0), listaFronteraDeArbolBinario.elemento(0));
		assertEquals(listaFronteraEsperada.elemento(1), listaFronteraDeArbolBinario.elemento(1));
		assertEquals(listaFronteraEsperada.elemento(2), listaFronteraDeArbolBinario.elemento(2));
		assertEquals(listaFronteraEsperada.elemento(3), listaFronteraDeArbolBinario.elemento(3));
		
		
		//CASO arbolBinarioC
		listaFronteraEsperada=new ListaGenericaEnlazada<Integer>();
		listaFronteraEsperada.agregarEn(13,0);
		listaFronteraEsperada.agregarEn(14,1);
		listaFronteraEsperada.agregarEn(8,2);
		
		listaFronteraDeArbolBinario=arbolBinarioC.frontera();
		assertEquals(listaFronteraEsperada.elemento(0), listaFronteraDeArbolBinario.elemento(0));
		assertEquals(listaFronteraEsperada.elemento(1), listaFronteraDeArbolBinario.elemento(1));
		assertEquals(listaFronteraEsperada.elemento(2), listaFronteraDeArbolBinario.elemento(2));
	}

	
	@Test
	public void testLleno() {
		assertTrue(arbolBinarioA.lleno());
		assertFalse(arbolBinarioD.lleno());
		assertFalse(arbolBinarioH.lleno());
	}
	

	@Test
	public void testCompleto() {
		assertTrue(arbolBinarioA.completo());
		assertFalse(arbolBinarioC.completo());
		assertTrue(arbolBinarioD.completo());
		assertFalse(arbolBinarioH.completo());
	}

	@Test
	public void testSumaVertical(){
		Utiles utiles = new Utiles();
		assertEquals(13, utiles.sumaMaximaVertical(arbolBinarioA));
		assertEquals(15, utiles.sumaMaximaVertical(arbolBinarioB));
		assertEquals(37, utiles.sumaMaximaVertical(arbolBinarioC));
		assertEquals(37, utiles.sumaMaximaVertical(arbolBinarioD));
		assertEquals(10, utiles.sumaMaximaVertical(arbolBinarioE));
		assertEquals(10, utiles.sumaMaximaVertical(arbolBinarioF));
		assertEquals(0, utiles.sumaMaximaVertical(arbolBinarioG));
		assertEquals(10, utiles.sumaMaximaVertical(arbolBinarioH));
		assertEquals(12, utiles.sumaMaximaVertical(arbolBinarioI));
	}
	
	@Test
	public void testSumaHorizontal(){
		Utiles utiles = new Utiles();
		assertEquals(20, utiles.sumaMaximaHorizontal(arbolBinarioA));
		assertEquals(22, utiles.sumaMaximaHorizontal(arbolBinarioB));
		assertEquals(35, utiles.sumaMaximaHorizontal(arbolBinarioC));
		assertEquals(15, utiles.sumaMaximaHorizontal(arbolBinarioE));
		assertEquals(15, utiles.sumaMaximaHorizontal(arbolBinarioF));
		assertEquals(11, utiles.sumaMaximaHorizontal(arbolBinarioH));
		assertEquals(7, utiles.sumaMaximaHorizontal(arbolBinarioI));
	}

	@Test
	public void testTrayectoriasPesadas(){
		Utiles utiles = new Utiles();
		ListaDeEnteros resultI = utiles.trayectoriaPesada(arbolBinarioI);
		ListaDeEnteros expectedI = new ListaDeEnterosEnlazada();
		expectedI.agregarFinal(9);
		expectedI.agregarFinal(7);
		expectedI.agregarFinal(5);
		while (!expectedI.fin()) assertEquals(expectedI.proximo(), resultI.proximo());

		ListaDeEnteros resultH = utiles.trayectoriaPesada(arbolBinarioH);
		ListaDeEnteros expectedH = new ListaDeEnterosEnlazada();
		expectedH.agregarFinal(15);
		expectedH.agregarFinal(12);
		while (!expectedH.fin()) assertEquals(expectedH.proximo(), resultH.proximo());

		ListaDeEnteros resultF = utiles.trayectoriaPesada(arbolBinarioF);
		ListaDeEnteros expectedF = new ListaDeEnterosEnlazada();
		expectedF.agregarFinal(15);
		expectedF.agregarFinal(12);
		expectedF.agregarFinal(10);
		while (!expectedF.fin()) assertEquals(expectedF.proximo(), resultF.proximo());
	}

	@Test
	public void testExpresion(){
		Expresion exp1, exp2, exp3, exp4, exp5;

		exp5 = new Expresion();
		exp5.armarConPostfija("abe+/cd*-");
		System.out.println(exp5);

		exp1 = new Expresion();
		exp1.armarConPostfija("65*73-48+*+");
		assertTrue(exp1.chequearInfija("6*5 + (7-3) * (4+8)"));
		assertTrue(exp1.chequearPrefija("+*65*-73+48"));
		System.out.println(exp1);


		ArbolBinario<Character> n1 = new ArbolBinario<>('-');
		n1.agregarHijoIzquierdo(new ArbolBinario<>('a'));
		n1.agregarHijoDerecho(new ArbolBinario<>('b'));

		ArbolBinario<Character> n2 = new ArbolBinario<>('/');
		n2.agregarHijoIzquierdo(n1);
		n2.agregarHijoDerecho(new ArbolBinario<>('c'));

		ArbolBinario<Character> n3 = new ArbolBinario<>('*');
		n3.agregarHijoIzquierdo(new ArbolBinario<>('d'));
		n3.agregarHijoDerecho(new ArbolBinario<>('e'));

		ArbolBinario<Character> n4 = new ArbolBinario<>('+');
		n4.agregarHijoIzquierdo(n2);
		n4.agregarHijoDerecho(n3);

		exp2 = new Expresion(n4);
		assertTrue(exp2.chequearInfija("(((a-b) / c) + (d * e))"));
		System.out.println(exp2);

		exp3 = new Expresion();
		exp3.armarConPostfija("65*73-48*++");
		System.out.println(exp3);

		exp4 = new Expresion();
		exp4.armarConInfija("a+b*(c-d)-e");
		assertTrue(exp4.chequearPostfija("abcd-*+e-"));
		System.out.println(exp4);
	}

	@Test
	public void testPares(){
		preordenPares(n7.anotarMaximos(n7));
	}
	
	@Test
	public void testPodar(){
		ArbolBinario<Integer> podado = n7.podar();
		preorden(podado);
	}
	
	private void preorden(ArbolBinario<Integer> ab){
		if (ab == null || ab.esVacio()) return;
		System.out.print(ab.getDatoRaiz() + " ");
		preorden(ab.getHijoIzquierdo());
		preorden(ab.getHijoDerecho());
	}

	private void preordenPares(ArbolBinario<Par> ab){
		if (ab == null || ab.esVacio()) return;
		System.out.print(ab.getDatoRaiz() + " ");
		preordenPares(ab.getHijoIzquierdo());
		preordenPares(ab.getHijoDerecho());
	}
}
