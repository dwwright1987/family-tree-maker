package com.wright.ftm.ui.scenes;

import com.wright.ftm.ui.nodes.FamiliesVBox;
import com.wright.ftm.ui.nodes.FamilyTreeMakerMenuBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FamilyTreeBuilderScene {
    public static Scene build(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private static BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(FamilyTreeMakerMenuBar.build(primaryStage));
        borderPane.setLeft(FamiliesVBox.build());

        return borderPane;
    }
}
