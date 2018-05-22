package com.wright.ftm.ui.dialogs;

import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.dtos.Sex;
import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;

public class AddFamilyMemberDialog extends Dialog<FamilyTreeMemberDTO> {
    private static final String FEMALE_SEX_OPTION = "Female";
    private static final String MALE_SEX_OPTION = "Male";
    private TextField firstNameTextField = createRequiredTextField();
    private ComboBox sexOptions = new ComboBox(FXCollections.observableArrayList(FEMALE_SEX_OPTION, MALE_SEX_OPTION));
    private double width = 300;

    public AddFamilyMemberDialog() {
        setHeight(200);
        setWidth(width);
        setTitle("Add Family Member");
        setDialogPane(buildPane());
        setResultConverter(this::handleClose);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(stage);
        UiLocationUtils.center(stage);
    }

    private DialogPane buildPane() {
        VBox inputs = new VBox();
        inputs.getChildren().addAll(
            createSexInput(),
            createFirstNameInput()
        );

        DialogPane dialogPane = new DialogPane();
        dialogPane.setPrefHeight(getHeight());
        dialogPane.getChildren().add(inputs);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.lookupButton(ButtonType.OK).setDisable(true);

        return dialogPane;
    }

    private HBox createSexInput() {
        sexOptions.getSelectionModel().selectFirst();
        return createInput("Sex:", sexOptions);
    }

    private HBox createFirstNameInput() {
        return createInput("First Name:", firstNameTextField);
    }

    private HBox createInput(String labelText, Control control) {
        HBox input = new HBox();
        input.setMinWidth(width);
        input.setPadding(new Insets(DEFAULT_PADDING));
        input.getChildren().addAll(createLabel(labelText), control);

        return input;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPadding(new Insets(0, DEFAULT_PADDING, 0, 0));

        return label;
    }

    private FamilyTreeMemberDTO handleClose(ButtonType response) {
        if (response == ButtonType.OK) {
            FamilyTreeMemberDTO familyTreeMemberDTO = new FamilyTreeMemberDTO();
            familyTreeMemberDTO.setSex(determineSex());

            return familyTreeMemberDTO;
        } else {
            return null;
        }
    }

    private Sex determineSex() {
        if (FEMALE_SEX_OPTION.equals(sexOptions.getValue())) {
            return FEMALE;
        } else {
            return MALE;
        }
    }

    private TextField createRequiredTextField() {
        return new TextField();
    }
}
