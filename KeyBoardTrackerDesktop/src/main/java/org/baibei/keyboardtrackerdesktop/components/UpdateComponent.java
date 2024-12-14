package org.baibei.keyboardtrackerdesktop.components;

import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.baibei.keyboardtrackerdesktop.services.RequestService;
import org.springframework.stereotype.Component;

@Component
public class UpdateComponent extends Thread {

    private final StandardRepository standardRepository;
    private final RequestService requestService;

    public UpdateComponent(StandardRepository standardRepository, RequestService requestService) {
        this.standardRepository = standardRepository;
        this.requestService = requestService;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                User user = standardRepository.find();
                user = requestService.send(
                        "http://localhost:8080/api/users/" + user.getUsername(),
                        RequestMethod.PATCH, new UserResponse(user));
                standardRepository.save(user);
                System.out.println("\n\n\nUSER UPDATED:\n" + user + "\n\n\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
