import prog3.grafos.Grafo;
import prog3.grafos.GrafoImplListAdy;
import prog3.grafos.Vertice;
import prog3.grafos.VerticeImplListAdy;

public class Test {

    public static void main(String[] args) {
        Grafo<Ciudad> grafo = new GrafoImplListAdy<>();
        Vertice<Ciudad> LP, Q, MOR, CK, SUI, NAV, SAL, LOB, CAN, AB, ALT, PIN;

        LP = new VerticeImplListAdy<>(new Ciudad("La Plata", 3));
        Q = new VerticeImplListAdy<>(new Ciudad("Quilmes", 3));
        MOR = new VerticeImplListAdy<>(new Ciudad("Moreno", 4));
        CK = new VerticeImplListAdy<>(new Ciudad("Carlos Keen", 5));
        SUI = new VerticeImplListAdy<>(new Ciudad("Suipacha", 3));
        NAV = new VerticeImplListAdy<>(new Ciudad("Navarro", 1));
        SAL = new VerticeImplListAdy<>(new Ciudad("Saladillo", 2));
        LOB = new VerticeImplListAdy<>(new Ciudad("Lobos", 1));
        CAN = new VerticeImplListAdy<>(new Ciudad("Cañuelas", 2));
        AB = new VerticeImplListAdy<>(new Ciudad("Abasto", 4));
        ALT = new VerticeImplListAdy<>(new Ciudad("Altamirano", 1));
        PIN = new VerticeImplListAdy<>(new Ciudad("Pinamar", 0));

        grafo.agregarVertice(LP);
        grafo.agregarVertice(Q);
        grafo.agregarVertice(MOR);
        grafo.agregarVertice(CK);
        grafo.agregarVertice(SUI);
        grafo.agregarVertice(NAV);
        grafo.agregarVertice(SAL);
        grafo.agregarVertice(LOB);
        grafo.agregarVertice(CAN);
        grafo.agregarVertice(AB);
        grafo.agregarVertice(ALT);
        grafo.agregarVertice(PIN);

        grafo.conectar(LP, Q, 0);
        grafo.conectar(LP, AB, 0);
        grafo.conectar(LP, ALT, 0);
        grafo.conectar(LP, PIN, 1);

        grafo.conectar(Q, LP, 0);
        grafo.conectar(Q, MOR, 0);

        grafo.conectar(MOR, AB, 1);
        grafo.conectar(MOR, CK, 0);
        grafo.conectar(MOR, Q, 0);

        grafo.conectar(CK, SUI, 0);
        grafo.conectar(CK, MOR, 0);

        grafo.conectar(SUI, CK, 0);
        grafo.conectar(SUI, NAV, 0);

        grafo.conectar(NAV, LOB, 1);
        grafo.conectar(NAV, SAL, 0);
        grafo.conectar(NAV, CAN, 0);
        grafo.conectar(NAV, SUI, 0);

        grafo.conectar(CAN, NAV, 0);
        grafo.conectar(CAN, AB, 0);
        grafo.conectar(CAN, ALT, 0);

        grafo.conectar(AB, MOR, 1);
        grafo.conectar(AB, CAN, 0);
        grafo.conectar(AB, LP, 0);

        grafo.conectar(SAL, NAV, 0);
        grafo.conectar(LOB, NAV, 1);
        grafo.conectar(ALT, CAN, 0);
        grafo.conectar(ALT, LP, 0);
        grafo.conectar(PIN, LP, 1);

        System.out.println(new Parcial().resolver(grafo, "La Plata", "Suipacha", 2));
        System.out.println(new Parcial().resolver(grafo, "Suipacha", "La Plata", 0));
        System.out.println(new Parcial().resolver(grafo, "La Plata", "Navarro", 0));
    }
}
