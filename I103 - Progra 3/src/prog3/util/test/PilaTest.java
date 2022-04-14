package prog3.util.test;

import prog3.util.PilaGenerica;

public class PilaTest {

    // TODO: IT'S OK!
    public static void main(String[] args) {
        PilaGenerica<Character> pila = new PilaGenerica<>();
        for (char i='a'; i<'f'; i++) pila.apilar(i);
        for (int i=0; i<4; i++) System.out.println("Popped: "+pila.desapilar());
        System.out.println("Top: "+pila.tope());
    }
}
