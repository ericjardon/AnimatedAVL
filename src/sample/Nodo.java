package sample;

import javafx.scene.shape.Line;
import ui.FXNodo;

public class Nodo<T extends Comparable<T>> {

    private T elemento;
    private Nodo<T> izquierdo;
    private Nodo<T> derecho;
    private int altura;
    private FXNodo<T> ui;
    Line aristaAlPadre;

    public FXNodo<T> getUi() {
        return ui;
    }

    public void setUi(FXNodo<T> ui) {
        this.ui = ui;
    }

    public Nodo(T elemento) {
        this.elemento = elemento;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public boolean isLeaf() {
        return (this.izquierdo == null && this.derecho == null);
    }

    public Line getAristaAlPadre() {
        return aristaAlPadre;
    }

    public void setAristaAlPadre(Line aristaAlPadre) {
        this.aristaAlPadre = aristaAlPadre;
    }

    @Override
    public String toString() {
        return "Nodo: (" + this.elemento + ") altura = " + this.altura;
    }
}