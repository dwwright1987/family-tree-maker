package com.wright.ftm.ui.controls;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.lang.StringUtils;

public class FamilyTreeMakerTextField {
    private TextFieldActionHandler delegate;
    private boolean required;
    private TextField textField;

    public FamilyTreeMakerTextField(boolean required, TextFieldActionHandler delegate) {
        this.delegate = delegate;
        this.required = required;
        textField = createTextField(required);
    }

    private TextField createTextField(boolean isRequired) {
        TextField textField = new TextField();
        textField.setOnKeyReleased(event -> onTextUpdate());

        if (isRequired) {
            textField.setBorder(createTextFieldBorder(Color.RED));
        }

        return textField;
    }

    private void onTextUpdate() {
        updateTextFieldBorder();
        if (delegate != null) {
            delegate.onTextFieldKeyReleased();
        }
    }

    private void updateTextFieldBorder() {
        if (StringUtils.isEmpty(textField.getText()) && required) {
            textField.setBorder(createTextFieldBorder(Color.RED));
        } else {
            textField.setBorder(createTextFieldBorder(Color.DIMGRAY));
        }
    }

    private Border createTextFieldBorder(Paint color) {
        return new Border(new BorderStroke(color, color, color, color, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT, Insets.EMPTY));
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
        onTextUpdate();
    }

    public TextField getTextField() {
        return textField;
    }
}
