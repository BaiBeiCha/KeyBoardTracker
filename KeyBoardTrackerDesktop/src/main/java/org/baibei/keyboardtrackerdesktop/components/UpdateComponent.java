package org.baibei.keyboardtrackerdesktop.components;

import org.baibei.keyboardtrackerdesktop.pojo.console.ConsoleOutput;
import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.baibei.keyboardtrackerdesktop.services.AppProperties;
import org.baibei.keyboardtrackerdesktop.services.RequestService;
import org.springframework.stereotype.Component;

@Component
public class UpdateComponent extends Thread {

    private final StandardRepository standardRepository;
    private final RequestService requestService;
    private final AppProperties appProperties;

    public UpdateComponent(StandardRepository standardRepository,
                           RequestService requestService,
                           AppProperties appProperties) {
        this.standardRepository = standardRepository;
        this.requestService = requestService;
        this.appProperties = appProperties;
    }

    public void run() {
        while (true) {
            try {
                appProperties.update();
                Long time_to_send = appProperties.getTimeToSend();
                Thread.sleep(time_to_send);

                User user = standardRepository.find();
                user = requestService.send(
                        "http://localhost:8080/api/users/" + user.getUsername(),
                        RequestMethod.PATCH, new UserResponse(user));

                if (appProperties.getPrintUpload()) {
                    ConsoleOutput.print("user uploaded",
                             user.getUsername());
                }
            } catch (Exception e) {
                if (appProperties.getPrintUpload()) {
                    ConsoleOutput.error("Can't connect to server");
                }
            }
        }
    }
}
