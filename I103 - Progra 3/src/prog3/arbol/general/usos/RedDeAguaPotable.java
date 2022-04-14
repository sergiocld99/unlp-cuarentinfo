package prog3.arbol.general.usos;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;

public class RedDeAguaPotable {

    public double caudalMinimo(ArbolGeneral<Double> config, double litros){
        if (config == null || config.esVacio()) return litros;
        double act, min = litros;

        ListaGenerica<ArbolGeneral<Double>> subs = config.getHijos();
        subs.comenzar();

        while (!subs.fin()){
            act = caudalMinimo(subs.proximo(), litros / subs.tamanio());
            if (act < min) min = act;
        }

        return min;
    }
}
