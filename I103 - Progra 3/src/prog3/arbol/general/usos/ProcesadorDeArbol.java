package prog3.arbol.general.usos;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;

public class ProcesadorDeArbol {
    private final ArbolGeneral<Integer> miArbol;

    public ProcesadorDeArbol(ArbolGeneral<Integer> unArbol){
        this.miArbol = unArbol;
    }

    public int[] procesar(){
        int[] retorno = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE};
        if (this.miArbol != null && !this.miArbol.esVacio())
            this.procesar(this.miArbol, retorno);
        return retorno;
    }

    private void procesar(ArbolGeneral<Integer> arbol, int[] resultados){
        if (arbol.esHoja()){
            if (arbol.getDatoRaiz() > resultados[0]) resultados[0] = arbol.getDatoRaiz();
            if (arbol.getDatoRaiz() < resultados[1]) resultados[1] = arbol.getDatoRaiz();
        } else {
            ListaGenerica<ArbolGeneral<Integer>> hijos = arbol.getHijos();
            hijos.comenzar();
            while (!hijos.fin()) procesar(hijos.proximo(), resultados);
        }
    }
}
