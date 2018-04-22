package com.wright.ftm.ui.utils;

import com.wright.ftm.services.JarFileReaderService;
import javafx.stage.Stage;

public class GraphicsUtils {
    public static void addAppIcon(Stage stage) {
        JarFileReaderService jarFileReaderService = new JarFileReaderService();
        stage.getIcons().add(jarFileReaderService.getImage("/images/icon.png"));
    }
}
