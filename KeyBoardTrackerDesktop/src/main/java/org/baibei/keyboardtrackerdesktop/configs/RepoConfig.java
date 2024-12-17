package org.baibei.keyboardtrackerdesktop.configs;

import org.baibei.keyboardtrackerdesktop.components.UpdateComponent;
import org.baibei.keyboardtrackerdesktop.pojo.console.ConsoleOutput;
import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.other.Username;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.baibei.keyboardtrackerdesktop.services.RegisterService;
import org.baibei.keyboardtrackerdesktop.services.RequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoConfig {

    private final StandardRepository standardRepository;
    private final RegisterService registerService;
    private final UpdateComponent updateComponent;
    private final RequestService requestService;

    public RepoConfig(StandardRepository standardRepository,
                      RegisterService registerService,
                      UpdateComponent updateComponent,
                      RequestService requestService) {
        this.standardRepository = standardRepository;
        this.registerService = registerService;
        this.updateComponent = updateComponent;
        this.requestService = requestService;

        if (standardRepository.find().getPassword() == null ||
                standardRepository.find().getPassword().equals("null")||
                standardRepository.find().getUsername() == null ||
                standardRepository.find().getUsername().equals("null")) {
            registerService.register();
        } else {
            try {
                User user = requestService.send("http://localhost:8080/api/users/"
                                + standardRepository.find().getUsername(),
                                RequestMethod.GET, new UserResponse());
                    ConsoleOutput.success("Successfully logged in!");
            } catch (Exception e) {
                ConsoleOutput.error("Can't connect to server!");
                registerService.register();
            }
        }
        updateComponent.start();
    }

    @Bean
    public Username getUsername() {
        User user = standardRepository.find();
        if (user == null || user.getUsername() == null) {
            String us = randomUsername();
            user.setUsername(us);
            standardRepository.save(user);
            return new Username(us);
        } else {
            return new Username(user.getUsername());
        }
    }

    private String randomUsername() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (Math.random() * 10 + 5); i++) {
            sb.append(random());
        }
        return sb.toString();
    }

    private char random() {
        return (char) ((int) (Math.random() * 26) + 65);
    }
}
