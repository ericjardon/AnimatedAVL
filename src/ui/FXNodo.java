package ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import sample.Nodo;

public class FXNodo<T extends Comparable<T>> extends StackPane {

    public static int radius = 10;
    Nodo<T> nodo;
    DoubleProperty X;
    DoubleProperty Y;
    Circle circle;
    Text txt;

    public FXNodo(double centerX, double centerY, Nodo<T> n) {
        super();
        nodo = n;
        circle = new Circle(centerX, centerY, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        X = new SimpleDoubleProperty(centerX);
        Y = new SimpleDoubleProperty(centerY);
        this.txt = new Text(nodo.getElemento().toString());
        txt.setBoundsType(TextBoundsType.VISUAL);
        txt.setFill(Color.BLACK);
        setLayoutX(centerX - radius);
        setLayoutY(centerY - radius);
        this.getChildren().addAll(circle, txt);
    }

    public Circle getCircle() {
        return circle;
    }

    public double getX() {
        return X.get();
    }

    public DoubleProperty xProperty() {
        return X;
    }

    public double getY() {
        return Y.get();
    }

    public DoubleProperty yProperty() {
        return Y;
    }

    public Text getE() {
        return txt;
    }

}
