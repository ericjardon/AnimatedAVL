package ui;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.AVL;
import sample.Nodo;

import javax.swing.*;

public class FXTree<T extends Comparable<T>> extends Group {
    // impresora independiente de clase Árbol. Toma una raíz y la "imprime" con componentes de FX recursivamente,
    // guardando registro de las coordenadas referencia y usando añadiendo una diferencia para las coordenadas nuevas.

    public AVL arbol;
    // nueva implementación
    double addWidth = 20;
    double addHeight = 30;

    // Tamaño del grupo
    /*public FXTree(){
        // est-ce que c'est necessaire?
        this.prefHeight(800);
        this.prefWidth(800);
    }*/
    public FXTree(AVL arbol) {
        super();
        this.arbol = arbol;
    }

    /*        MÉTODOS   NUEVOS    */
    public void imprimir(Nodo<T> raiz) {
        // coordenadas iniciales de raíz: 600,100, separación horizontal será 50
        imprimirRec(raiz,750,100);
        //idea: crecer el offset conforme crece altura?
    }

    public void imprimirRec(Nodo<T> nodoPadre, double refX, double refY) {
        // base recursiva:
        if (nodoPadre == null) {
            return;
        }
        double xOffset = (Math.pow(2,nodoPadre.getAltura())*15);
        double yOffset = (Math.pow(1.5,nodoPadre.getAltura())*20);
        // IMPRIMIR LADO DERECHO
        imprimirRec(nodoPadre.getDerecho(), refX+xOffset, refY+yOffset);
        //dibuja arista si hijo no es vacío
        if(nodoPadre.getDerecho()!=null && nodoPadre.getDerecho().getElemento().compareTo(nodoPadre.getElemento())>0) {
            Line arista = new Line(refX, refY, refX+xOffset-10, refY+yOffset-10); // coordenadas del nodo actual y las del siguiente
            arista.setStrokeWidth(3);
            this.getChildren().add(arista);
            nodoPadre.getDerecho().setAristaAlPadre(arista);

            /*Circle punta = new Circle(refX+xOffset-10,refY+yOffset-10, 1);
            punta.setStrokeWidth(0.0);
            this.getChildren().add(punta);
            TranslateTransition puntaAnimation = new TranslateTransition(Duration.seconds(1), punta);
            puntaAnimation.setFromX(-(refX+xOffset-10));
            puntaAnimation.setFromY(-(refY+yOffset-10));
            puntaAnimation.setToX(0);
            puntaAnimation.setToY(0);
            arista.endXProperty().bind(punta.centerXProperty().add(punta.translateXProperty()));
            arista.endYProperty().bind(punta.centerYProperty().add(punta.translateYProperty()));
            puntaAnimation.setCycleCount(1);
            puntaAnimation.play();*/
        }

        // IMPRIMIR LADO IZQUIERDO
        imprimirRec(nodoPadre.getIzquierdo(),refX-xOffset, refY+yOffset);
        //dibuja arista si hijo no es vacío
        if(nodoPadre.getIzquierdo()!=null && nodoPadre.getIzquierdo().getElemento().compareTo(nodoPadre.getElemento())<0) {
            Line arista = new Line(refX, refY, refX-xOffset+10, refY+yOffset-10); // coordenadas del nodo actual y las del siguiente
            arista.setStrokeWidth(3);
            this.getChildren().add(arista);
            nodoPadre.getIzquierdo().setAristaAlPadre(arista);

            /*Circle punta = new Circle(refX-xOffset+10,refY+yOffset-10, 1);
            punta.setStrokeWidth(0.0);
            this.getChildren().add(punta);
            TranslateTransition puntaAnimation = new TranslateTransition(Duration.seconds(1), punta);
            puntaAnimation.setFromX(-(refX-xOffset+10));
            puntaAnimation.setFromY(-(refY+yOffset-10));
            puntaAnimation.setToX(0);
            puntaAnimation.setToY(0);
            arista.endXProperty().bind(punta.centerXProperty().add(punta.translateXProperty()));
            arista.endYProperty().bind(punta.centerYProperty().add(punta.translateYProperty()));
            puntaAnimation.setCycleCount(1);
            puntaAnimation.play();*/
        }

        // IMPRIMIR NODO PADRE (Nodo FX?)
        FXNodo<T> n = new FXNodo<>(refX,refY,nodoPadre);
        this.getChildren().add(n);    // se añade el componente raíz del Ui
        nodoPadre.setUi(n);
        /*TranslateTransition nodeAnimation = new TranslateTransition(Duration.seconds(1), n);
        nodeAnimation.setFromX(-refX);
        nodeAnimation.setFromY(-refY);
        nodeAnimation.setToX(0);
        nodeAnimation.setToY(0);
        nodeAnimation.setCycleCount(1);
        nodeAnimation.play();*/

        System.out.println("----------");
        System.out.println(nodoPadre.getElemento() + " ("+refX +", "+ refY+")");  // debugging purposes
    }

    public void animarInsertion(Nodo<T> nodo) {
        TranslateTransition nodeAnimation = new TranslateTransition(Duration.seconds(1), nodo.getUi());
        nodeAnimation.setFromX(-(nodo.getUi().getX()));
        nodeAnimation.setFromY(-(nodo.getUi().getY()));
        nodeAnimation.setToX(0);
        nodeAnimation.setToY(0);
        if (nodo.getAristaAlPadre()!=null) {
            nodo.getAristaAlPadre().endXProperty().bind(nodo.getUi().xProperty().add(nodo.getUi().translateXProperty()));
            nodo.getAristaAlPadre().endYProperty().bind(nodo.getUi().yProperty().add(nodo.getUi().translateYProperty()));
        }
        nodeAnimation.setCycleCount(1);
        nodeAnimation.play();
    }

    public void animarDeletion(Nodo<T> nodo) {
        TranslateTransition nodeAnimation = new TranslateTransition(Duration.seconds(1), nodo.getUi());
        nodeAnimation.setByX(-(nodo.getUi().getX()+15));
        nodeAnimation.setByY(-(nodo.getUi().getY())+15);
        nodeAnimation.setCycleCount(1);
        nodeAnimation.play();
        nodeAnimation.setOnFinished(new EliminaEvent(this.getChildren(), nodo.getElemento()));
        this.getChildren().remove(nodo.getAristaAlPadre());
    }

    public void insertar(T input) {
        this.getChildren().clear();
        arbol.insertar(input);
        imprimir(arbol.getRaiz());
        animarInsertion(arbol.findNode(input));
    }

    public void eliminar(T input) {
        animarDeletion(arbol.findNode(input));
    }

    class EliminaEvent implements EventHandler<ActionEvent>{
        ObservableList<Node> children;
        T input;

        public EliminaEvent(ObservableList<Node> children, T input) {
            this.children = children;
            this.input = input;
        }
        @Override
        public void handle(ActionEvent event) {
            children.clear();
            arbol.remove(input);
            imprimir(arbol.getRaiz());
        }
    }
}