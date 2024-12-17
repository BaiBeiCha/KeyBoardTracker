package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;
import org.baibei.keyboardtrackerdesktop.pojo.other.Command;
import org.baibei.keyboardtrackerdesktop.pojo.console.ConsoleOutput;
import org.baibei.keyboardtrackerdesktop.pojo.other.RepositoryPath;
import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.repositories.PropertiesRepository;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod.PATCH;

@Service
public class ConsoleControlService {

    private static RepositoryPath repoPath;
    private static RequestService requestService;
    private static StandardRepository repository;
    private static RegisterService registerService;
    private static AppProperties appProperties;
    private static PropertiesRepository propertiesRepository;
    private static CommandsInfoService commandsInfoService;

    public ConsoleControlService(RepositoryPath repoPath,
                                 RequestService requestService,
                                 StandardRepository repository,
                                 RegisterService registerService,
                                 AppProperties appProperties,
                                 PropertiesRepository propertiesRepository,
                                 CommandsInfoService commandsInfoService) {
        ConsoleControlService.repoPath = repoPath;
        ConsoleControlService.requestService = requestService;
        ConsoleControlService.repository = repository;
        ConsoleControlService.registerService = registerService;
        ConsoleControlService.appProperties = appProperties;
        ConsoleControlService.propertiesRepository = propertiesRepository;
        ConsoleControlService.commandsInfoService = commandsInfoService;
    }

    public static void handle(String command) {
        Command cmd = new Command();
        try {
            cmd = Command.handle(command);
        } catch (Exception e) {
            ConsoleOutput.error("Cannot handle command: " + command);
            return;
        }
        ConsoleOutput.printLine();
        System.out.println();

        switch (cmd.getCommand()) {
            case "commands":
                ConsoleOutput.info(commandsInfoService.getInfo());
                break;

            case "repodir":
                switch (cmd.getParams()[0]) {
                    case "info", "?":
                        ConsoleOutput.print("path" ,toRealPath(repoPath.getPath()));
                        break;

                    default:
                        ConsoleOutput.error("Unknown command: " + cmd.toString());
                        break;
                }
                break;

            case "properties":
                switch (cmd.getParams()[0]) {
                    case "info", "?":
                        ConsoleOutput.info(appProperties.toString());
                        break;

                    case "path":
                        ConsoleOutput.print("path" ,toRealPath("properties.txt"));
                        break;

                    case "update":
                        System.out.println(cmd.getParams()[1]);
                        propertiesRepository.write(cmd.getParams()[1], cmd.getParams()[2]);
                        appProperties.update();
                        ConsoleOutput.success(appProperties.toString());
                        break;

                    default:
                        ConsoleOutput.error("Unknown command: " + cmd.toString());
                        break;
                }
                break;

            case "sync":
                try {
                    User user = requestService.send("http://localhost:8080/api/users/" +
                                    repository.find().getUsername(),
                            RequestMethod.GET, new UserResponse());
                    repository.save(user);
                    ConsoleOutput.success();
                    printUser(user);
                } catch (Exception e) {
                    ConsoleOutput.error("Can't connect to server");
                }
                break;

            case "send":
                try {
                    User userSend = repository.find();
                    UserResponse response = new UserResponse(userSend);
                    User newUser = requestService.send("http://localhost:8080/api/users/" +
                                    userSend.getUsername(),
                            PATCH, response);
                    repository.save(newUser);
                    ConsoleOutput.success();
                    printUser(userSend);
                } catch (Exception e) {
                    ConsoleOutput.error("Can't connect to server");
                }
                break;

            case "user":
                switch (cmd.getParams()[0]) {
                    case "info", "?":
                        User info = repository.find();
                        ConsoleOutput.info(info.toString());
                        break;

                    case "change":
                        try {
                            switch (cmd.getParams()[1]) {
                                case "username":
                                    User userUsernameUpdate = repository.find();
                                    userUsernameUpdate.setUsername(cmd.getParams()[2]);
                                    requestService.send("http://localhost:8080/api/users/"
                                                    + repository.find().getUsername(),
                                            PATCH, new UserResponse(userUsernameUpdate));
                                    repository.save(userUsernameUpdate);
                                    ConsoleOutput.success();
                                    break;

                                case "password":
                                    User userPasswordUpdate = repository.find();
                                    userPasswordUpdate.setPassword(cmd.getParams()[2]);
                                    requestService.send("http://localhost:8080/api/users/"
                                                    + repository.find().getUsername(),
                                            PATCH, new UserResponse(userPasswordUpdate));
                                    repository.save(userPasswordUpdate);
                                    ConsoleOutput.success();
                                    break;

                                default:
                                    ConsoleOutput.error("Unknown command: " + cmd.toString());
                            }
                        } catch (Exception e) {
                            ConsoleOutput.error("Can't connect to server");
                        }
                        break;

                    case "update":
                        switch (cmd.getParams()[1]) {
                            case "addkey":
                                User userAddKey = repository.find();
                                userAddKey.addKey(new Key(cmd.getParams()[2].charAt(0)));
                                repository.save(userAddKey);
                                ConsoleOutput.success();
                                break;

                            case "addword":
                                User userAddWord = repository.find();
                                userAddWord.addWord(new Word(cmd.getParams()[2]));
                                repository.save(userAddWord);
                                ConsoleOutput.success();
                                break;

                            default:
                                ConsoleOutput.error("Unknown command: " + cmd.toString());
                                break;
                        }
                        break;

                    case "reg", "register":
                        registerService.register();

                    case "delete", "del":
                        repository.delete();
                        ConsoleOutput.success();
                        break;

                    default:
                        ConsoleOutput.error("Unknown command: " + cmd.toString());
                    break;
                }
                break;

            default:
                ConsoleOutput.error("Unknown command: " + cmd.toString());
                break;
        }
    }

    public static String printUser(User user) {
        StringBuilder sb = new StringBuilder();
        if (user == null) {
            user = new User();
            user.setUsername("NULL");
            user.setPassword("NULL");
        }
        sb.append("USER\n");
        sb.append(ConsoleOutput.getLine());
        sb.append("\nUsername: ").append(user.getUsername());
        sb.append("\nKeys:\n").append(user.getKeysObject().toString());
        sb.append("\nWords:\n").append(user.getWordsObject().toString());

        return sb.toString();
    }

    private static String toRealPath(String pathString) {
        Path path = Paths.get(pathString);
        try {
            Path realPath = path.toRealPath();
            return realPath.toString();
        } catch (IOException e) {
            ConsoleOutput.error(e.getMessage());
        }
        return null;
    }
}
