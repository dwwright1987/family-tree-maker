package com.wright.ftm.ui.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WarningAlert extends FamilyTreeMakerAlert {
    public static void show(String message) {
        Alert alert = createAlert(AlertType.WARNING, message);
        alert.show();
    }
}
