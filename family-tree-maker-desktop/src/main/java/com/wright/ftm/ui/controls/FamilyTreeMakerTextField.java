package com.wright.ftm.ui.controls;

import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;

public class FamilyTreeMakerTextField extends FamilyTreeMakerControl {
    private TextField textField = createTextField();

    public FamilyTreeMakerTextField(boolean required, FamilyTreeMakerControlEvenHandler delegate) {
        super(required, delegate);
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setOnKeyReleased(event -> onControlAction(textField));

        updateBorder(textField);

        return textField;
    }

    protected boolean requirementsMet() {
        if (textField != null) {
            return !StringUtils.isEmpty(textField.getText());
        } else {
            return false;
        }
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
        onControlAction(textField);
    }

    public TextField getTextField() {
        return textField;
    }
}
