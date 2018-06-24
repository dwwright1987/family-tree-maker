package com.wright.ftm.ui.nodes;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
import com.wright.ftm.ui.alerts.WarningAlert;
import com.wright.ftm.ui.controls.FamilyTreeMemberNode;
import com.wright.ftm.ui.dialogs.FamilyMemberDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static javafx.scene.paint.Color.WHITE;

public class FamilyTreeNode {
    private Button addFirstFamilyMemberButton = createAddFirstFamilyMemberButton();
    private VBox familyTreeBox = new VBox();
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
    private GridPane familyTreePane = createFamilyTreePane(familyTreeBox);
    private FamilyTreeDTO selectedFamilyTreeDTO;

    public VBox build() {
        familyTreeBox.setAlignment(Pos.CENTER);
        familyTreeBox.setPadding(new Insets(DEFAULT_PADDING));
        familyTreeBox.setSpacing(DEFAULT_PADDING);
        familyTreeBox.getChildren().addAll(familyTreePane, addFirstFamilyMemberButton);
        familyTreeBox.setVisible(false);

        return familyTreeBox;
    }

    private GridPane createFamilyTreePane(Pane parent) {
        GridPane gridPane = new GridPane();
        gridPane.prefHeightProperty().bind(parent.heightProperty());
        gridPane.prefWidthProperty().bind(parent.widthProperty());
        gridPane.setPadding(new Insets(DEFAULT_PADDING));
        gridPane.setHgap(DEFAULT_PADDING);
        gridPane.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setBorder(new Border(new BorderStroke(Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        return gridPane;
    }

    private Button createAddFirstFamilyMemberButton() {
        Button addFamilyMemberButton = new Button("Add First Family Member");
        addFamilyMemberButton.setOnMouseClicked(event -> showFamilyMemberDialog());

        return addFamilyMemberButton;
    }

    private void showFamilyMemberDialog() {
        FamilyMemberDialog familyMemberDialog = new FamilyMemberDialog(null, selectedFamilyTreeDTO);
        familyMemberDialog.showAndWait().ifPresent(familyTreeMemberDTO -> {
            FamilyTreeMemberDTO createdFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(familyTreeMemberDTO, selectedFamilyTreeDTO);
            if (createdFamilyTreeMemberDTO == null) {
                WarningAlert.show("Could not create family member");
            }
        });
    }

    private List<FamilyTreeMemberNode> createFamilyTreeMemberNodes(FamilyTreeDTO familyTreeDTO) {
        return getFamilyTreeMembers(familyTreeDTO).stream().map((FamilyTreeMemberDTO familyTreeMemberDTO) -> new FamilyTreeMemberNode(familyTreeMemberDTO, familyTreeDTO)).collect(Collectors.toList());
    }

    private List<FamilyTreeMemberDTO> getFamilyTreeMembers(FamilyTreeDTO familyTreeDTO) {
        return familyTreeMembersService.getFamilyMembers(familyTreeDTO);
    }

    public void setSelectedFamilyTreeDTO(FamilyTreeDTO familyTreeDTO) {
        selectedFamilyTreeDTO = familyTreeDTO;

        List<FamilyTreeMemberNode> familyTreeMemberNodes = createFamilyTreeMemberNodes(familyTreeDTO);

        familyTreePane.getChildren().clear();

        if (familyTreeMemberNodes.size() > 0) {
            familyTreePane.add(familyTreeMemberNodes.get(0), 0, 0);
            familyTreePane.add(familyTreeMemberNodes.get(1), 1, 0);
            addFirstFamilyMemberButton.setDisable(true);
        } else {
            addFirstFamilyMemberButton.setDisable(false);

        }
    }

    public void setVisible(boolean visible) {
        if (familyTreeBox != null) {
            familyTreeBox.setVisible(visible);
        }
    }
}
