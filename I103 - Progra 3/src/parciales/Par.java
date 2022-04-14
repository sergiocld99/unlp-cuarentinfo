package parciales;

public class Par {
    private int datoIzq, datoDer;

    public Par(int datoIzq, int datoDer) {
        this.datoIzq = datoIzq;
        this.datoDer = datoDer;
    }

    public Par() {

    }

    public int getDatoIzq() {
        return datoIzq;
    }

    public void setDatoIzq(int datoIzq) {
        this.datoIzq = datoIzq;
    }

    public int getDatoDer() {
        return datoDer;
    }

    public void setDatoDer(int datoDer) {
        this.datoDer = datoDer;
    }

    @Override
    public String toString() {
        return "("+getDatoIzq()+", "+getDatoDer()+")";
    }
}
