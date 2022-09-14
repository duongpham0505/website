package com.program.website.electronic.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {
    private static final FileHelper fileHelper = new FileHelper();
    public static FileHelper getInstance(){return fileHelper;}
    private static final Logger logger = LogManager.getLogger(FileHelper.class);

    public void createFile(String path) {
        try {
            Path newFilePath = Paths.get(path);
            Files.createFile(newFilePath);
        }catch (Exception e) {
            logger.error(e);
        }
    }

    public String getFile(String path) {
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                logger.error(e);
            }
        }catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public void writeFile(String data, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf(data);
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("b", "test c");
        FileHelper.getInstance().writeFile(jsonObject.toString(),path + "/website/src/main/resources/config/test.json");
    }
}
