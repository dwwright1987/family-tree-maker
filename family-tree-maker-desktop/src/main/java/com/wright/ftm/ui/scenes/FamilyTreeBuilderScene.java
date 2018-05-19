package com.wright.ftm.ui.scenes;

import com.wright.ftm.ui.nodes.FamiliesNode;
import com.wright.ftm.ui.nodes.FamilyTreeMakerMenuBarNode;
import com.wright.ftm.ui.nodes.FamilyTreeNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FamilyTreeBuilderScene {
    public static Scene build(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private static BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(FamilyTreeMakerMenuBarNode.build(primaryStage));
        borderPane.setLeft(FamiliesNode.build());
        borderPane.setCenter(FamilyTreeNode.build());

        return borderPane;
    }
}
