package com.wright.ftm;

import com.wright.ftm.db.DbManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiMain extends Application {
    private DbManager dbManager = DbManager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        dbManager.startDb();

        primaryStage.setTitle("Family Tree Maker");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        dbManager.stopDb();

        super.stop();
    }
}
