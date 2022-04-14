package prog3.arbol.binario.util;

import prog3.arbol.binario.ArbolBinario;
import prog3.util.PilaGenerica;

public class Expresion {
    private ArbolBinario<Character> arbol;

    public Expresion(){
        this.arbol = null;
    }

    public Expresion(ArbolBinario<Character> unArbol){
        this.arbol = unArbol;
    }

    /** Arma el arbol a partir de una expresion postfija pasada como parámetro.
     *  La cadena solo debe tener operandos y operadores (no se admiten blancos) */
    public void armarConPostfija(String exp){
        PilaGenerica<ArbolBinario<Character>> pila = new PilaGenerica<>();
        ArbolBinario<Character> nuevo;

        for (char c : exp.toCharArray()){
            nuevo = new ArbolBinario<>(c);
            if (esOperador(c)){
                nuevo.agregarHijoDerecho(pila.desapilar());
                nuevo.agregarHijoIzquierdo(pila.desapilar());
            }
            pila.apilar(nuevo);
        }

        this.arbol = pila.desapilar();
    }

    /** Arma el arbol a partir de una expresion prefija pasada como parámetro.
     * La cadena puede tener operadores, operandos y blancos */
    public void armarConPrefija(String exp){
        this.arbol = armarConPrefija(new StringBuilder(exp));
    }

    private ArbolBinario<Character> armarConPrefija(StringBuilder exp){
        if (exp.length() == 0) return null;
        while (exp.charAt(0) == ' ') exp.deleteCharAt(0);
        char c = exp.charAt(0);

        ArbolBinario<Character> nuevo = new ArbolBinario<>(c);

        if (esOperador(c)){
            ArbolBinario<Character> subI = armarConPrefija(exp.deleteCharAt(0));
            ArbolBinario<Character> subD = armarConPrefija(exp.deleteCharAt(0));
            if (subI != null) nuevo.agregarHijoIzquierdo(subI);
            if (subD != null) nuevo.agregarHijoDerecho(subD);
        }

        return nuevo;
    }

    /** Convierte la expresion a postfija y arma el arbol.
     * Admite blancos, exponentes y paréntesis (cerrarlos como corresponde) */
    public void armarConInfija(String exp){
        PilaGenerica<Character> pila = new PilaGenerica<>();    // pila de signos y paréntesis
        StringBuilder postfija = new StringBuilder();
        char aux;

        for (char c : exp.toCharArray()){
            if (c == ' ') continue;
            if (c == ')') {
                while (pila.tope() != '(') postfija.append(pila.desapilar());
                pila.desapilar();   // no se agrega '(' a la expresion
            }
            else if (c == '(') pila.apilar(c);
            else if (esOperador(c)){
                while (!pila.esVacia() && prioridad(c) <= prioridad(pila.tope())){
                    aux = pila.desapilar();
                    if (aux != '(') postfija.append(aux);
                }
                pila.apilar(c);
            } else postfija.append(c);
        }

        while (!pila.esVacia()) postfija.append(pila.desapilar());
        this.armarConPostfija(postfija.toString());
    }

    public boolean chequearInfija(String exp){
        Expresion otro = new Expresion();
        otro.armarConInfija(exp);

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        this.getInfija(sb1, this.arbol);
        otro.getInfija(sb2, otro.arbol);

        return sb1.toString().equals(sb2.toString());
    }

    public boolean chequearPrefija(String exp){
        Expresion otro = new Expresion();
        otro.armarConPrefija(exp);

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        this.getPrefija(sb1, this.arbol);
        otro.getPrefija(sb2, otro.arbol);

        return sb1.toString().equals(sb2.toString());
    }

    public boolean chequearPostfija(String exp){
        Expresion otro = new Expresion();
        otro.armarConPostfija(exp);

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        this.getPostfija(sb1, this.arbol);
        otro.getPostfija(sb2, otro.arbol);

        return sb1.toString().equals(sb2.toString());
    }

    /** Intenta resolver el arbol de expresión si sus operandos son numeros.
     * Si no puede se atrapa la excepción para que el programa no se rompa */
    public int resultado() {
        try {
            return resultado(this.arbol);
        } catch (Exception e){
            return -1;
        }
    }

    private int prioridad(char c){
        if (c == '^') return 2;
        if (c == '*' || c == '/') return 1;
        if (c == '+' || c == '-') return 0;
        return -1;
    }

    private boolean esOperador(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // IN ORDEN
    private void getInfija(StringBuilder sb, ArbolBinario<Character> ab){
        if (!ab.esHoja()) sb.append("(");
        if (!ab.getHijoIzquierdo().esVacio()) getInfija(sb, ab.getHijoIzquierdo());
        sb.append(ab.getDatoRaiz());
        if (!ab.getHijoDerecho().esVacio()) getInfija(sb, ab.getHijoDerecho());
        if (!ab.esHoja()) sb.append(")");
    }

    // PRE ORDEN
    private void getPrefija(StringBuilder sb, ArbolBinario<Character> ab){
        sb.append(ab.getDatoRaiz());
        if (!ab.getHijoIzquierdo().esVacio()) getPrefija(sb, ab.getHijoIzquierdo());
        if (!ab.getHijoDerecho().esVacio()) getPrefija(sb, ab.getHijoDerecho());
    }

    // POST ORDEN
    private void getPostfija(StringBuilder sb, ArbolBinario<Character> ab){
        if (!ab.getHijoIzquierdo().esVacio()) getPostfija(sb, ab.getHijoIzquierdo());
        if (!ab.getHijoDerecho().esVacio()) getPostfija(sb, ab.getHijoDerecho());
        sb.append(ab.getDatoRaiz());
    }

    // POST ORDEN
    private int resultado(ArbolBinario<Character> ab){
        if (ab.esHoja()) return Integer.parseInt(ab.getDatoRaiz().toString());
        int left = resultado(ab.getHijoIzquierdo());
        int right = resultado(ab.getHijoDerecho());
        if (ab.getDatoRaiz() == '+') return left + right;
        if (ab.getDatoRaiz() == '-') return left - right;
        if (ab.getDatoRaiz() == '*') return left * right;
        if (ab.getDatoRaiz() == '/') return left / right;
        else return (int) Math.pow(left, right);
    }

    @Override
    public String toString() {
        StringBuilder infija = new StringBuilder();
        StringBuilder prefija = new StringBuilder();
        StringBuilder posfija = new StringBuilder();

        getInfija(infija, this.arbol);
        getPrefija(prefija, this.arbol);
        getPostfija(posfija, this.arbol);

        return "Infija: " + infija + " = " + this.resultado() +
                "\nPrefija: " + prefija +
                "\nPosfija: " + posfija + "\n";
    }
}
