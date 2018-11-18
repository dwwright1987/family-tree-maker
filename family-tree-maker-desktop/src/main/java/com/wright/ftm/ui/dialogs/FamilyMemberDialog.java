package com.wright.ftm.ui.dialogs;

import com.wright.ftm.dtos.FamilyTreeDTO;
import com.wright.ftm.dtos.FamilyTreeMemberDTO;
import com.wright.ftm.dtos.Sex;
import com.wright.ftm.ui.controls.FamilyTreeMakerControlEvenHandler;
import com.wright.ftm.ui.controls.FamilyTreeMakerDatePicker;
import com.wright.ftm.ui.controls.FamilyTreeMakerTextField;
import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import static com.wright.ftm.Constants.DEFAULT_PADDING;
import static com.wright.ftm.dtos.Sex.FEMALE;
import static com.wright.ftm.dtos.Sex.MALE;

public class FamilyMemberDialog extends Dialog<FamilyTreeMemberDTO> implements FamilyTreeMakerControlEvenHandler {
    private static final String FEMALE_SEX_OPTION = "Female";
    private static final String MALE_SEX_OPTION = "Male";
    private FamilyTreeMakerDatePicker birthDatePicker = new FamilyTreeMakerDatePicker(true, this);
    private FamilyTreeMakerDatePicker deathDatePicker = new FamilyTreeMakerDatePicker(false, this);
    private FamilyTreeMakerTextField firstNameTextField = new FamilyTreeMakerTextField(true, this);
    private FamilyTreeMakerTextField lastNameTextField = new FamilyTreeMakerTextField(true, this);
    private FamilyTreeMakerTextField middleNameTextField = new FamilyTreeMakerTextField(false, this);
    private TextArea notesTextArea = buildNotesTextArea();
    private ComboBox sexOptions = new ComboBox(FXCollections.observableArrayList(FEMALE_SEX_OPTION, MALE_SEX_OPTION));
    private double width = 290;
    private FamilyTreeMemberDTO existingFamilyTreeMemberDTO;

    public FamilyMemberDialog(FamilyTreeMemberDTO existingFamilyTreeMemberDTO, FamilyTreeDTO familyTreeDTO) {
        this.existingFamilyTreeMemberDTO = existingFamilyTreeMemberDTO;

        setHeight(475);
        setWidth(width);
        setTitle(this.existingFamilyTreeMemberDTO == null ? "Add Family Member" : "Modify Family Member");
        setDialogPane(buildPane(existingFamilyTreeMemberDTO, familyTreeDTO));
        setResultConverter(this::handleClose);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(stage);
        UiLocationUtils.center(stage);

        onFamilyTreeMakerControlEvent();
    }

    private TextArea buildNotesTextArea() {
        TextArea textArea = new TextArea();
        textArea.setMinHeight(100);
        textArea.setMaxWidth(225);
        return textArea;
    }

    private DialogPane buildPane(FamilyTreeMemberDTO familyTreeMemberDTO, FamilyTreeDTO familyTreeDTO) {
        VBox inputs = new VBox();
        inputs.getChildren().addAll(
            createSexInput(),
            createInput("First Name:", firstNameTextField.getTextField()),
            createInput("Middle Name:", middleNameTextField.getTextField()),
            createInput("Last Name:", lastNameTextField.getTextField()),
            createInput("Birth Date:", birthDatePicker.getDatePicker()),
            createInput("Death Date:", deathDatePicker.getDatePicker()),
            createInput("Notes:", notesTextArea)
        );

        if (familyTreeMemberDTO == null) {
            lastNameTextField.setText(familyTreeDTO.getName());
        } else {
            if (familyTreeMemberDTO.getSex() == FEMALE) {
                sexOptions.getSelectionModel().select(FEMALE_SEX_OPTION);
            } else {
                sexOptions.getSelectionModel().select(MALE_SEX_OPTION);
            }
            firstNameTextField.setText(familyTreeMemberDTO.getFirstName());
            middleNameTextField.setText(familyTreeMemberDTO.getMiddleName());
            lastNameTextField.setText(familyTreeMemberDTO.getLastName());
            birthDatePicker.setDate(familyTreeMemberDTO.getBirthDate());
            deathDatePicker.setDate(familyTreeMemberDTO.getDeathDate());
        }

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
            FamilyTreeMemberDTO familyTreeMemberDTO = existingFamilyTreeMemberDTO == null ? new FamilyTreeMemberDTO() : existingFamilyTreeMemberDTO;
            familyTreeMemberDTO.setSex(determineSex());
            familyTreeMemberDTO.setFirstName(firstNameTextField.getText());
            familyTreeMemberDTO.setMiddleName(middleNameTextField.getText());
            familyTreeMemberDTO.setLastName(lastNameTextField.getText());
            familyTreeMemberDTO.setBirthDate(birthDatePicker.getDate());
            familyTreeMemberDTO.setDeathDate(deathDatePicker.getDate());
            familyTreeMemberDTO.setNotes(notesTextArea.getText());

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

    public void onFamilyTreeMakerControlEvent() {
        if (allRequiredFieldFilledOut()) {
            setOkButtonDisabled(false);
        } else {
            setOkButtonDisabled(true);
        }
    }

    private boolean allRequiredFieldFilledOut() {
        return StringUtils.isNotEmpty(firstNameTextField.getText()) && StringUtils.isNotEmpty(lastNameTextField.getText()) && birthDatePicker.getDate() != null;
    }

    private void setOkButtonDisabled(boolean disabled) {
        Node okButton = getDialogPane().lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.setDisable(disabled);
        }
    }
}
