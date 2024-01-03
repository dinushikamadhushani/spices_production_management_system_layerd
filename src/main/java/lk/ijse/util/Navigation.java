package lk.ijse.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static Parent rootNode;
    private static Scene scene;
    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(Navigation.class.getResource("/view/" + path));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
       // stage.setTitle("dashboard");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchPaging(Pane pane, String path,String name) throws IOException {
//        pane.getChildren().clear();
//        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view" + path));
//        Parent root = loader.load();
//        pane.getChildren().add(root);

        Parent parent = FXMLLoader.load(Navigation.class.getResource("/view/"+path));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setTitle(name);
        stage.setScene(scene);
    }
    public static void ChangePane(AnchorPane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }
}
