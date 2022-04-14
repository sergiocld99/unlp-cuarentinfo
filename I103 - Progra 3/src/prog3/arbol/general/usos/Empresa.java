package prog3.arbol.general.usos;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;
import prog3.util.ColaGenerica;

public class Empresa {
    private ArbolGeneral<Empleado> empleados;

    // TODO: CATEGORY = LEVEL
    public int empleadosPorCategoria(int categoria){
        if (categoria < 1) return -1;

        ColaGenerica<ArbolGeneral<Empleado>> cola = new ColaGenerica<>();
        ListaGenerica<ArbolGeneral<Empleado>> hijos;
        ArbolGeneral<Empleado> ab;
        int cant, lev;

        cola.encolar(this.empleados);
        cant = lev = 1;

        while (!cola.esVacia()){
            ab = cola.desencolar();
            if (ab != null){
                if (lev == categoria) cant++;
                else {
                    hijos = ab.getHijos();
                    hijos.comenzar();
                    while (!hijos.fin()) cola.encolar(hijos.proximo());
                }
            } else if (!cola.esVacia()){
                cola.encolar(null);
                lev++;
            }
        }

        return cant;
    }


    public int categoriaConMasEmpleados(){
        ColaGenerica<ArbolGeneral<Empleado>> cola = new ColaGenerica<>();
        ListaGenerica<ArbolGeneral<Empleado>> hijos;
        ArbolGeneral<Empleado> ab;
        int max, ret, cant, lev = 1;

        cola.encolar(this.empleados);
        max = ret = cant = 0;

        while (!cola.esVacia()){
            ab = cola.desencolar();
            if (ab != null){
                hijos = ab.getHijos();
                hijos.comenzar();
                while (!hijos.fin()) cola.encolar(hijos.proximo());
                cant++;
            } else {
                if (cant > max){
                    max = cant;
                    ret = lev;
                }
                if (!cola.esVacia()){
                    cola.encolar(null);
                    cant = 0;
                    lev++;
                }
            }
        }

        return ret;
    }


    public int cantidadTotalDeEmpleados(){
        if (this.empleados == null) return -1;
        else return contarNodos(this.empleados);
    }

    private int contarNodos(ArbolGeneral<Empleado> ab){
        if (ab.esVacio()) return 0;
        if (ab.esHoja()) return 1;
        int cant = 1;
        
        ListaGenerica<ArbolGeneral<Empleado>> hijos = ab.getHijos();
        hijos.comenzar();
        while (!hijos.fin()) cant += contarNodos(hijos.proximo());
        return cant;
    }


    public void reemplazarPresidente(){
        ArbolGeneral<Empleado> older, tree = this.empleados;
        if (tree == null || tree.esVacio()) return;
        if (tree.esHoja()) this.empleados = null;
        else do {
            older = hijoConMasAntiguedad(tree);
            update(tree.getDatoRaiz(), older.getDatoRaiz());
            if (older.esHoja()) tree.eliminarHijo(older);
            else tree = older;
        } while (!older.esHoja());
    }

    // PRECONDICIÓN: AB NO ES UNA HOJA
    private ArbolGeneral<Empleado> hijoConMasAntiguedad(ArbolGeneral<Empleado> ab){
        ListaGenerica<ArbolGeneral<Empleado>> hijos = ab.getHijos();
        ArbolGeneral<Empleado> aux, older;

        hijos.comenzar();
        older = hijos.proximo();
        while (!hijos.fin()) {
            aux = hijos.proximo();
            if (aux.getDatoRaiz().getAntiguedad() > older.getDatoRaiz().getAntiguedad())
                older = aux;
        }

        return older;
    }

    private void update(Empleado ant, Empleado act){
        ant.setNombre(act.getNombre());
        ant.setAntiguedad(act.getAntiguedad());
        ant.setCategoria(act.getCategoria());
    }
}
