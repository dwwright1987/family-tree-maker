package com.wright.ftm.ui.alerts;

import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FatalAlert {
    public static void show(Stage owningStage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeight(100);
        alert.setWidth(300);
        alert.setOnCloseRequest(event -> {
           owningStage.close();
        });

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(alertStage);
        UiLocationUtils.center(alertStage);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                owningStage.close();
            }
        });
    }
}
