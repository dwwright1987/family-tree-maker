package com.wright.ftm.ui.controls;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class FamilyTreeMakerControl {
    private boolean required;
    protected Control control;
    protected FamilyTreeMakerControlEvenHandler delegate;

    protected FamilyTreeMakerControl(boolean required, FamilyTreeMakerControlEvenHandler delegate) {
        this.delegate = delegate;
        this.required = required;
    }

    protected void onControlAction(Control control) {
        updateBorder(control);
        if (delegate != null && required) {
            delegate.onFamilyTreeMakerControlEvent();
        }
    }

    protected void updateBorder(Control control) {
        if (!requirementsMet() && required) {
            control.setBorder(createBorder(Color.RED));
        } else {
            control.setBorder(createBorder(Color.DIMGRAY));
        }
    }

    protected abstract boolean requirementsMet();

    private Border createBorder(Paint color) {
        return new Border(new BorderStroke(color, color, color, color, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID , BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT, Insets.EMPTY));
    }
}
