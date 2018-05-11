package com.wright.ftm.ui.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.function.Consumer;

public class ConfirmationAlert extends FamilyTreeMakerAlert {
    public static void show(String message, Consumer<? super ButtonType> responseFunc) {
        Alert alert = createAlert(AlertType.CONFIRMATION, message);
        alert.showAndWait().ifPresent(responseFunc);
    }
}
