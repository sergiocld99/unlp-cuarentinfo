package prog3.arbol.general.usos;

import prog3.arbol.general.ArbolGeneral;
import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Imagen {
    private boolean[][] imagen;

    public Imagen(int n){
        this.imagen = new boolean[n][n];
    }

    private Imagen(Imagen img, int n){
        int xi, xf, yi, yf;

        switch (n){
            case 0:{
                xi = yi = 0;
                xf = yf = img.dimension() / 2;
                break;
            }
            case 1:{
                yi = 0;
                yf = xi = img.dimension() / 2;
                xf = img.dimension();
                break;
            }
            case 2:{
                xi = 0;
                xf = yi = img.dimension() / 2;
                yf = img.dimension();
                break;
            }
            default:{
                xi = yi = img.dimension() / 2;
                xf = yf = img.dimension();
                break;
            }
        }

        this.imagen = new boolean[xf-xi][yf-yi];

        for (int x=xi; x<xf; x++)
            for (int y=yi; y<yf; y++)
                if (img.get(x,y)) this.set(x-xi, y-yi);
    }

    public boolean igualColor(){
        boolean color = this.get(0,0);
        for (boolean[] b : this.imagen)
            for (boolean c : b)
                if (c != color) return false;

        return true;
    }

    public boolean color(){
        return this.get(0,0);
    }

    // PRECONDICION: LA IMAGEN ES CUADRADA
    public ListaGenerica<Imagen> dividirEnSubImagenes(){
        ListaGenerica<Imagen> ret = new ListaGenericaEnlazada<>();

        for (int i=0; i<4; i++) ret.agregarFinal(new Imagen(this, i));
        return ret;
    }

    public ArbolGeneral<Boolean> imagenComprimida(){
        if (this.igualColor()) return new ArbolGeneral<>(this.color());

        ArbolGeneral<Boolean> ret = new ArbolGeneral<>();
        ListaGenerica<Imagen> subs = this.dividirEnSubImagenes();
        subs.comenzar();

        while (!subs.fin()) ret.agregarHijo(subs.proximo().imagenComprimida());
        return ret;
    }

    public int dimension(){
        return this.imagen.length;
    }

    public void set(int x, int y){
        this.imagen[x][y] = true;
    }

    public boolean get(int x, int y){
        return this.imagen[x][y];
    }
}
