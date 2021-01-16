package ro.mta.se.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.AppControler;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(this.getClass().getResource("/View/WeatherView.fxml"));
            loader.setController(new AppControler());

            primaryStage.setTitle("Weather App");
            primaryStage.setScene(new Scene(loader.load()));

            primaryStage.setOpacity(0.97);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
