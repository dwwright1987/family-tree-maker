package com.wright.ftm.ui.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FatalAlert extends FamilyTreeMakerAlert {
    public static void show(Stage owningStage, String message) {
        Alert alert = createAlert(AlertType.ERROR, message);
        alert.setOnCloseRequest(event -> {
           owningStage.close();
        });
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                owningStage.close();
            }
        });
    }
}
