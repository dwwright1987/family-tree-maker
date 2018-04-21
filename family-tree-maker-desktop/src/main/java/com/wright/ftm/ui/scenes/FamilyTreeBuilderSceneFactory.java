package com.wright.ftm.ui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FamilyTreeBuilderSceneFactory {
    public static Scene buildScene(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private static BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenuBar(primaryStage));

        return borderPane;
    }

    private static MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(primaryStage));

        return menuBar;
    }

    private static Menu createFileMenu(Stage primaryStage) {
        Menu menu = new Menu("File");
        menu.getItems().addAll(createExportMenuItem(), new SeparatorMenuItem(), createExitMenuItem(primaryStage));

        return menu;
    }

    private static MenuItem createExportMenuItem() {
        return new MenuItem("Export");
    }

    private static MenuItem createExitMenuItem(Stage primaryStage) {
        MenuItem menuItem = new MenuItem("Exit");
        menuItem.setOnAction(event -> {
            primaryStage.close();
        });

        return menuItem;
    }
}
