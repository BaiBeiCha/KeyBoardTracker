package org.baibei.keyboardtrackerdesktop.configs;

import org.baibei.keyboardtrackerdesktop.components.UpdateComponent;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.other.Username;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.baibei.keyboardtrackerdesktop.services.RegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoConfig {

    private final StandardRepository standardRepository;
    private final RegisterService registerService;
    private final UpdateComponent updateComponent;

    public RepoConfig(StandardRepository standardRepository,
                      RegisterService registerService,
                      UpdateComponent updateComponent) {
        this.standardRepository = standardRepository;
        this.registerService = registerService;
        this.updateComponent = updateComponent;

        if (standardRepository.find().getPassword() == null) {
            registerService.register();
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
