package org.baibei.keyboardtrackerdesktop.repositories;

import org.baibei.keyboardtrackerdesktop.pojo.console.ConsoleOutput;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Repository
public class PropertiesRepository {

    /*
    * property_name = value
    */

    private String path = "properties.txt";

    public Long getTimeToSend() {
        try {
            String[] properties = read().trim().split("\n");
            for (String property : properties) {
                if (property.contains("time_to_send")) {
                    return Long.parseLong(property.split("=")[1].trim());
                }
            }
        } catch (Exception ignored) {
            ConsoleOutput.error("Can't read properties file");
        }
        return null;
    }

    public Boolean getPrintUpdate() {
        try {
            String[] properties = read().trim().split("\n");
            for (String property : properties) {
                if (property.contains("print_upload")) {
                    return Boolean.parseBoolean(property.split("=")[1].trim());
                }
            }
        } catch (Exception e) {
            ConsoleOutput.error("Can't read properties file");
        }
        return null;
    }

    public String read() throws IOException {
        StringBuilder infoString = new StringBuilder();
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            try(FileWriter writer = new FileWriter(path, false))
            {
                String info = buildStandartInfo();
                writer.write(info);
                writer.flush();
            }
            catch(Exception e){
                ConsoleOutput.error("Can't make properties file");
            }
        }

        try (FileReader reader = new FileReader(path)) {
            int character;
            while ((character = reader.read()) != -1) {
                infoString.append((char) character);
            }
        } catch(Exception e) {
            ConsoleOutput.error("Can't read properties file");
        }

        return infoString.toString();
    }

    public void write(String name ,String info) {
        Long time_to_send = getTimeToSend();
        Boolean print_upload = getPrintUpdate();

        try(FileWriter writer = new FileWriter(path, false))
        {
            StringBuilder newInfo = new StringBuilder();
            switch (name) {
                case "time_to_send":
                    newInfo.append("time_to_send = ")
                            .append(info)
                            .append("\n")
                            .append("print_upload = ")
                            .append(print_upload);
                    break;

                case "print_upload":
                    newInfo.append("time_to_send = ")
                            .append(time_to_send)
                            .append("\n")
                            .append("print_upload = ")
                            .append(info);
                break;

                default:
                    throw new Exception();
            }
            writer.write(newInfo.toString());
            writer.flush();
        }
        catch(Exception e){
            ConsoleOutput.error("Can't update properties file");
        }
    }

    private String buildStandartInfo() {
        return "time_to_send = 10000\n" +
                "print_upload = true";
    }
}
