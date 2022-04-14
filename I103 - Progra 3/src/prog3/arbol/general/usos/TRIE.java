package prog3.arbol.general.usos;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class TRIE {
    private final ArbolGeneral<Character> root;

    public TRIE(){
        this.root = new ArbolGeneral<>(null);
    }

    // TODO: WORKING FINE :)
    public static void main(String[] args) {
        TRIE diccio = new TRIE();
        diccio.agregarPalabra("ARBOL");
        diccio.agregarPalabra("Arbusto");
        diccio.agregarPalabra("arboleda");
        diccio.agregarPalabra("arbitraje");
        diccio.agregarPalabra("PROGRAMACION");
        diccio.agregarPalabra("Programar");
        diccio.agregarPalabra("programa");
        System.out.println(diccio.palabrasQueEmpiezanCon(""));
    }

    public void agregarPalabra(String palabra){
        if (palabra == null || palabra.isEmpty()) return;
        char[] letras = palabra.toLowerCase().toCharArray();

        ArbolGeneral<Character> aux, actual = this.root;

        for (char c : letras){
            aux = buscarHijo(c, actual);
            if (aux == null){
                aux = new ArbolGeneral<>(c);
                agregarHijo(aux, actual);
            }
            actual = aux;
        }
    }

    // Método propio: devuelve un árbol con el hijo buscado. Si no lo encuentra, devuelve null
    private ArbolGeneral<Character> buscarHijo(Character c, ArbolGeneral<Character> arbol){
        ListaGenerica<ArbolGeneral<Character>> hijos = arbol.getHijos();
        ArbolGeneral<Character> actual;

        hijos.comenzar();
        do {
            actual = hijos.fin() ? null : hijos.proximo();
        } while (actual != null && actual.getDatoRaiz() != c);

        return actual;
    }

    private void agregarHijo(ArbolGeneral<Character> unHijo, ArbolGeneral<Character> arbol) {
        if (arbol.esHoja()) arbol.agregarHijo(unHijo);
        else {
            ListaGenerica<ArbolGeneral<Character>> aux, hijos;
            ArbolGeneral<Character> actual;

            aux = new ListaGenericaEnlazada<>();
            aux.agregarFinal(unHijo);

            hijos = arbol.getHijos();
            hijos.comenzar();

            while (!hijos.fin()) {
                actual = hijos.proximo();
                if (actual.getDatoRaiz() > unHijo.getDatoRaiz()){
                    aux.agregarFinal(actual);
                    arbol.eliminarHijo(actual);
                }
            }

            aux.comenzar();
            while (!aux.fin()){
                arbol.agregarHijo(aux.proximo());
            }
        }
    }


    public ListaGenerica<StringBuilder> palabrasQueEmpiezanCon(String prefijo){
        if (prefijo == null) return null;
        char[] prefix = prefijo.toLowerCase().toCharArray();

        ListaGenerica<StringBuilder> ret = new ListaGenericaEnlazada<>();
        ArbolGeneral<Character> actual = this.root;
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<prefix.length && actual != null; i++){
            actual = buscarHijo(prefix[i], actual);
            sb.append(prefix[i]);
        }

        if (actual != null){
            ListaGenerica<ArbolGeneral<Character>> hijos = actual.getHijos();
            hijos.comenzar();
            while (!hijos.fin()) construir(ret, sb, hijos.proximo());
        }

        return ret;
    }

    private void construir( ListaGenerica<StringBuilder> list,  StringBuilder pref,
                           ArbolGeneral<Character> arbol){
        pref = new StringBuilder(pref).append(arbol.getDatoRaiz());
        if (arbol.esHoja()) list.agregarFinal(pref);
        else {
            ListaGenerica<ArbolGeneral<Character>> hijos = arbol.getHijos();
            hijos.comenzar();

            while (!hijos.fin()) construir(list, pref, hijos.proximo());
        }
    }
}
