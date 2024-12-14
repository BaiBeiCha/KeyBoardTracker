package org.baibei.keyboardtrackerdesktop.components;

import org.baibei.keyboardtrackerdesktop.pojo.other.RepositoryPath;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitComponent {

    @Bean
    public RepositoryPath getRepositoryPath() {
        return new RepositoryPath("D:\\repo.txt");
    }
}
