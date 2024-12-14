package org.baibei.keyboardtrackerdesktop.pojo.user;

import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;

public class UserConvertor {

    public static User convert(String info) {
        User user = new User();

        try {
            String[] args = info.split("\n");

            for (String arg : args) {
                String[] var = arg.split(":");

                switch (var[0]) {
                    case "username":
                        user.setUsername(var[1]);
                        break;

                    case "password":
                        user.setPasswordS(var[1]);
                        break;

                    case "key":
                        String[] keyArgs = var[1].split("=");
                        Key key = new Key(keyArgs[0].toLowerCase().charAt(0));
                        long amountK = Long.parseLong(keyArgs[1]);
                        key.setAmount(amountK);
                        user.addKey(key);
                        break;

                    case "word":
                        String[] wordArgs = var[1].split("=");
                        Word word = new Word(wordArgs[0].toLowerCase());
                        long amountW = Long.parseLong(wordArgs[1]);
                        word.setAmount(amountW);
                        user.addWord(word);
                        break;

                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
