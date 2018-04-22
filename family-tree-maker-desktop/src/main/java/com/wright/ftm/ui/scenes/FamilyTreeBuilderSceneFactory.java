package com.wright.ftm.ui.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.wright.ftm.Constants.DEFAULT_PADDING;

public class FamilyTreeBuilderSceneFactory {
    public static Scene buildScene(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private static BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenuBar(primaryStage));
        borderPane.setLeft(createFamiliesPane());

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

    private static VBox createFamiliesPane() {
        ListView<String> familyTreeNamesView = new ListView<>();

        Button createFamilyTreeButton = new Button("Create Family Tree");

        VBox familiesBox = new VBox();
        familiesBox.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE , BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        familiesBox.setMinWidth(250);
        familiesBox.setAlignment(Pos.CENTER);
        familiesBox.setPadding(new Insets(DEFAULT_PADDING));
        familiesBox.setSpacing(DEFAULT_PADDING);
        familiesBox.getChildren().addAll(familyTreeNamesView, createFamilyTreeButton);

        familyTreeNamesView.prefHeightProperty().bind(familiesBox.heightProperty());

        return familiesBox;
    }
}
