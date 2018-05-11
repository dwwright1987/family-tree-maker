package com.wright.ftm.ui.alerts;

import com.wright.ftm.ui.utils.GraphicsUtils;
import com.wright.ftm.ui.utils.UiLocationUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public abstract class FamilyTreeMakerAlert {
    protected static Alert createAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.setHeight(calculateCellHeight(message));
        alert.setWidth(300);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        GraphicsUtils.addAppIcon(alertStage);
        UiLocationUtils.center(alertStage);

        return alert;
    }

    private static int calculateCellHeight(String message) {
        return 100 * calculateMessageSizeFactor(message);
    }

    private static int calculateMessageSizeFactor(String message) {
        double maxMessageWidthCharCount = 55.0;
        return (int)Math.ceil(message.length() / maxMessageWidthCharCount);
    }
}
