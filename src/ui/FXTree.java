package ui;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.Nodo;
import static java.lang.Math.abs;

public class FXTree<T extends Comparable<T>> extends Group {
    // impresora independiente de clase Árbol. Toma una raíz y la "imprime" con componentes de FX recursivamente,
    // guardando registro de las coordenadas referencia y usando añadiendo una diferencia para las coordenadas nuevas.

    // nueva implementación
    double addWidth = 20;
    double addHeight = 30;

    // Tamaño del grupo
    /*public FXTree(){
        // est-ce que c'est necessaire?
        this.prefHeight(800);
        this.prefWidth(800);
    }*/

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
        System.out.println(nodoPadre.getElemento() + " X:"+refX +"//  Y:"+ refY);  // debugging purposes
    }

    // idea: que el grupo de la impresión si guarde la nueva organización, pero no se despliegue aún, antes de desplegarlo, se hace la animación?
    // o desplegarlo e inmediatamente después hacer la animación.
    // la animación sería un método que toma los nodos a animar? superpuesto: para un nodo, para una línea y dos nodos.

    public void animarInsertion(Nodo<T> nodo) {
        TranslateTransition nodeAnimation = new TranslateTransition(Duration.seconds(1), nodo.getUi());
        nodeAnimation.setFromX(-(nodo.getUi().getX()));
        nodeAnimation.setFromY(-(nodo.getUi().getY()));
        nodeAnimation.setToX(0);
        nodeAnimation.setToY(0);
        if (nodo.getAristaAlPadre()!=null) {
            System.out.println("Sí entra");
            nodo.getAristaAlPadre().endXProperty().bind(nodo.getUi().xProperty().add(nodo.getUi().translateXProperty()));
            nodo.getAristaAlPadre().endYProperty().bind(nodo.getUi().yProperty().add(nodo.getUi().translateYProperty()));
        }
        nodeAnimation.setCycleCount(1);
        nodeAnimation.play();
    }

    /*        MÉTODOS   VIEJOS    */
    public void animateInsertion(Nodo<T> root, Nodo<T> child, double offset) {
        // crear arista con start en root y end en child.
        // animar para colocar donde deberían según las coordenadas de centro de cada uno
        double toX = root.getUi().getCircle().getCenterX() + offset;
        double toY = root.getUi().getCircle().getCenterY() + (20);
        //addNodeUi(child, toX,toY);
        Arista<T> line = new Arista<T>(root,child);
        this.getChildren().add(line);
        TranslateTransition textAnimation = new TranslateTransition(Duration.seconds(1), child.getUi().getE());
        textAnimation.setFromX(-toX);
        textAnimation.setFromY(-toY);
        textAnimation.setToX(0);
        textAnimation.setToY(0);      // corresponden a x,y de Layout o de Círculo?
        TranslateTransition c2Animation = new TranslateTransition(Duration.seconds(1), child.getUi().getCircle());
        c2Animation.setFromX(-toX);
        c2Animation.setFromY(-toY);
        c2Animation.setToX(0);
        c2Animation.setToY(0);
        /*line.startXProperty().bind(line.getC1().centerXProperty().add(line.getC1().translateXProperty()));
        line.startYProperty().bind(line.getC1().centerYProperty().add(line.getC1().translateYProperty()));
        line.endXProperty().bind(line.getC2().centerXProperty().add(line.getC2().translateXProperty()));
        line.endYProperty().bind(line.getC2().centerYProperty().add(line.getC2().translateYProperty()));*/
        c2Animation.setCycleCount(3);
        c2Animation.play();
        textAnimation.setCycleCount(3);
        textAnimation.play();
        //System.out.println("New Layout for (" + child.getElemento() + "): " + child.getUi().getLayoutX() + child.getUi().getLayoutY() );
        System.out.println("New Center for ("+ child.getElemento() + "): " + child.getUi().getCircle().getCenterX() + child.getUi().getCircle().getCenterY());
    }
}