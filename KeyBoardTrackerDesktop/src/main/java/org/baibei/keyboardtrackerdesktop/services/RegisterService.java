package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.passwordencoder.PasswordEncoder;
import org.baibei.keyboardtrackerdesktop.pojo.console.Color;
import org.baibei.keyboardtrackerdesktop.pojo.console.ConsoleOutput;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod.*;

@Service
public class RegisterService {

    private final StandardRepository standardRepository;
    private final RequestService requestService;
    private final PasswordEncoder passwordEncoder = new PasswordEncoder();
    private final Scanner scanner = new Scanner(System.in);

    public RegisterService(StandardRepository standardRepository,
                           RequestService requestService) {
        this.standardRepository = standardRepository;
        this.requestService = requestService;
    }

    public User register() {
        User user = new User();

        boolean registered = false;
        while (!registered) {
            ConsoleOutput.printHeader("registration", Color.PURPLE);
            ConsoleOutput.printLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            user.setUsername(username);

            try {
                User newUser = requestService.send("http://localhost:8080/api/users/" + username,
                        GET, new UserResponse(user));

                if (newUser.getPassword() != null) {

                    System.out.println("User already exists!\nWhat do you want to do:" +
                            "\nLogin - login \\ Register new user - reg");
                    String choose = scanner.nextLine();

                    switch (choose) {
                        case "login":
                            while (true) {
                                System.out.print("Enter password: ");
                                String password = scanner.nextLine();

                                try {
                                    if (passwordEncoder.verify(password, newUser.getPassword())) {
                                        ConsoleOutput.success("Password verified!");
                                        registered = true;
                                        user.setPassword(password);

                                        standardRepository.save(user);
                                        return user;
                                    } else {
                                        ConsoleOutput.error("Password does not match!");
                                    }
                                } catch (Exception e) {
                                    ConsoleOutput.error(e.getMessage());
                                }
                            }

                        case "reg":
                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();
                            user.setPassword(password);

                            requestService.send("http://localhost:8080/api/users/" + username,
                                    PATCH, new UserResponse(user));

                            standardRepository.save(user);
                            return user;

                        default:
                            System.out.println("Wrong choice! Try again");
                    }
                } else {
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    user.setPassword(password);

                    requestService.send("http://localhost:8080/api/users/" + username,
                            PATCH, new UserResponse(user));

                    ConsoleOutput.success("Successfully registered!");

                    standardRepository.save(user);
                    return user;
                }
            } catch (Exception e) {
                ConsoleOutput.error("Can't connect to server");
                ConsoleOutput.error(e.getMessage());
                standardRepository.delete();
            }
        }

        standardRepository.save(user);
        return user;
    }
}
