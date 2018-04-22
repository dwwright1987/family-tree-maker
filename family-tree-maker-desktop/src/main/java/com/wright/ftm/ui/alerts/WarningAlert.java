package com.wright.ftm.ui.alerts;

import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class WarningAlert {
    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setHeight(100);
        alert.setWidth(300);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(alertStage);
        UiLocationUtils.center(alertStage);

        alert.show();
    }
}
