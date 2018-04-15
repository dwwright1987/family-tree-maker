package com.wright.ftm;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.stages.FatalAlert;
import com.wright.ftm.stages.StageUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiMain extends Application {
    private DbManager dbManager = DbManager.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        boolean databaseSuccessfullyStarted = dbManager.startDb();

        primaryStage.setHeight(700);
        primaryStage.setWidth(1300);
        primaryStage.setTitle("Family Tree Maker");

        StageUtils.center(primaryStage);

        primaryStage.show();

        if (!databaseSuccessfullyStarted) {
            FatalAlert.show(primaryStage, "Failed to load data!");
        }
    }

    @Override
    public void stop() throws Exception {
        dbManager.stopDb();

        super.stop();
    }
}
