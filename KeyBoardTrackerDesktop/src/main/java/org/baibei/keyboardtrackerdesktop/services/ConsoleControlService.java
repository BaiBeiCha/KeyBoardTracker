package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;
import org.baibei.keyboardtrackerdesktop.pojo.other.Command;
import org.baibei.keyboardtrackerdesktop.pojo.other.RepositoryPath;
import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsoleControlService {

    private static RepositoryPath repoPath;
    private static RequestService requestService;
    private static StandardRepository repository;
    private static RegisterService registerService;

    public ConsoleControlService(RepositoryPath repoPath,
                                 RequestService requestService,
                                 StandardRepository repository,
                                 RegisterService registerService) {
        ConsoleControlService.repoPath = repoPath;
        ConsoleControlService.requestService = requestService;
        ConsoleControlService.repository = repository;
        ConsoleControlService.registerService = registerService;
    }

    public static void handle(String command) {
        Command cmd = Command.handle(command);
        System.out.println(cmd);

        switch (cmd.getCommand()) {
            case "repodir":
                switch (cmd.getParams()[0]) {
                    case "new":
                        repoPath.setPath(cmd.getParams()[1]);
                        break;

                    case "info", "?":
                        System.out.println(repoPath.toString());
                        break;

                    default:
                        System.out.println("Unknown command: " + cmd.getParams()[0]);
                        break;
                }
                break;

            //case "repodel":
            //    try {
            //        System.gc();
            //        Files.deleteIfExists(Paths.get(repoPath.getPath()));
            //    } catch (IOException e) {
            //        e.printStackTrace();
            //    }
            //    break;

            case "sync":
                User user = requestService.send("http://localhost:8080/api/users/" +
                                repository.find().getUsername(),
                        RequestMethod.GET, new UserResponse());
                repository.save(user);
                break;

            case "send":
                User userSend = repository.find();
                UserResponse response = new UserResponse(userSend);
                User newUser = requestService.send("http://localhost:8080/api/users/" +
                        userSend.getUsername(),
                        RequestMethod.PATCH, response);
                repository.save(newUser);
                break;

            case "user":
                switch (cmd.getParams()[0]) {
                    case "info", "?":
                        User info = repository.find();
                        System.out.println(info.toString());
                        break;

                    case "update":
                        switch (cmd.getParams()[1]) {
                            case "username":
                                User userUpdate = repository.find();
                                userUpdate.setUsername(cmd.getParams()[2]);
                                repository.save(userUpdate);
                                break;

                            case "addkey":
                                User userAddKey = repository.find();
                                userAddKey.addKey(new Key(cmd.getParams()[2].charAt(0)));
                                repository.save(userAddKey);
                                break;

                            case "addword":
                                User userAddWord = repository.find();
                                userAddWord.addWord(new Word(cmd.getParams()[2]));
                                repository.save(userAddWord);
                                break;

                            default:
                                System.out.println("Unknown command: " + cmd.getParams()[1]);
                                break;
                        }
                        break;

                    case "new":
                        registerService.register();

                    case "delete", "del":
                        repository.delete();
                        break;

                    default:
                    System.out.println("Unknown command: " + cmd.getParams()[0]);
                    break;
                }
                break;

            default:
                System.out.println("Unknown command: " + cmd.getCommand());
                break;
        }
    }
}
