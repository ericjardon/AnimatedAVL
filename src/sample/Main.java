package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.FXTree;

public class Main extends Application {
    private static int ancho = 1500;
    private static int alto = 1000;
    @Override
    public void start(Stage primaryStage) throws Exception{

        AVL<Integer> a = new AVL<>();
        primaryStage.setTitle("AVL Tree");
        FXTree<Integer> treePrinter = new FXTree<>();

        // Botones
        Button insertBtn =new Button("Insertar");
        TextField insertField = new TextField();
        Button deleteBtn = new Button("Eliminar");
        TextField deleteField = new TextField();

        //Boxes
        HBox insertionBox;
        HBox deletionBox;
        VBox menu;

        insertionBox = new HBox(8,insertBtn,insertField);
        deletionBox = new HBox(8,deleteBtn,deleteField);
        menu = new VBox(20,insertionBox,deletionBox);

        // Setting Layout Positions
        /*insertBtn.setLayoutX(30);
        insertBtn.setLayoutY(80);
        insertField.setLayoutX(110);
        insertField.setLayoutY(80);
        //VBox menuVBox = new VBox(10, , , );

        deleteBtn.setLayoutX(30);
        deleteBtn.setLayoutY(120);
        deleteField.setLayoutX(110);
        deleteField.setLayoutY(130);*/

        Group gp = new Group();
        gp.getChildren().addAll(treePrinter, menu);

        // EVENT HANDLERS
        // DELETE
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int input = Integer.parseInt(deleteField.getText());
                //si no lo encuentra
                if (a.findNode(input) == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alerta");
                    alert.setHeaderText("Error al eliminar elemento");
                    alert.setContentText(input + " no se encuentra en el árbol.");
                    alert.showAndWait();
                    deleteField.clear();
                } else {
                    treePrinter.getChildren().clear();
                    a.remove(input);
                    deleteField.clear();
                    treePrinter.imprimir(a.getRaiz());
                }
            }
            // idea: imprimir se apoya de un recursivo, podríamos indicarle la raíz como el nodopadre donde hubo modificación.
            // Así solo reimprime esa parte y la reanima.
        });
        // INSERT
        insertBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int input = Integer.parseInt(insertField.getText());
                    treePrinter.getChildren().clear();
                    a.insertar(input);
                    insertField.clear();
                    treePrinter.imprimir(a.getRaiz());
                    treePrinter.animarInsertion(a.findNode(input));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alerta");
                    alert.setHeaderText("Error al insertar elemento");
                    alert.setContentText("Valor ingresado inválido");
                    alert.showAndWait();
                    insertField.clear();
                }
            }
        });

        // SETTING UP SCROLLPANE
        ScrollPane sp = new ScrollPane();
        sp.setContent(gp);

        // ALL READY
        Scene main = new Scene(sp, ancho, alto);
        primaryStage.setScene(main);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
