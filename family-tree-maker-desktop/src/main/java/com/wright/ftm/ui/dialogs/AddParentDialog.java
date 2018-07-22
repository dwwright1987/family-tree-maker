package com.wright.ftm.ui.dialogs;

import com.wright.ftm.Constants;
import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.services.FamilyTreeMembersService;
import com.wright.ftm.ui.alerts.WarningAlert;
import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddParentDialog extends Dialog<Object> {
    private static final int BUTTON_WIDTH = 155;
    private FamilyTreeMembersService familyTreeMembersService = new FamilyTreeMembersService();
    private FamilyTreeDTO familyTreeDTO;

    public AddParentDialog(FamilyTreeDTO familyTreeDTO, FamilyTreeMemberDTO child) {
        this.familyTreeDTO = familyTreeDTO;

        setTitle("Add Parent");
        setHeight(115);
        setWidth(BUTTON_WIDTH + Constants.DEFAULT_PADDING * 2);
        setDialogPane(buildPane());
        setResultConverter(this::handleClose);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(stage);
        UiLocationUtils.center(stage);
    }

    private DialogPane buildPane() {
        VBox addOptions = new VBox();
        addOptions.setPadding(new Insets(Constants.DEFAULT_PADDING));
        addOptions.setSpacing(Constants.DEFAULT_PADDING);
        addOptions.getChildren().add(createCreateNewParentButton());
        addOptions.getChildren().add(createSearchForExistingParentButton());

        DialogPane dialogPane = new DialogPane();
        dialogPane.setPrefHeight(getHeight());
        dialogPane.setPrefWidth(getWidth());
        dialogPane.getChildren().add(addOptions);
        dialogPane.getButtonTypes().addAll(ButtonType.CANCEL);

        return dialogPane;
    }

    private Object handleClose(ButtonType response) {
        return null;
    }

    private Button createSearchForExistingParentButton() {
        Button button = new Button("Search For Existing Parent");
        button.setMinWidth(BUTTON_WIDTH);
        return button;
    }

    private Button createCreateNewParentButton() {
        Button button = new Button("Create New Parent");
        button.setMinWidth(BUTTON_WIDTH);
        button.setOnMouseClicked(event -> showFamilyMemberDialog());

        return button;
    }

    private void showFamilyMemberDialog() {
        FamilyMemberDialog familyMemberDialog = new FamilyMemberDialog(null, familyTreeDTO);
        familyMemberDialog.showAndWait().ifPresent(familyTreeMemberDTO -> {
            FamilyTreeMemberDTO createdFamilyTreeMemberDTO = familyTreeMembersService.createFamilyMember(familyTreeMemberDTO, familyTreeDTO);
            if (createdFamilyTreeMemberDTO == null) {
                WarningAlert.show("Could not create parent");
            }
        });
    }
}
