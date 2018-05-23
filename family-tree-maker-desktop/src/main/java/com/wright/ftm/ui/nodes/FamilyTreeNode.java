package com.wright.ftm.ui.nodes;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
import com.wright.ftm.ui.alerts.WarningAlert;
import com.wright.ftm.ui.dialogs.AddFamilyMemberDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static javafx.scene.paint.Color.WHITE;

public class FamilyTreeNode {
    private VBox familyTreeBox;
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
    private FamilyTreeDTO selectedFamilyTreeDTO;

    public VBox build() {
        familyTreeBox = new VBox();
        familyTreeBox.setAlignment(Pos.CENTER);
        familyTreeBox.setPadding(new Insets(DEFAULT_PADDING));
        familyTreeBox.setSpacing(DEFAULT_PADDING);
        familyTreeBox.getChildren().addAll(createFamilyTreePane(familyTreeBox), createAddFamilyMemberButton());
        familyTreeBox.setVisible(false);

        return familyTreeBox;
    }

    private GridPane createFamilyTreePane(Pane parent) {
        GridPane gridPane = new GridPane();
        gridPane.prefHeightProperty().bind(parent.heightProperty());
        gridPane.prefWidthProperty().bind(parent.widthProperty());
        gridPane.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        return gridPane;
    }

    private Button createAddFamilyMemberButton() {
        Button addFamilyMemberButton = new Button("Add Family Member");
        addFamilyMemberButton.setOnMouseClicked(event -> addFamilyMemberDialog());

        return addFamilyMemberButton;
    }

    private void addFamilyMemberDialog() {
        AddFamilyMemberDialog addFamilyMemberDialog = new AddFamilyMemberDialog();
        addFamilyMemberDialog.showAndWait().ifPresent(familyTreeMemberDTO -> {
            FamilyTreeMemberDTO createdFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(familyTreeMemberDTO, selectedFamilyTreeDTO);
            if (createdFamilyTreeMemberDTO == null) {
                WarningAlert.show("Could not create family member");
            }
        });
    }

    public void setSelectedFamilyTreeDTO(FamilyTreeDTO familyTreeDTO) {
        selectedFamilyTreeDTO = familyTreeDTO;
    }

    public void setVisible(boolean visible) {
        if (familyTreeBox != null) {
            familyTreeBox.setVisible(visible);
        }
    }
}
