package scaleGeneratorUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scaleGeneratorUI.fxml"));
        primaryStage.setTitle("Scale Generator");
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("resources/css/jfoenix-fonts.css");
        scene.getStylesheets().add("resources/css/jfoenix-design.css");
        primaryStage.setMinWidth(475);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
