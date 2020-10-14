package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fXMLLoader;
        Parent root1= FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        primaryStage.setTitle("Supplement ");
        primaryStage.setScene(new Scene(root1, 750,600));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
