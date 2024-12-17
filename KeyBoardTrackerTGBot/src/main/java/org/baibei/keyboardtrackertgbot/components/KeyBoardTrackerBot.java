package org.baibei.keyboardtrackertgbot.components;

import org.baibei.keyboardtrackertgbot.pojo.command.Command;
import org.baibei.keyboardtrackertgbot.pojo.command.CommandHandler;
import org.baibei.keyboardtrackertgbot.pojo.keys.Key;
import org.baibei.keyboardtrackertgbot.pojo.keys.Keys;
import org.baibei.keyboardtrackertgbot.pojo.other.BotToken;
import org.baibei.keyboardtrackertgbot.pojo.passwordencoder.PasswordEncoder;
import org.baibei.keyboardtrackertgbot.pojo.user.User;
import org.baibei.keyboardtrackertgbot.pojo.words.Word;
import org.baibei.keyboardtrackertgbot.pojo.words.Words;
import org.baibei.keyboardtrackertgbot.services.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class KeyBoardTrackerBot extends TelegramLongPollingBot {

    private final String botToken;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private int loginStage = 0;
    private User user = new User();

    public KeyBoardTrackerBot(BotToken botToken, UserService userService,
                              PasswordEncoder passwordEncoder) {
        super(botToken.getToken());
        this.botToken = botToken.getToken();
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        try {
            connect();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "KeyBoardTracker";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    public void connect() throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (loginStage > 3) {
            if (!handleCommand(update)) {
                sendMessage(update.getMessage().getChatId(), "❌");
            }
        } else {
            login(update);
        }
    }

    private boolean handleCommand(Update update) {
        String tgId = update.getMessage().getChatId().toString();
        String command = update.getMessage().getText();
        Command cmd = CommandHandler.handle(command);

        user = userService.getUserByUsername(user.getUsername());

        System.out.println(cmd.toString());

        switch (cmd.getCommand()) {
            case "/start":
                loginStage = 0;
                userService.deleteUser(tgId);
                login(update);
                break;

            case "/keys":
                cmd = cmd.setArguments();
                if (cmd.getArguments()[0].equals("all")) {
                    sendMessage(Long.parseLong(tgId), user.getKeys().toString());
                } else if (user.getKeys().contains(new Key(cmd.getArguments()[0].charAt(0)))) {
                    Keys keys = user.getKeys();
                    char c = cmd.getArguments()[1].charAt(0);
                    Key key = keys.get(keys.posOf(new Key(c)));
                    sendMessage(Long.parseLong(tgId), key.toString());
                } else {
                    return false;
                }
                break;

            case "/words":
                cmd = cmd.setArguments();
                if (cmd.getArguments()[0].equals("all")) {
                    sendMessage(Long.parseLong(tgId), user.getWords().toString());
                } else if (user.getWords().contains(new Word(cmd.getArguments()[1]))) {
                    Words words = user.getWords();
                    String w = cmd.getArguments()[1];
                    Word word = words.get(words.posOf(new Word(w)));
                    sendMessage(Long.parseLong(tgId), word.toString());
                } else {
                    return false;
                }
                break;

            default:
                return false;
        }
        return true;
    }

    private void login(Update update) {
        String tgId = update.getMessage().getChatId().toString();
        if (userService.existsUser(tgId)) {
            user = userService.getUserFromRepository(tgId);
            loginStage = 4;
            handleCommand(update);
        } else {
            switch (loginStage) {
                case 0:
                    sendMessage(Long.parseLong(tgId), "Please, login");
                    sendMessage(Long.parseLong(tgId), "Enter your username");
                    loginStage = 1;
                    break;

                case 1:
                    user.setUsername(update.getMessage().getText());
                    if (userService.existsUserByUsername(user.getUsername())) {
                        sendMessage(Long.parseLong(tgId), "Enter your password");
                        loginStage = 2;
                    } else {
                        sendMessage(Long.parseLong(tgId), "Invalid username!");
                        loginStage = 1;
                    }
                    break;

                case 2:
                    String input = update.getMessage().getText();
                    System.out.println(input);
                    System.out.println(passwordEncoder.encode(input));
                    System.out.println(userService.getUserPassword(user.getUsername()));
                    if (passwordEncoder.verify(input, userService.getUserPassword(user.getUsername()))) {
                        sendMessage(Long.parseLong(tgId), "Success!");
                        user.setPassword(input);
                        loginStage = 3;
                    } else {
                        sendMessage(Long.parseLong(tgId), "Wrong password!");
                        loginStage = 2;
                    }
                    break;

                case 3:
                    userService.saveUser(user, tgId);
                    loginStage = 4;
                    handleCommand(update);
                    break;
            }
        }
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}