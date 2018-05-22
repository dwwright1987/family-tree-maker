package com.wright.ftm.ui.scenes;

import com.wright.ftm.ui.nodes.FamiliesNode;
import com.wright.ftm.ui.nodes.FamilyTreeMakerMenuBarNode;
import com.wright.ftm.ui.nodes.FamilyTreeNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FamilyTreeBuilderScene {
    private FamilyTreeNode familyTreeNode = new FamilyTreeNode();
    private FamiliesNode familiesNode = new FamiliesNode(familyTreeNode);

    public Scene build(Stage primaryStage) {
        return new Scene(createBorderPane(primaryStage));
    }

    private BorderPane createBorderPane(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(FamilyTreeMakerMenuBarNode.build(primaryStage));
        borderPane.setLeft(familiesNode.build());
        borderPane.setCenter(familyTreeNode.build());

        return borderPane;
    }
}
