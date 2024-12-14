package org.baibei.keyboardtrackerdesktop;

import org.baibei.keyboardtrackerdesktop.services.ConsoleControlService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class KeyBoardTrackerDesktopApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyBoardTrackerDesktopApplication.class, args);

        consoleControl();
    }

    private static void consoleControl() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.trim().equals("exit")) {
                break;
            } else {
                ConsoleControlService.handle(command);
            }
        }
    }
}
