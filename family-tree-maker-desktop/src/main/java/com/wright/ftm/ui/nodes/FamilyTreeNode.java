package com.wright.ftm.ui.nodes;

import com.wright.ftm.ui.dialogs.AddFamilyMemberDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static javafx.scene.paint.Color.WHITE;

public class FamilyTreeNode {
    public static VBox build() {
        VBox familyTreeBox = new VBox();
        familyTreeBox.setAlignment(Pos.CENTER);
        familyTreeBox.setPadding(new Insets(DEFAULT_PADDING));
        familyTreeBox.setSpacing(DEFAULT_PADDING);
        familyTreeBox.getChildren().addAll(createFamilyTreePane(familyTreeBox), createAddFamilyMemberButton());

        return familyTreeBox;
    }

    private static GridPane createFamilyTreePane(Pane parent) {
        GridPane gridPane = new GridPane();
        gridPane.prefHeightProperty().bind(parent.heightProperty());
        gridPane.prefWidthProperty().bind(parent.widthProperty());
        gridPane.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        return gridPane;
    }

    private static Button createAddFamilyMemberButton() {
        Button addFamilyMemberButton = new Button("Add Family Member");
        addFamilyMemberButton.setOnMouseClicked(event -> addFamilyMemberDialog());

        return addFamilyMemberButton;
    }

    private static void addFamilyMemberDialog() {
//        Dialog dialog = new Dialog();
//        dialog.setHeight(100);
//        dialog.setWidth(300);
//        dialog.setTitle("Add Family Member");
//
//        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
//        GraphicsUtils.addAppIcon(stage);
//        UiLocationUtils.center(stage);
//
////        dialog.setOnCloseRequest(event -> {
////            dialog.close();
////        });
//        dialog.showAndWait().ifPresent(response -> {
////            if (response == ButtonType.OK) {
////                owningStage.close();
////            }
//            dialog.close();
//        });
        AddFamilyMemberDialog addFamilyMemberDialog = new AddFamilyMemberDialog();
        addFamilyMemberDialog.showAndWait().ifPresent(response -> {

        });
    }
}
