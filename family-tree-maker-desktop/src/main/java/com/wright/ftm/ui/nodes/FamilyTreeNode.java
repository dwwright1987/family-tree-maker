package com.wright.ftm.ui.nodes;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static javafx.scene.paint.Color.WHITE;

public class FamilyTreeNode {
    public static VBox build() {
        VBox familyTreeBox = new VBox();
        familyTreeBox.getChildren().addAll(createFamilyTreePane(familyTreeBox));

        return familyTreeBox;
    }

    private static GridPane createFamilyTreePane(Pane parent) {
        Insets gridPaneInsets = new Insets(DEFAULT_PADDING);

        GridPane gridPane = new GridPane();
        gridPane.prefHeightProperty().bind(parent.heightProperty());
        gridPane.prefWidthProperty().bind(parent.widthProperty());
        gridPane.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, gridPaneInsets)));
        gridPane.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, gridPaneInsets)));

        return gridPane;
    }
}
