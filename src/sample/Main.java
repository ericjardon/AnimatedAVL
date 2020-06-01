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
        FXTree<Integer> treePrinter = new FXTree<>(a);

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

        Group gp = new Group();
        gp.getChildren().addAll(treePrinter, menu);

        // EVENT HANDLERS
        // INSERT
        insertBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int input = Integer.parseInt(insertField.getText());
                    if (a.findNode(input) != null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alerta");
                        alert.setHeaderText("Elemento duplicado");
                        alert.setContentText(input + " ya se encuentra en el árbol.");
                        alert.showAndWait();
                    } else {
                        insertField.clear();
                        treePrinter.insertar(input);
                    }
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

        // DELETE
        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int input = Integer.parseInt(deleteField.getText());
                //si no lo encuentra
                if (a.findNode(input) == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alerta");
                    alert.setHeaderText("Elemento inexistente.");
                    alert.setContentText(input + " no se encuentra en el árbol.");
                    alert.showAndWait();
                    deleteField.clear();
                } else {
                    deleteField.clear();
                    treePrinter.eliminar(input);
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
