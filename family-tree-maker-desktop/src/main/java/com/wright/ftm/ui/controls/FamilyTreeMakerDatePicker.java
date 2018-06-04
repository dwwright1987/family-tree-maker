package com.wright.ftm.ui.controls;

import com.wright.ftm.utils.DateUtils;
import javafx.scene.control.DatePicker;

import java.sql.Date;

public class FamilyTreeMakerDatePicker extends FamilyTreeMakerControl {
    private DatePicker datePicker = createDatePicker();

    public FamilyTreeMakerDatePicker(boolean required, FamilyTreeMakerControlEvenHandler delegate) {
        super(required, delegate);
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setOnAction(event -> onControlAction(datePicker));

        updateBorder(datePicker);

        return datePicker;
    }

    protected boolean requirementsMet() {
        return datePicker != null && datePicker.getValue() != null;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Date getDate() {
        return DateUtils.getSqlDate(datePicker.getValue());
    }

    public void setDate(Date date) {
        datePicker.setValue(DateUtils.getLocalDate(date));
        updateBorder(datePicker);
    }
}
