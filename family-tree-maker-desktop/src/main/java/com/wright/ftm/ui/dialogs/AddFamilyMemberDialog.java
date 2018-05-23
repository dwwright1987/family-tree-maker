package com.wright.ftm.ui.dialogs;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.dtos.Sex;
import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;

public class AddFamilyMemberDialog extends Dialog<FamilyTreeMemberDTO> {
    private static final String FEMALE_SEX_OPTION = "Female";
    private static final String MALE_SEX_OPTION = "Male";
    private TextField firstNameTextField = createTextField(true);
    private TextField middleNameTextField = createTextField(false);
    private TextField lastNameTextField = createTextField(true);
    private ComboBox sexOptions = new ComboBox(FXCollections.observableArrayList(FEMALE_SEX_OPTION, MALE_SEX_OPTION));
    private double width = 300;

    public AddFamilyMemberDialog(FamilyTreeDTO familyTreeDTO) {
        setHeight(220);
        setWidth(width);
        setTitle("Add Family Member");
        setDialogPane(buildPane(familyTreeDTO));
        setResultConverter(this::handleClose);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(stage);
        UiLocationUtils.center(stage);

        setOkButtonDisabled(true);
    }

    private DialogPane buildPane(FamilyTreeDTO familyTreeDTO) {
        VBox inputs = new VBox();
        inputs.getChildren().addAll(
            createSexInput(),
            createInput("First Name:", firstNameTextField),
            createInput("Middle Name:", middleNameTextField),
            createInput("Last Name:", lastNameTextField)
        );

        lastNameTextField.setText(familyTreeDTO.getName());
        updateTextFieldBorder(lastNameTextField, true);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setPrefHeight(getHeight());
        dialogPane.getChildren().add(inputs);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        return dialogPane;
    }

    private HBox createSexInput() {
        sexOptions.getSelectionModel().selectFirst();
        return createInput("Sex:", sexOptions);
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
            familyTreeMemberDTO.setFirstName(firstNameTextField.getText());
            familyTreeMemberDTO.setMiddleName(middleNameTextField.getText());
            familyTreeMemberDTO.setLastName(lastNameTextField.getText());

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

    private TextField createTextField(boolean required) {
        TextField textField = new TextField();
        textField.setOnKeyReleased(event -> {
            updateTextFieldBorder(textField, required);
            updateOkButtonState();
        });

        if (required) {
            textField.setBorder(createTextFieldBorder(Color.RED));
        }

        return textField;
    }

    private void updateTextFieldBorder(TextField textField, boolean required) {
        if (StringUtils.isEmpty(textField.getText()) && required) {
            textField.setBorder(createTextFieldBorder(Color.RED));
        } else {
            textField.setBorder(createTextFieldBorder(Color.DIMGRAY));
        }
    }

    private Border createTextFieldBorder(Paint color) {
        return new Border(new BorderStroke(color, color, color, color, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT, Insets.EMPTY));
    }

    private void updateOkButtonState() {
        if (StringUtils.isNotEmpty(firstNameTextField.getText()) && StringUtils.isNotEmpty(lastNameTextField.getText())) {
            setOkButtonDisabled(false);
        } else {
            setOkButtonDisabled(true);
        }
    }

    private void setOkButtonDisabled(boolean disabled) {
        getDialogPane().lookupButton(ButtonType.OK).setDisable(disabled);
    }
}
