package com.wright.ftm;

import javafx.application.Application;
import javafx.stage.Stage;

public class GuiMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Family Tree Maker");

        primaryStage.show();
    }
}
