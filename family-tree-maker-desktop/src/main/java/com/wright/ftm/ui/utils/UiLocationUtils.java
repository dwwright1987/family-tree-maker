package com.wright.ftm.ui.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UiLocationUtils {
    public static void center(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2d);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2d);
    }
}
