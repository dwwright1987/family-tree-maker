package com.wright.ftm.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JarFileReaderService {
    public String readAsString(String jarPath) throws IOException {
        StringBuilder contentsBuilder = new StringBuilder();

        InputStream inputStream = getClass().getResourceAsStream(jarPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            contentsBuilder.append(line);
        }

        return contentsBuilder.toString();
    }

    public InputStream readAsInputStream(String jarPath) {
        return getClass().getResourceAsStream( jarPath);
    }
}
