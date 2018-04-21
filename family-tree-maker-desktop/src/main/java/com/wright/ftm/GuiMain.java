package com.wright.ftm;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.services.LoggerConfigurationService;
import com.wright.ftm.stages.FatalAlert;
import com.wright.ftm.stages.StageUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiMain extends Application {
    private static Logger logger;
    private DbManager dbManager = DbManager.getInstance();

    public static void main(String[] args) {
        LoggerConfigurationService loggerConfigurationService = new LoggerConfigurationService();
        loggerConfigurationService.configure();

        getLogger().info("**********************************************");
        getLogger().info("********* Family Tree Maker Desktop **********");
        getLogger().info("**********************************************");

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        getLogger().info("Starting app...");
        boolean databaseSuccessfullyStarted = dbManager.startDb();

        primaryStage.setHeight(700);
        primaryStage.setWidth(1300);
        primaryStage.setTitle("Family Tree Maker");

        StageUtils.center(primaryStage);

        primaryStage.show();

        if (!databaseSuccessfullyStarted) {
            FatalAlert.show(primaryStage, "Failed to load data!");
        }
        getLogger().info("App started");
    }

    public void stop() throws Exception {
        getLogger().info("Stopping app...");
        dbManager.stopDb();

        super.stop();
        getLogger().info("App stopped");
    }

    private static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(GuiMain.class);
        }

        return logger;
    }
}
