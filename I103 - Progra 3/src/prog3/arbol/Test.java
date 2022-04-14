package prog3.arbol;

import prog3.arbol.general.ArbolGeneral;

public class Test {

    public static void main(String[] args) {
        ArbolGeneral<Boolean> a1, a2, a3, a4, a5, a6, a7, a8;

        a1 = new ArbolGeneral<>(Boolean.TRUE);
        a1.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a1.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));

        a2 = new ArbolGeneral<>(Boolean.FALSE);
        a2.agregarHijo(a1);

        a3 = new ArbolGeneral<>(Boolean.TRUE);
        a3.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a3.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));

        a4 = new ArbolGeneral<>(Boolean.TRUE);
        a4.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a4.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));

        a5 = new ArbolGeneral<>(Boolean.FALSE);
        a5.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a5.agregarHijo(a3);
        a5.agregarHijo(a4);

        a6 = new ArbolGeneral<>(Boolean.FALSE);
        a6.agregarHijo(a2);
        a6.agregarHijo(a5);

        a7 = new ArbolGeneral<>(Boolean.FALSE);
        a7.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a7.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));

        a8 = new ArbolGeneral<>(Boolean.TRUE);
        a8.agregarHijo(a6);
        a8.agregarHijo(new ArbolGeneral<>(Boolean.TRUE));
        a8.agregarHijo(a7);

        long time = System.currentTimeMillis();
        System.out.println(new Parcial().resolver(a8));
        long time2 = System.currentTimeMillis();
        long res = time2 - time;
        System.out.println("Resuelto en " + res);
    }
}
