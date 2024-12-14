package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class RegisterService {

    private final StandardRepository standardRepository;
    private final RequestService requestService;
    private final Scanner scanner = new Scanner(System.in);

    public RegisterService(StandardRepository standardRepository,
                           RequestService requestService) {
        this.standardRepository = standardRepository;
        this.requestService = requestService;
    }

    public User register() {
        User user = new User();

        System.out.println("\n\n\nREGISTRATION:");
        System.out.println("--------------------------");
        System.out.println("Enter username: ");
        user.setUsername(scanner.nextLine());
        System.out.println("Enter password: ");
        user.setPassword(scanner.nextLine());
        System.out.println("--------------------------");
        System.out.println();

        user = requestService.send("http://localhost:8080/api/users/"
                + user.getUsername(), RequestMethod.PATCH, new UserResponse(user));
        standardRepository.save(user);

        return user;
    }
}
