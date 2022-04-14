package prog3.grafos.util;

public class Costo {
    private int costoMin;
    private int posVerticePrevio;

    public Costo() {
    }

    public Costo(int costoMin, int posVerticePrevio) {
        this.costoMin = costoMin;
        this.posVerticePrevio = posVerticePrevio;
    }

    public int getCostoMin() {
        return costoMin;
    }

    public void setCostoMin(int costoMin) {
        this.costoMin = costoMin;
    }

    public int getPosVerticePrevio() {
        return posVerticePrevio;
    }

    public void setPosVerticePrevio(int posVerticePrevio) {
        this.posVerticePrevio = posVerticePrevio;
    }

    @Override
    public String toString() {
        return this.getCostoMin() + " pasando previamente por vértice " + this.getPosVerticePrevio();
    }
}
