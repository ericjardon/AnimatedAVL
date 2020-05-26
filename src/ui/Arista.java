package ui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import sample.Nodo;

public class Arista<T extends Comparable<T>> extends Line {
    private FXNodo padre;
    private FXNodo hijo;

    public Arista(Nodo<T> nodoPadre, Nodo<T> nodoHijo) {
        //double startX, double startY, double EndX, double EndY,
        super();
        this.padre = nodoPadre.getUi();
        this.hijo = nodoHijo.getUi();
    }
}
